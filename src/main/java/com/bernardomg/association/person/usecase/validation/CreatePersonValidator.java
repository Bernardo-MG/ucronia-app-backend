
package com.bernardomg.association.person.usecase.validation;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.AbstractValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreatePersonValidator extends AbstractValidator<Person> {

    public CreatePersonValidator() {
        super();
    }

    @Override
    protected final void checkRules(final Person person, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        if (StringUtils.isBlank(person.getName()
            .getFirstName())) {
            log.error("Empty name");
            failure = FieldFailure.of("name", "empty", person.getName());
            failures.add(failure);
        }
    }

}
