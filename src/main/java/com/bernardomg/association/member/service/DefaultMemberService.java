
package com.bernardomg.association.member.service;

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

import com.bernardomg.association.member.domain.ActiveMemberDomainService;
import com.bernardomg.association.member.exception.MissingMemberIdException;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.MemberChange;
import com.bernardomg.association.member.model.MemberName;
import com.bernardomg.association.member.model.MemberQuery;
import com.bernardomg.association.member.persistence.model.MemberEntity;
import com.bernardomg.association.member.persistence.repository.MemberRepository;

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

    private final ActiveMemberDomainService activeMemberSource;

    /**
     * Member repository.
     */
    private final MemberRepository          memberRepository;

    public DefaultMemberService(final MemberRepository memberRepo, final ActiveMemberDomainService activeMemberSrc) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
        activeMemberSource = Objects.requireNonNull(activeMemberSrc);
    }

    @Override
    public final Member create(final MemberChange member) {
        final MemberEntity entity;
        final MemberEntity created;
        final Long         index;

        log.debug("Creating member {}", member);

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        entity = toEntity(member);

        index = memberRepository.findNextNumber();
        entity.setNumber(index);

        // Trim strings
        entity.setName(StringUtils.trim(entity.getName()));
        entity.setSurname(StringUtils.trim(entity.getSurname()));

        created = memberRepository.save(entity);

        return toDto(created);
    }

    @Override
    public final void delete(final long number) {
        final Optional<MemberEntity> member;

        log.debug("Deleting member {}", number);

        member = memberRepository.findByNumber(number);
        if (member.isEmpty()) {
            // TODO: change name
            throw new MissingMemberIdException(number);
        }

        // TODO: Forbid deleting when there are relationships

        memberRepository.deleteById(member.get()
            .getId());
    }

    @Override
    public final Iterable<Member> getAll(final MemberQuery query, final Pageable pageable) {
        final Page<Member> members;
        final Pageable     pagination;

        log.debug("Reading members with sample {} and pagination {}", query, pageable);

        pagination = correctPagination(pageable);

        switch (query.getStatus()) {
            case ACTIVE:
                members = activeMemberSource.findAllActive(pagination);
                break;
            case INACTIVE:
                members = activeMemberSource.findAllInactive(pagination);
                break;
            default:
                members = activeMemberSource.findAll(pagination);
        }

        return members;
    }

    @Override
    public final Optional<Member> getOne(final long number) {
        final Optional<MemberEntity> member;

        log.debug("Reading member {}", number);

        member = memberRepository.findByNumber(number);
        if (member.isEmpty()) {
            // TODO: change name
            throw new MissingMemberIdException(number);
        }

        return member.map(this::toActive);
    }

    @Override
    public final Member update(final long number, final MemberChange change) {
        final Optional<MemberEntity> member;
        final MemberEntity           entity;
        final MemberEntity           updated;

        log.debug("Updating member {} using data {}", number, change);

        // TODO: Identificator and phone must be unique or empty

        member = memberRepository.findByNumber(number);
        if (member.isEmpty()) {
            // TODO: change name
            throw new MissingMemberIdException(number);
        }

        entity = toEntity(change);

        // Set id
        entity.setId(member.get()
            .getId());
        entity.setNumber(number);

        // Trim strings
        entity.setName(entity.getName()
            .trim());
        entity.setSurname(entity.getSurname()
            .trim());

        updated = memberRepository.save(entity);

        return toActive(updated);
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

    private final Member toActive(final MemberEntity member) {
        return activeMemberSource.findOne(member.getId());
    }

    private final Member toDto(final MemberEntity entity) {
        final MemberName memberName;
        final String     fullName;

        fullName = Strings.trimWhitespace(entity.getName() + " " + entity.getSurname());
        memberName = MemberName.builder()
            .firstName(entity.getName())
            .lastName(entity.getSurname())
            .fullName(fullName)
            .build();
        return Member.builder()
            .number(entity.getNumber())
            .identifier(entity.getIdentifier())
            .name(memberName)
            .phone(entity.getPhone())
            .build();
    }

    private final MemberEntity toEntity(final MemberChange data) {
        return MemberEntity.builder()
            .identifier(data.getIdentifier())
            .name(data.getName()
                .getFirstName())
            .surname(data.getName()
                .getLastName())
            .phone(data.getPhone())
            .build();
    }

}
