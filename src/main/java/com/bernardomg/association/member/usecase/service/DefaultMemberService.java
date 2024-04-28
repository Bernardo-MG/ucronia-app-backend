
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
import com.bernardomg.association.member.usecase.validation.CreateMemberValidator;

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
        final Member toCreate;
        final Long   index;

        log.debug("Creating member {}", member);

        // Set number
        index = memberRepository.findNextNumber();

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        toCreate = Member.builder()
            .withIdentifier(member.getIdentifier())
            .withName(member.getName())
            .withPhone(member.getPhone())
            .withNumber(index)
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
        final boolean exists;

        log.debug("Updating member {} using data {}", member.getNumber(), member);

        // TODO: Identificator and phone must be unique or empty

        exists = memberRepository.exists(member.getNumber());
        if (!exists) {
            throw new MissingMemberException(member.getNumber());
        }

        return memberRepository.save(member);
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
