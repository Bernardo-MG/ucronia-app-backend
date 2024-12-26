
package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaMemberRepository implements MemberRepository {

    private final PersonSpringRepository personSpringRepository;

    public JpaMemberRepository(final PersonSpringRepository personSpringRepo) {
        super();

        personSpringRepository = Objects.requireNonNull(personSpringRepo);
    }

    @Override
    public final Iterable<Member> findActive(final Pagination pagination, final Sorting sorting) {
        final Page<Member> members;
        final Pageable     pageable;

        log.trace("Finding active public members with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        members = personSpringRepository.findAllActive(pageable)
            .map(this::toDomain);

        log.trace("Found active public members with pagination {} and sorting {}: {}", pagination, sorting, members);

        return members;
    }

    @Override
    public final Iterable<Member> findAll(final Pagination pagination, final Sorting sorting) {
        final Page<Member> members;
        final Pageable     pageable;

        log.trace("Finding all the public members with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        members = personSpringRepository.findAllWithMembership(pageable)
            .map(this::toDomain);

        log.trace("Found all the public members with pagination {} and sorting {}: {}", pagination, sorting, members);

        return members;
    }

    @Override
    public final Iterable<Member> findInactive(final Pagination pagination, final Sorting sorting) {
        final Page<Member> members;
        final Pageable     pageable;

        log.trace("Finding inactive public members with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        members = personSpringRepository.findAllInactive(pageable)
            .map(this::toDomain);

        log.trace("Found active public members with pagination {} and sorting {}: {}", pagination, sorting, members);

        return members;
    }

    @Override
    public final Optional<Member> findOne(final Long number) {
        final Optional<Member> member;

        log.trace("Finding public member with number {}", number);

        member = personSpringRepository.findByNumberWithMembership(number)
            .map(this::toDomain);

        log.trace("Found public member with number {}: {}", number, member);

        return member;
    }

    private final Member toDomain(final PersonEntity entity) {
        final PersonName name;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        // TODO: check it has membership flag
        return new Member(entity.getNumber(), name);
    }

}
