
package com.bernardomg.security.validation.register;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.security.model.User;
import com.bernardomg.security.validation.user.rule.UserUsernameExistsValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class ChangePasswordValidator implements Validator<User> {

    private final Validator<User> validator;

    public ChangePasswordValidator(final UserUsernameExistsValidationRule userExistsValidationRule) {
        super();

        validator = new RuleValidator<>(Arrays.asList(userExistsValidationRule));
    }

    @Override
    public final void validate(final User user) {
        validator.validate(user);
    }

}
