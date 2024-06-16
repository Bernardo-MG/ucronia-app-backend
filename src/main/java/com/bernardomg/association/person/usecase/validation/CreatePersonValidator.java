
package com.bernardomg.association.person.usecase.validation;

import java.util.List;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.validation.validator.AbstractFieldRuleValidator;

public final class CreatePersonValidator extends AbstractFieldRuleValidator<Person> {

    public CreatePersonValidator() {
        super(List.of(new PersonNameNotEmptyRule()));
    }

}
