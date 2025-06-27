
package com.bernardomg.association.person.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.domain.repository.ContactMethodRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the contact method name is not already registered.
 */
public final class ContactMethodNameNotExistsRule implements FieldRule<ContactMethod> {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(ContactMethodNameNotExistsRule.class);

    private final ContactMethodRepository contactMethodRepository;

    public ContactMethodNameNotExistsRule(final ContactMethodRepository contactMethodRepo) {
        super();

        contactMethodRepository = Objects.requireNonNull(contactMethodRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final ContactMethod contactMethod) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (contactMethodRepository.existsByName(contactMethod.name())) {
            log.error("Existing contact method name {}", contactMethod.name());
            fieldFailure = new FieldFailure("existing", "name", contactMethod.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
