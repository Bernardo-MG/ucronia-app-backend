
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

        log.debug("Finding active users");

        members = memberSpringRepository.findAllActivePublic(pageable)
            .map(this::toDomain);

        log.debug("Found active users {}", members);

        return members;
    }

    @Override
    public final Iterable<PublicMember> findAll(final Pageable pageable) {
        final Page<PublicMember> members;

        log.debug("Finding all the members");

        members = memberSpringRepository.findAllPublic(pageable)
            .map(this::toDomain);

        log.debug("Found all the members: {}", members);

        return members;
    }

    @Override
    public final Iterable<PublicMember> findInactive(final Pageable pageable) {
        final Page<PublicMember> members;

        log.debug("Finding inactive users");

        members = memberSpringRepository.findAllInactivePublic(pageable)
            .map(this::toDomain);

        log.debug("Found active users {}", members);

        return members;
    }

    @Override
    public final Optional<PublicMember> findOne(final Long number) {
        final Optional<PublicMember> member;

        log.debug("Finding member with number {}", number);

        member = memberSpringRepository.findByNumberPublic(number)
            .map(this::toDomain);

        log.debug("Found member with number {}: {}", number, member);

        return member;
    }

    private final PublicMember toDomain(final MinimalMember entity) {
        final PersonName memberName;

        memberName = PersonName.builder()
            .withFirstName(entity.getFirstName())
            .withLastName(entity.getLastName())
            .build();
        return PublicMember.builder()
            .withNumber(entity.getNumber())
            .withName(memberName)
            .withActive(entity.getActive())
            .build();
    }

}
