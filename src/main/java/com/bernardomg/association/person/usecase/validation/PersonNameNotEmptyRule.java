
package com.bernardomg.association.person.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class PersonNameNotEmptyRule implements FieldRule<Person> {

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
