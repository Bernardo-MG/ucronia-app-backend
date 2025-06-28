
package com.bernardomg.association.person.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.Person.PersonContact;
import com.bernardomg.association.person.domain.repository.ContactMethodRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the contact method exists.
 */
public final class PersonContactMethodExistsRule implements FieldRule<Person> {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(PersonContactMethodExistsRule.class);

    private final ContactMethodRepository contactMethodRepository;

    public PersonContactMethodExistsRule(final ContactMethodRepository contactMethodRepo) {
        super();

        contactMethodRepository = Objects.requireNonNull(contactMethodRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Person person) {
        // TODO: what about multiple failues?
        return person.contacts()
            .stream()
            .map(PersonContact::method)
            .map(this::check)
            .filter(Optional::isPresent)
            .map(o -> o.orElse(null))
            .findFirst();
    }

    private final Optional<FieldFailure> check(final ContactMethod contactMethod) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (!contactMethodRepository.exists(contactMethod.number())) {
            log.error("Existing contact method name {}", contactMethod.name());
            fieldFailure = new FieldFailure("notExisting", "contact", contactMethod.number());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
