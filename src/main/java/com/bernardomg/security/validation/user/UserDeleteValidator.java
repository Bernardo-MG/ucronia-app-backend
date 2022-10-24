
package com.bernardomg.security.validation.user;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.security.validation.user.rule.UserIdExistsValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class UserDeleteValidator implements Validator<Long> {

    private final Validator<Long> validator;

    public UserDeleteValidator(final UserIdExistsValidationRule userIdExistsValidationRule) {
        super();

        validator = new RuleValidator<>(Arrays.asList(userIdExistsValidationRule));
    }

    @Override
    public final void validate(final Long id) {
        validator.validate(id);
    }

}
