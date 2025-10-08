
package com.bernardomg.association.person.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the contact method has a name.
 */
public final class ContactMethodNameNotEmptyRule implements FieldRule<ContactMethod> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(ContactMethodNameNotEmptyRule.class);

    public ContactMethodNameNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final ContactMethod contactMethod) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(contactMethod.name())) {
            log.error("Empty name");
            fieldFailure = new FieldFailure("empty", "name", contactMethod.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
