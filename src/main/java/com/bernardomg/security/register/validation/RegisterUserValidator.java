
package com.bernardomg.security.register.validation;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.validation.user.rule.UserUsernameNotExistsValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class RegisterUserValidator implements Validator<User> {

    private final Validator<User> validator;

    public RegisterUserValidator(final UserUsernameNotExistsValidationRule userExistsValidationRule) {
        super();

        validator = new RuleValidator<>(Arrays.asList(userExistsValidationRule));
    }

    @Override
    public final void validate(final User user) {
        validator.validate(user);
    }

}
