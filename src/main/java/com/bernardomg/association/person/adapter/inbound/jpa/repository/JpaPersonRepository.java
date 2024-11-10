
package com.bernardomg.association.person.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.Person.Membership;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.domain.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaPersonRepository implements PersonRepository {

    private final PersonSpringRepository personSpringRepository;

    public JpaPersonRepository(final PersonSpringRepository personSpringRepo) {
        super();

        personSpringRepository = Objects.requireNonNull(personSpringRepo);
    }

    @Override
    public final void activate(final long number) {
        final Optional<PersonEntity> read;
        final PersonEntity           person;

        log.trace("Activating member {}", number);

        // TODO: throw an exception if it doesn't exist

        read = personSpringRepository.findByNumber(number);
        if (read.isPresent()) {
            person = read.get();
            person.setActive(true);
            personSpringRepository.save(person);

            log.trace("Activated member {}", number);
        }
    }

    @Override
    public final void activateAll(final Collection<Long> numbers) {
        final Collection<PersonEntity> read;

        log.trace("Activating members {}", numbers);

        read = personSpringRepository.findAllByNumberIn(numbers);
        read.forEach(p -> p.setActive(true));
        personSpringRepository.saveAll(read);

        log.trace("Activated members {}", numbers);
    }

    @Override
    public final void deactivate(final long number) {
        final Optional<PersonEntity> read;
        final PersonEntity           person;

        log.trace("Deactivating member {}", number);

        // TODO: throw an exception if it doesn't exist

        read = personSpringRepository.findByNumber(number);
        if (read.isPresent()) {
            person = read.get();
            person.setActive(false);
            personSpringRepository.save(person);

            log.trace("Deactivated member {}", number);
        }
    }

    @Override
    public final void deactivateAll(final Collection<Long> numbers) {
        final Collection<PersonEntity> read;

        log.trace("Deactivating members {}", numbers);

        read = personSpringRepository.findAllByNumberIn(numbers);
        read.forEach(p -> p.setActive(false));
        personSpringRepository.saveAll(read);

        log.trace("Deactivated members {}", numbers);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting fee {}", number);

        personSpringRepository.deleteByNumber(number);

        log.debug("Deleted fee {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if fee {} exists", number);

        exists = personSpringRepository.existsByNumber(number);

        log.debug("Fee {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Iterable<Person> findAll(final Pageable pageable) {
        final Page<Person> persons;

        log.debug("Finding all the persons");

        persons = personSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found all the persons: {}", persons);

        return persons;
    }

    @Override
    public final Collection<Person> findAllToRenew() {
        final Collection<Person> persons;

        log.debug("Finding all the members to renew");

        persons = personSpringRepository.findAllByMemberTrueAndRenewMembershipTrue()
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all the members to renew: {}", persons);

        return persons;
    }

    @Override
    public final Collection<Person> findAllWithRenewalMismatch() {
        final Collection<Person> persons;

        log.debug("Finding all the people with a renewal mismatch");

        persons = personSpringRepository.findAllWithRenewalMismatch()
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all the people with a renewal mismatch: {}", persons);

        return persons;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the transactions");

        number = personSpringRepository.findNextNumber();

        log.debug("Found next number for the transactions: {}", number);

        return number;
    }

    @Override
    public final Optional<Person> findOne(final Long number) {
        final Optional<Person> person;

        log.debug("Finding person with number {}", number);

        person = personSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found person with number {}: {}", number, person);

        return person;
    }

    @Override
    public final boolean isActive(final long number) {
        final Boolean active;

        log.trace("Checking if member {} is active", number);

        active = personSpringRepository.isActive(number);

        log.trace("Member {} is active: {}", number, active);

        return Boolean.TRUE.equals(active);
    }

    @Override
    public final Person save(final Person person) {
        final Optional<PersonEntity> existing;
        final PersonEntity           entity;
        final PersonEntity           created;
        final Person                 saved;

        log.debug("Saving person {}", person);

        entity = toEntity(person);

        existing = personSpringRepository.findByNumber(person.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        if (entity.getMember()) {
            entity.setMember(entity.getMember());
            entity.setActive(entity.getActive());
        }

        created = personSpringRepository.save(entity);

        // TODO: Why not returning the saved one?
        saved = personSpringRepository.findByNumber(created.getNumber())
            .map(this::toDomain)
            .get();

        log.debug("Saved person {}", saved);

        return saved;
    }

    private final Person toDomain(final PersonEntity entity) {
        final PersonName           name;
        final Optional<Membership> membership;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        if (!entity.getMember()) {
            membership = Optional.empty();
        } else {
            membership = Optional.of(new Membership(entity.getActive(), entity.getRenewMembership()));
        }
        return new Person(entity.getIdentifier(), entity.getNumber(), name, entity.getPhone(), membership);
    }

    private final PersonEntity toEntity(final Person data) {
        final boolean member;
        final boolean active;
        final boolean renew;

        if (data.membership()
            .isPresent()) {
            member = true;
            active = data.membership()
                .get()
                .active();
            renew = data.membership()
                .get()
                .renew();
        } else {
            member = false;
            active = true;
            renew = true;
        }
        return PersonEntity.builder()
            .withNumber(data.number())
            .withFirstName(data.name()
                .firstName())
            .withLastName(data.name()
                .lastName())
            .withIdentifier(data.identifier())
            .withPhone(data.phone())
            .withMember(member)
            .withActive(active)
            .withRenewMembership(renew)
            .build();
    }

}
