
package com.bernardomg.association.person.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the donor has a name.
 */
public final class PersonNameNotEmptyRule implements FieldRule<Person> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(PersonNameNotEmptyRule.class);

    public PersonNameNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Person person) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(person.name()
            .firstName())) {
            log.error("Empty name");
            fieldFailure = new FieldFailure("empty", "name.firstName", person.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
