
package com.bernardomg.association.member.usecase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
@Transactional
public final class DefaultReducedMemberService implements PublicMemberService {

    private final MemberRepository memberRepository;

    public DefaultReducedMemberService(final MemberRepository memberRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
    }

    @Override
    public final Iterable<PublicMember> getAll(final Pageable pageable) {
        final Pageable               pagination;
        final Iterable<Member>       members;
        final Iterable<PublicMember> result;

        log.debug("Reading members with pagination {}", pageable);

        pagination = correctPagination(pageable);

        members = memberRepository.findActive(pagination);

        if (members instanceof Page) {
            result = ((Page<Member>) members).map(this::toReduced);
        } else {
            result = StreamSupport.stream(members.spliterator(), false)
                .map(this::toReduced)
                .toList();
        }

        return result;
    }

    @Override
    public final Optional<PublicMember> getOne(final long number) {
        final Optional<Member> member;

        log.debug("Reading member {}", number);

        member = memberRepository.findOne(number);
        if (member.isEmpty()) {
            throw new MissingMemberException(number);
        }

        return member.map(this::toReduced);
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

    private final PublicMember toReduced(final Member member) {
        return PublicMember.builder()
            .withNumber(member.getNumber())
            .withName(member.getName())
            .withActive(member.isActive())
            .build();
    }

}
