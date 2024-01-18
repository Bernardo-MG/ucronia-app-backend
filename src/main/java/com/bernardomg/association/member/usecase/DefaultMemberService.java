
package com.bernardomg.association.member.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.bernardomg.association.member.delivery.model.MemberChange;
import com.bernardomg.association.member.delivery.model.MemberQuery;
import com.bernardomg.association.member.domain.exception.MissingMemberIdException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberName;

import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;

    public DefaultMemberService(final MemberRepository memberRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
    }

    @Override
    public final Member create(final MemberChange member) {
        final Member toCreate;
        final Long   index;

        log.debug("Creating member {}", member);

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        toCreate = toDomain(member);

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

        return memberRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {
        final boolean exists;

        log.debug("Deleting member {}", number);

        exists = memberRepository.exists(number);
        if (!exists) {
            // TODO: change name
            throw new MissingMemberIdException(number);
        }

        // TODO: Forbid deleting when there are relationships

        memberRepository.delete(number);
    }

    @Override
    public final Iterable<Member> getAll(final MemberQuery query, final Pageable pageable) {
        final Page<Member> members;
        final Pageable     pagination;

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
            // TODO: change name
            throw new MissingMemberIdException(number);
        }

        return member;
    }

    @Override
    public final Member update(final long number, final MemberChange change) {
        final Optional<Member> member;
        final Member           toUpdate;

        log.debug("Updating member {} using data {}", number, change);

        // TODO: Identificator and phone must be unique or empty

        member = memberRepository.findOne(number);
        if (member.isEmpty()) {
            // TODO: change name
            throw new MissingMemberIdException(number);
        }

        toUpdate = toDomain(change);

        // Set number
        toUpdate.setNumber(number);

        // TODO: the model should do this
        // Trim strings
        toUpdate.getName()
            .setFirstName(StringUtils.trim(toUpdate.getName()
                .getFirstName()));
        toUpdate.getName()
            .setLastName(StringUtils.trim(toUpdate.getName()
                .getLastName()));

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

    private final Member toDomain(final MemberChange data) {
        final MemberName memberName;
        final String     fullName;

        // TODO: the model should return this automatically
        fullName = Strings.trimWhitespace(data.getName()
            .getFirstName() + " "
                + data.getName()
                    .getLastName());
        memberName = MemberName.builder()
            .firstName(data.getName()
                .getFirstName())
            .lastName(data.getName()
                .getLastName())
            .fullName(fullName)
            .build();
        return Member.builder()
            .identifier(data.getIdentifier())
            .name(memberName)
            .phone(data.getPhone())
            .build();
    }

}
