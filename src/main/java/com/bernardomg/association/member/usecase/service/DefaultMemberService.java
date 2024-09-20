
package com.bernardomg.association.member.usecase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberQuery;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.usecase.validation.MemberNameNotEmptyRule;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
@Transactional
public final class DefaultMemberService implements MemberService {

    private final Validator<Member> createMemberValidator;

    private final MemberRepository  memberRepository;

    public DefaultMemberService(final MemberRepository memberRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
        createMemberValidator = new FieldRuleValidator<>(new MemberNameNotEmptyRule());
    }

    @Override
    public final Member create(final Member member) {
        final Member toCreate;
        final Long   number;

        log.debug("Creating member {}", member);

        // Set number
        number = memberRepository.findNextNumber();

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        toCreate = Member.builder()
            .withIdentifier(member.getIdentifier())
            .withName(member.getName())
            .withPhone(member.getPhone())
            .withNumber(number)
            .withActive(false)
            .build();

        createMemberValidator.validate(toCreate);

        return memberRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting member {}", number);

        if (!memberRepository.exists(number)) {
            throw new MissingMemberException(number);
        }

        // TODO: Forbid deleting when there are relationships

        memberRepository.delete(number);
    }

    @Override
    public final Iterable<Member> getAll(final MemberQuery query, final Pageable pageable) {
        final Pageable pagination;

        log.debug("Reading members with query {} and pagination {}", query, pageable);

        pagination = correctPagination(pageable);

        return switch (query.getStatus()) {
            case ACTIVE -> memberRepository.findActive(pagination);
            case INACTIVE -> memberRepository.findInactive(pagination);
            default -> memberRepository.findAll(pagination);
        };
    }

    @Override
    public final Optional<Member> getOne(final long number) {
        final Optional<Member> member;

        log.debug("Reading member {}", number);

        member = memberRepository.findOne(number);
        if (member.isEmpty()) {
            log.error("Missing member {}", number);
            throw new MissingMemberException(number);
        }

        return member;
    }

    @Override
    public final Member patch(final Member member) {
        final Member existing;
        final Member toSave;

        log.debug("Patching member {} using data {}", member.getNumber(), member);

        // TODO: Identificator and phone must be unique or empty
        // TODO: Apply the creation validations

        existing = memberRepository.findOne(member.getNumber())
            .orElseThrow(() -> {
                log.error("Missing member {}", member.getNumber());
                throw new MissingMemberException(member.getNumber());
            });

        toSave = copy(existing, member);

        return memberRepository.save(toSave);
    }

    @Override
    public final Member update(final Member member) {
        final Member toUpdate;

        log.debug("Updating member {} using data {}", member.getNumber(), member);

        // TODO: Identificator and phone must be unique or empty
        // TODO: Apply the creation validations

        if (!memberRepository.exists(member.getNumber())) {
            log.error("Missing member {}", member.getNumber());
            throw new MissingMemberException(member.getNumber());
        }

        toUpdate = Member.builder()
            .withNumber(member.getNumber())
            .withIdentifier(member.getIdentifier())
            .withName(member.getName())
            .withPhone(member.getPhone())
            .withActive(member.getActive())
            .build();

        return memberRepository.save(toUpdate);
    }

    private final Member copy(final Member existing, final Member updated) {
        final PersonName name;

        if (updated.getName() == null) {
            name = existing.getName();
        } else {
            name = PersonName.builder()
                .withFirstName(Optional.ofNullable(updated.getName()
                    .getFirstName())
                    .orElse(existing.getName()
                        .getFirstName()))
                .withLastName(Optional.ofNullable(updated.getName()
                    .getLastName())
                    .orElse(existing.getName()
                        .getLastName()))
                .build();
        }
        return Member.builder()
            .withNumber(Optional.ofNullable(updated.getNumber())
                .orElse(existing.getNumber()))
            .withIdentifier(Optional.ofNullable(updated.getIdentifier())
                .orElse(existing.getIdentifier()))
            .withName(name)
            .withPhone(Optional.ofNullable(updated.getPhone())
                .orElse(existing.getPhone()))
            .withActive(Optional.ofNullable(updated.getActive())
                .orElse(existing.getActive()))
            .build();
    }

    private final Pageable correctPagination(final Pageable pageable) {
        final Sort     sort;
        final Pageable page;

        // TODO: change the pagination system
        sort = correctSort(pageable.getSort());

        if (pageable.isPaged()) {
            page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        } else {
            page = Pageable.unpaged(sort);
        }

        return page;
    }

    private final Sort correctSort(final Sort received) {
        final Optional<Order> fullNameOrder;
        final Optional<Order> numberOrder;
        final List<Order>     orders;
        final List<Order>     validOrders;

        // Full name
        fullNameOrder = received.stream()
            .filter(o -> "fullName".equals(o.getProperty()))
            .findFirst();

        orders = new ArrayList<>();
        if (fullNameOrder.isPresent()) {
            if (Direction.ASC.equals(fullNameOrder.get()
                .getDirection())) {
                orders.add(Order.asc("person.firstName"));
                orders.add(Order.asc("person.lastName"));
            } else {
                orders.add(Order.desc("person.firstName"));
                orders.add(Order.desc("person.lastName"));
            }
        }

        // Number
        numberOrder = received.stream()
            .filter(o -> "number".equals(o.getProperty()))
            .findFirst();
        if (numberOrder.isPresent()) {
            if (Direction.ASC.equals(numberOrder.get()
                .getDirection())) {
                orders.add(Order.asc("person.number"));
            } else {
                orders.add(Order.desc("person.number"));
            }
        }

        validOrders = received.stream()
            .filter(o -> !"fullName".equals(o.getProperty()))
            .filter(o -> !"number".equals(o.getProperty()))
            .toList();
        orders.addAll(validOrders);

        return Sort.by(orders);
    }

}
