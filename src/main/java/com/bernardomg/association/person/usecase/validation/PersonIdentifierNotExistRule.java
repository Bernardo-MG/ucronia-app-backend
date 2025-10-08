
package com.bernardomg.association.person.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the person has a name.
 */
public final class PersonIdentifierNotExistRule implements FieldRule<Person> {

    /**
     * Logger for the class.
     */
    private static final Logger    log = LoggerFactory.getLogger(PersonIdentifierNotExistRule.class);

    private final PersonRepository personRepository;

    public PersonIdentifierNotExistRule(final PersonRepository personRepo) {
        super();

        personRepository = Objects.requireNonNull(personRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Person person) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(person.identifier()) || !personRepository.existsByIdentifier(person.identifier())) {
            failure = Optional.empty();
        } else {
            log.error("Existing identifier {}", person.identifier());
            fieldFailure = new FieldFailure("existing", "identifier", person.identifier());
            failure = Optional.of(fieldFailure);
        }

        return failure;
    }

}
