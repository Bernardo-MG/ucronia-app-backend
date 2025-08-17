
package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaMemberRepository implements MemberRepository {

    /**
     * Logger for the class.
     */
    private static final Logger          log = LoggerFactory.getLogger(JpaMemberRepository.class);

    private final PersonSpringRepository personSpringRepository;

    public JpaMemberRepository(final PersonSpringRepository personSpringRepo) {
        super();

        personSpringRepository = Objects.requireNonNull(personSpringRepo);
    }

    @Override
    public final Page<Member> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Member> members;
        final Pageable                                     pageable;

        log.trace("Finding all the public members with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        members = personSpringRepository.findAllActiveMembers(pageable)
            .map(this::toDomain);

        log.trace("Found all the public members with pagination {} and sorting {}: {}", pagination, sorting, members);

        return new Page<>(members.getContent(), members.getSize(), members.getNumber(), members.getTotalElements(),
            members.getTotalPages(), members.getNumberOfElements(), members.isFirst(), members.isLast(), sorting);
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
