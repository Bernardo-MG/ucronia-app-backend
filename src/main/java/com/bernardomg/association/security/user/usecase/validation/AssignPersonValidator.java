
package com.bernardomg.association.security.user.usecase.validation;

import java.util.List;

import com.bernardomg.association.security.user.domain.model.UserPerson;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.validation.validator.AbstractFieldRuleValidator;

public final class AssignPersonValidator extends AbstractFieldRuleValidator<UserPerson> {

    public AssignPersonValidator(final UserPersonRepository userPersonRepository) {
        super(List.of(new UserPersonNameNotEmptyRule(userPersonRepository)));
    }

}
