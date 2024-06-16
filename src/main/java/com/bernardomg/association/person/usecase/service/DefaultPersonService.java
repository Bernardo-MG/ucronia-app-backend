
package com.bernardomg.association.person.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.usecase.validation.PersonNameNotEmptyRule;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the person service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
@Transactional
public final class DefaultPersonService implements PersonService {

    private final Validator<Person> createPersonValidator;

    private final PersonRepository  personRepository;

    public DefaultPersonService(final PersonRepository personRepo) {
        super();

        personRepository = Objects.requireNonNull(personRepo);
        createPersonValidator = new FieldRuleValidator<>(new PersonNameNotEmptyRule());
    }

    @Override
    public final Person create(final Person person) {
        final Person toCreate;
        final Long   number;

        log.debug("Creating person {}", person);

        // Set number
        number = personRepository.findNextNumber();

        toCreate = Person.builder()
            .withName(person.getName())
            .withNumber(number)
            .withIdentifier(person.getIdentifier())
            .withPhone(person.getPhone())
            .build();

        createPersonValidator.validate(toCreate);

        return personRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting person {}", number);

        if (!personRepository.exists(number)) {
            throw new MissingPersonException(number);
        }

        // TODO: Forbid deleting when there are relationships

        personRepository.delete(number);
    }

    @Override
    public final Iterable<Person> getAll(final Pageable pageable) {
        log.debug("Reading persons with pagination {}", pageable);

        return personRepository.findAll(pageable);
    }

    @Override
    public final Optional<Person> getOne(final long number) {
        final Optional<Person> person;

        log.debug("Reading person {}", number);

        person = personRepository.findOne(number);
        if (person.isEmpty()) {
            throw new MissingPersonException(number);
        }

        return person;
    }

    @Override
    public final Person update(final Person person) {
        final Optional<Person> existing;

        log.debug("Updating person {} using data {}", person.getNumber(), person);

        // TODO: Identificator and phone must be unique or empty
        // TODO: Apply the creation validations

        existing = personRepository.findOne(person.getNumber());
        if (existing.isEmpty()) {
            throw new MissingPersonException(person.getNumber());
        }

        return personRepository.save(person);
    }

}
