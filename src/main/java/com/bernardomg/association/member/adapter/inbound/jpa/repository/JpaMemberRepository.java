
package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.PersonName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaMemberRepository implements MemberRepository {

    private final MemberSpringRepository memberSpringRepository;

    private final PersonSpringRepository personSpringRepository;

    public JpaMemberRepository(final MemberSpringRepository memberSpringRepo,
            final PersonSpringRepository personSpringRepo) {
        super();

        memberSpringRepository = Objects.requireNonNull(memberSpringRepo);
        personSpringRepository = Objects.requireNonNull(personSpringRepo);
    }

    @Override
    public final void activate(final long number) {
        final Optional<MemberEntity> read;
        final MemberEntity           member;

        read = memberSpringRepository.findByNumber(number);
        if (read.isPresent()) {
            member = read.get();
            member.setActive(true);
            memberSpringRepository.save(member);
        }
    }

    @Override
    public final void deactivate(final long number) {
        final Optional<MemberEntity> read;
        final MemberEntity           member;

        read = memberSpringRepository.findByNumber(number);
        if (read.isPresent()) {
            member = read.get();
            member.setActive(false);
            memberSpringRepository.save(member);
        }
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting fee {}", number);

        memberSpringRepository.deleteByNumber(number);

        log.debug("Deleted fee {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if fee {} exists", number);

        exists = memberSpringRepository.existsByNumber(number);

        log.debug("Fee {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Iterable<Member> findActive(final Pageable pageable) {
        final Page<Member> members;

        log.debug("Finding active users");

        members = memberSpringRepository.findAllActive(pageable)
            .map(this::toDomain);

        log.debug("Found active users {}", members);

        return members;
    }

    @Override
    public final Iterable<Member> findAll(final Pageable pageable) {
        final Page<Member> members;

        log.debug("Finding all the members");

        members = memberSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found all the members: {}", members);

        return members;
    }

    @Override
    public final Iterable<Member> findInactive(final Pageable pageable) {
        final Page<Member> members;

        log.debug("Finding inactive users");

        members = memberSpringRepository.findAllInactive(pageable)
            .map(this::toDomain);

        log.debug("Found active users {}", members);

        return members;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the members");

        number = personSpringRepository.findNextNumber();

        log.debug("Found next number for the members: {}", number);

        return number;
    }

    @Override
    public final Optional<Member> findOne(final Long number) {
        final Optional<Member> member;

        log.debug("Finding member with number {}", number);

        member = memberSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found member with number {}: {}", number, member);

        return member;
    }

    @Override
    public final boolean isActive(final long number) {
        final boolean active;

        log.debug("Checking if member {} is active", number);

        active = memberSpringRepository.isActive(number);

        log.debug("Member {} is active: {}", number, active);

        return active;
    }

    @Override
    public final Member save(final Member member) {
        final Optional<MemberEntity> existingMember;
        final Optional<PersonEntity> existingPerson;
        final MemberEntity           entity;
        final MemberEntity           created;
        final Member                 saved;

        log.debug("Saving member {}", member);

        entity = toEntity(member);

        existingMember = memberSpringRepository.findByNumber(member.getNumber());
        if (existingMember.isPresent()) {
            entity.setId(existingMember.get()
                .getId());
        }

        existingPerson = personSpringRepository.findByNumber(member.getNumber());
        if (existingPerson.isPresent()) {
            entity.getPerson()
                .setId(existingPerson.get()
                    .getId());
        }

        personSpringRepository.save(entity.getPerson());
        created = memberSpringRepository.save(entity);

        saved = memberSpringRepository.findByNumber(created.getPerson()
            .getNumber())
            .map(this::toDomain)
            .get();

        log.debug("Saved member {}", saved);

        return saved;
    }

    private final Member toDomain(final MemberEntity entity) {
        final PersonName memberName;

        memberName = PersonName.builder()
            .withFirstName(entity.getPerson()
                .getFirstName())
            .withLastName(entity.getPerson()
                .getLastName())
            .build();
        return Member.builder()
            .withNumber(entity.getPerson()
                .getNumber())
            .withIdentifier(entity.getPerson()
                .getIdentifier())
            .withName(memberName)
            .withPhone(entity.getPerson()
                .getPhone())
            .withActive(entity.getActive())
            .build();
    }

    private final MemberEntity toEntity(final Member data) {
        final PersonEntity person;

        // FIXME: load the person if it exists
        person = PersonEntity.builder()
            .withNumber(data.getNumber())
            .withIdentifier(data.getIdentifier())
            .withFirstName(data.getName()
                .getFirstName())
            .withLastName(data.getName()
                .getLastName())
            .withPhone(data.getPhone())
            .build();
        return MemberEntity.builder()
            .withPerson(person)
            .withActive(data.isActive())
            .build();
    }

}
