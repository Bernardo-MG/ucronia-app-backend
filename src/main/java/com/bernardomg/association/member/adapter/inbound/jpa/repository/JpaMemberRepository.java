
package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.PersonName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
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

        log.trace("Activating member {}", number);

        // TODO: throw an exception if it doesn't exist

        read = memberSpringRepository.findByNumber(number);
        if (read.isPresent()) {
            member = read.get();
            member.setActive(true);
            memberSpringRepository.save(member);

            log.trace("Activated member {}", number);
        }
    }

    @Override
    public final void deactivate(final long number) {
        final Optional<MemberEntity> read;
        final MemberEntity           member;

        log.trace("Deactivating member {}", number);

        // TODO: throw an exception if it doesn't exist

        read = memberSpringRepository.findByNumber(number);
        if (read.isPresent()) {
            member = read.get();
            member.setActive(false);
            memberSpringRepository.save(member);

            log.trace("Deactivated member {}", number);
        }
    }

    @Override
    public final void delete(final long number) {
        log.trace("Deleting member {}", number);

        memberSpringRepository.deleteByNumber(number);

        log.trace("Deleted member {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.trace("Checking if member {} exists", number);

        exists = memberSpringRepository.existsByNumber(number);

        log.trace("Member {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Iterable<Member> findActive(final Pageable pageable) {
        final Page<Member> members;

        log.trace("Finding active members");

        members = memberSpringRepository.findAllActive(pageable)
            .map(this::toDomain);

        log.trace("Found active members: {}", members);

        return members;
    }

    @Override
    public final Iterable<Member> findAll(final Pageable pageable) {
        final Page<Member> members;

        log.trace("Finding all the members with pagination {}", pageable);

        members = memberSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.trace("Found all the members: {}", members);

        return members;
    }

    @Override
    public final Iterable<Member> findInactive(final Pageable pageable) {
        final Page<Member> members;

        log.trace("Finding inactive members");

        members = memberSpringRepository.findAllInactive(pageable)
            .map(this::toDomain);

        log.trace("Found active members: {}", members);

        return members;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.trace("Finding next number for the members");

        number = personSpringRepository.findNextNumber();

        log.trace("Found next number for the members: {}", number);

        return number;
    }

    @Override
    public final Optional<Member> findOne(final Long number) {
        final Optional<Member> member;

        log.trace("Finding member with number {}", number);

        member = memberSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.trace("Found member with number {}: {}", number, member);

        return member;
    }

    @Override
    public final boolean isActive(final long number) {
        final Boolean active;

        log.trace("Checking if member {} is active", number);

        active = memberSpringRepository.isActive(number);

        log.trace("Member {} is active: {}", number, active);

        return Boolean.TRUE.equals(active);
    }

    @Override
    public final Member save(final Member member) {
        final Optional<MemberEntity> existingMember;
        final Optional<PersonEntity> existingPerson;
        final MemberEntity           entity;
        final MemberEntity           created;
        final Member                 saved;

        log.trace("Saving member {}", member);

        entity = toEntity(member);

        existingMember = memberSpringRepository.findByNumber(member.number());
        if (existingMember.isPresent()) {
            entity.setId(existingMember.get()
                .getId());
        }

        existingPerson = personSpringRepository.findByNumber(member.number());
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

        log.trace("Saved member {}", saved);

        return saved;
    }

    private final Member toDomain(final MemberEntity entity) {
        final PersonName name;

        name = new PersonName(entity.getPerson()
            .getFirstName(),
            entity.getPerson()
                .getLastName());
        return new Member(entity.getPerson()
            .getNumber(),
            entity.getPerson()
                .getIdentifier(),
            name, entity.getActive(), entity.getPerson()
                .getPhone());
    }

    private final MemberEntity toEntity(final Member data) {
        final PersonEntity person;

        // FIXME: load the person if it exists
        person = PersonEntity.builder()
            .withNumber(data.number())
            .withIdentifier(data.identifier())
            .withFirstName(data.name()
                .firstName())
            .withLastName(data.name()
                .lastName())
            .withPhone(data.phone())
            .build();
        return MemberEntity.builder()
            .withPerson(person)
            .withActive(data.active())
            .build();
    }

}
