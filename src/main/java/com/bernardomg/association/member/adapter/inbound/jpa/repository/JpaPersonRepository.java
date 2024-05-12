
package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.member.domain.model.Person;
import com.bernardomg.association.member.domain.model.PersonName;
import com.bernardomg.association.member.domain.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaPersonRepository implements PersonRepository {

    private final PersonSpringRepository personSpringRepository;

    public JpaPersonRepository(final PersonSpringRepository personSpringRepo) {
        super();

        personSpringRepository = personSpringRepo;
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
    public final Person save(final Person person) {
        final Optional<PersonEntity> existing;
        final PersonEntity           entity;
        final PersonEntity           created;
        final Person                 saved;

        log.debug("Saving person {}", person);

        entity = toEntity(person);

        existing = personSpringRepository.findByNumber(person.getNumber());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = personSpringRepository.save(entity);

        saved = personSpringRepository.findByNumber(created.getNumber())
            .map(this::toDomain)
            .get();

        log.debug("Saved person {}", saved);

        return saved;
    }

    private final Person toDomain(final PersonEntity entity) {
        final PersonName memberName;

        memberName = PersonName.builder()
            .withFirstName(entity.getName())
            .withLastName(entity.getSurname())
            .build();
        return Person.builder()
            .withNumber(entity.getNumber())
            .withName(memberName)
            .withIdentifier(entity.getIdentifier())
            .withPhone(entity.getPhone())
            .build();
    }

    private final PersonEntity toEntity(final Person data) {
        return PersonEntity.builder()
            .withNumber(data.getNumber())
            .withName(data.getName()
                .getFirstName())
            .withSurname(data.getName()
                .getLastName())
            .withIdentifier(data.getIdentifier())
            .withPhone(data.getPhone())
            .build();
    }

}
