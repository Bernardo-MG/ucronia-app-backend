
package com.bernardomg.association.member.usecase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberName;
import com.bernardomg.association.member.domain.model.MemberQuery;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.usecase.validation.CreateMemberValidator;

import io.jsonwebtoken.lang.Strings;
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

    private final CreateMemberValidator createMemberValidator = new CreateMemberValidator();

    private final MemberRepository      memberRepository;

    public DefaultMemberService(final MemberRepository memberRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
    }

    @Override
    public final Member create(final Member member) {
        final Member     toCreate;
        final Long       index;
        final String     fullName;
        final MemberName memberName;

        log.debug("Creating member {}", member);

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        memberName = MemberName.builder()
            .withFirstName(member.getName()
                .getFirstName())
            .withLastName(member.getName()
                .getLastName())
            .build();
        toCreate = Member.builder()
            .withIdentifier(member.getIdentifier())
            .withName(memberName)
            .withPhone(member.getPhone())
            .build();

        createMemberValidator.validate(toCreate);

        // Set number
        index = memberRepository.findNextNumber();
        toCreate.setNumber(index);

        // TODO: the model should do this
        // Trim strings
        toCreate.getName()
            .setFirstName(StringUtils.trim(toCreate.getName()
                .getFirstName()));
        toCreate.getName()
            .setLastName(StringUtils.trim(toCreate.getName()
                .getLastName()));
        fullName = Strings.trimWhitespace(toCreate.getName()
            .getFirstName() + " "
                + toCreate.getName()
                    .getLastName());
        toCreate.getName()
            .setFullName(fullName);

        return memberRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting member {}", number);

        if (!memberRepository.exists(number)) {
            // TODO: change name
            throw new MissingMemberException(number);
        }

        // TODO: Forbid deleting when there are relationships

        memberRepository.delete(number);
    }

    @Override
    public final Iterable<Member> getAll(final MemberQuery query, final Pageable pageable) {
        final Iterable<Member> members;
        final Pageable         pagination;

        log.debug("Reading members with sample {} and pagination {}", query, pageable);

        pagination = correctPagination(pageable);

        switch (query.getStatus()) {
            case ACTIVE:
                members = memberRepository.findActive(pagination);
                break;
            case INACTIVE:
                members = memberRepository.findInactive(pagination);
                break;
            default:
                members = memberRepository.findAll(pagination);
        }

        return members;
    }

    @Override
    public final Optional<Member> getOne(final long number) {
        final Optional<Member> member;

        log.debug("Reading member {}", number);

        member = memberRepository.findOne(number);
        if (member.isEmpty()) {
            throw new MissingMemberException(number);
        }

        return member;
    }

    @Override
    public final Member update(final Member member) {
        final boolean    exists;
        final Member     toUpdate;
        final String     fullName;
        final MemberName memberName;

        log.debug("Updating member {} using data {}", member.getNumber(), member);

        // TODO: Identificator and phone must be unique or empty

        exists = memberRepository.exists(member.getNumber());
        if (!exists) {
            // TODO: change name
            throw new MissingMemberException(member.getNumber());
        }

        memberName = MemberName.builder()
            .withFirstName(member.getName()
                .getFirstName())
            .withLastName(member.getName()
                .getLastName())
            .build();
        toUpdate = Member.builder()
            .withNumber(member.getNumber())
            .withIdentifier(member.getIdentifier())
            .withName(memberName)
            .withPhone(member.getPhone())
            .build();

        // TODO: the model should do this
        // Trim strings
        toUpdate.getName()
            .setFirstName(StringUtils.trim(toUpdate.getName()
                .getFirstName()));
        toUpdate.getName()
            .setLastName(StringUtils.trim(toUpdate.getName()
                .getLastName()));
        fullName = Strings.trimWhitespace(toUpdate.getName()
            .getFirstName() + " "
                + toUpdate.getName()
                    .getLastName());
        toUpdate.getName()
            .setFullName(fullName);

        return memberRepository.save(toUpdate);
    }

    private final Pageable correctPagination(final Pageable pageable) {
        final Sort     sort;
        final Pageable page;

        // TODO: change the pagination system
        sort = correctSort(pageable.getSort());

        if (pageable.isPaged()) {
            page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        } else {
            page = Pageable.unpaged(pageable.getSort());
        }

        return page;
    }

    private final Sort correctSort(final Sort received) {
        final Optional<Order> fullNameOrder;
        final List<Order>     orders;
        final List<Order>     validOrders;

        fullNameOrder = received.stream()
            .filter(o -> "fullName".equals(o.getProperty()))
            .findFirst();

        orders = new ArrayList<>();
        if (fullNameOrder.isPresent()) {
            if (fullNameOrder.get()
                .getDirection() == Direction.ASC) {
                orders.add(Order.asc("name"));
                orders.add(Order.asc("surname"));
            } else {
                orders.add(Order.desc("name"));
                orders.add(Order.desc("surname"));
            }
        }

        validOrders = received.stream()
            .filter(o -> !"fullName".equals(o.getProperty()))
            .toList();
        orders.addAll(validOrders);

        return Sort.by(orders);
    }

}
