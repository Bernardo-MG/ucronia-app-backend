
package com.bernardomg.association.person.usecase.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.filter.PersonFilter;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.usecase.validation.PersonNameNotEmptyRule;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

/**
 * Default implementation of the person service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultPersonService implements PersonService {

    /**
     * Logger for the class.
     */
    private static final Logger     log = LoggerFactory.getLogger(DefaultPersonService.class);

    private final Validator<Person> createPersonValidator;

    private final Validator<Person> patchPersonValidator;

    private final PersonRepository  personRepository;

    private final Validator<Person> updatePersonValidator;

    public DefaultPersonService(final PersonRepository personRepo) {
        super();

        personRepository = Objects.requireNonNull(personRepo);
        createPersonValidator = new FieldRuleValidator<>(new PersonNameNotEmptyRule());
        updatePersonValidator = new FieldRuleValidator<>(new PersonNameNotEmptyRule());
        patchPersonValidator = new FieldRuleValidator<>(new PersonNameNotEmptyRule());
    }

    @Override
    public final Person create(final Person person) {
        final Person toCreate;
        final Long   number;

        log.debug("Creating person {}", person);

        // Set number
        number = personRepository.findNextNumber();

        toCreate = new Person(person.identifier(), number, person.name(), person.birthDate(), person.membership(),
            List.of());

        createPersonValidator.validate(toCreate);

        return personRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting person {}", number);

        if (!personRepository.exists(number)) {
            log.error("Missing person {}", number);
            throw new MissingPersonException(number);
        }

        personRepository.delete(number);
    }

    @Override
    public final Iterable<Person> getAll(final PersonFilter filter, final Pagination pagination,
            final Sorting sorting) {
        log.debug("Reading persons with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        return personRepository.findAll(filter, pagination, sorting);
    }

    @Override
    public final Optional<Person> getOne(final long number) {
        final Optional<Person> person;

        log.debug("Reading person {}", number);

        person = personRepository.findOne(number);
        if (person.isEmpty()) {
            log.error("Missing person {}", number);
            throw new MissingPersonException(number);
        }

        return person;
    }

    @Override
    public final Person patch(final Person person) {
        final Person existing;
        final Person toSave;

        log.debug("Patching member {} using data {}", person.number(), person);

        // TODO: Identificator and phone must be unique or empty
        // TODO: Apply the creation validations

        existing = personRepository.findOne(person.number())
            .orElseThrow(() -> {
                log.error("Missing person {}", person.number());
                throw new MissingPersonException(person.number());
            });

        toSave = copy(existing, person);

        patchPersonValidator.validate(toSave);

        return personRepository.save(toSave);
    }

    @Override
    public final Person update(final Person person) {
        log.debug("Updating person {} using data {}", person.number(), person);

        // TODO: Identificator and phone must be unique or empty
        // TODO: The membership maybe can't be removed

        if (!personRepository.exists(person.number())) {
            log.error("Missing person {}", person.number());
            throw new MissingPersonException(person.number());
        }

        updatePersonValidator.validate(person);

        return personRepository.save(person);
    }

    private final Person copy(final Person existing, final Person updated) {
        final PersonName name;

        if (updated.name() == null) {
            name = existing.name();
        } else {
            name = new PersonName(Optional.ofNullable(updated.name()
                .firstName())
                .orElse(existing.name()
                    .firstName()),
                Optional.ofNullable(updated.name()
                    .lastName())
                    .orElse(existing.name()
                        .lastName()));
        }
        return new Person(Optional.ofNullable(updated.identifier())
            .orElse(existing.identifier()),
            Optional.ofNullable(updated.number())
                .orElse(existing.number()),
            name, Optional.ofNullable(updated.birthDate())
                .orElse(existing.birthDate()),
            Optional.ofNullable(updated.membership())
                .orElse(Optional.empty()),
            List.of());
    }

}
