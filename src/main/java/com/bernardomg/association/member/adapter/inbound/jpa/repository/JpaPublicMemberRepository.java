
package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MinimalMember;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.person.domain.model.PersonName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaPublicMemberRepository implements PublicMemberRepository {

    private final MemberSpringRepository memberSpringRepository;

    public JpaPublicMemberRepository(final MemberSpringRepository memberSpringRepo) {
        super();

        memberSpringRepository = Objects.requireNonNull(memberSpringRepo);
    }

    @Override
    public final Iterable<PublicMember> findActive(final Pageable pageable) {
        final Page<PublicMember> members;

        log.trace("Finding active public members");

        members = memberSpringRepository.findAllActivePublic(pageable)
            .map(this::toDomain);

        log.trace("Found active public members {}", members);

        return members;
    }

    @Override
    public final Iterable<PublicMember> findAll(final Pageable pageable) {
        final Page<PublicMember> members;

        log.trace("Finding all the public members");

        members = memberSpringRepository.findAllPublic(pageable)
            .map(this::toDomain);

        log.trace("Found all the public members: {}", members);

        return members;
    }

    @Override
    public final Iterable<PublicMember> findInactive(final Pageable pageable) {
        final Page<PublicMember> members;

        log.trace("Finding inactive public members");

        members = memberSpringRepository.findAllInactivePublic(pageable)
            .map(this::toDomain);

        log.trace("Found active public members {}", members);

        return members;
    }

    @Override
    public final Optional<PublicMember> findOne(final Long number) {
        final Optional<PublicMember> member;

        log.trace("Finding public member with number {}", number);

        member = memberSpringRepository.findByNumberPublic(number)
            .map(this::toDomain);

        log.trace("Found public member with number {}: {}", number, member);

        return member;
    }

    private final PublicMember toDomain(final MinimalMember entity) {
        final PersonName name;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        return new PublicMember(entity.getNumber(), name, entity.getActive());
    }

}
