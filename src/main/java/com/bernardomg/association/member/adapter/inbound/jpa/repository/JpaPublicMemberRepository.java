
package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.PersonName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaPublicMemberRepository implements PublicMemberRepository {

    private final PersonSpringRepository personSpringRepository;

    public JpaPublicMemberRepository(final PersonSpringRepository personSpringRepo) {
        super();

        personSpringRepository = Objects.requireNonNull(personSpringRepo);
    }

    @Override
    public final Iterable<PublicMember> findActive(final Pageable pageable) {
        final Page<PublicMember> members;

        log.trace("Finding active public members");

        members = personSpringRepository.findAllActive(pageable)
            .map(this::toDomain);

        log.trace("Found active public members {}", members);

        return members;
    }

    @Override
    public final Iterable<PublicMember> findAll(final Pageable pageable) {
        final Page<PublicMember> members;

        log.trace("Finding all the public members");

        members = personSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.trace("Found all the public members: {}", members);

        return members;
    }

    @Override
    public final Iterable<PublicMember> findInactive(final Pageable pageable) {
        final Page<PublicMember> members;

        log.trace("Finding inactive public members");

        members = personSpringRepository.findAllInactive(pageable)
            .map(this::toDomain);

        log.trace("Found active public members {}", members);

        return members;
    }

    @Override
    public final Optional<PublicMember> findOne(final Long number) {
        final Optional<PublicMember> member;

        log.trace("Finding public member with number {}", number);

        member = personSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.trace("Found public member with number {}: {}", number, member);

        return member;
    }

    private final PublicMember toDomain(final PersonEntity entity) {
        final PersonName name;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        // TODO: check it has membership
        return new PublicMember(entity.getNumber(), name, entity.getMembership()
            .getActive());
    }

}
