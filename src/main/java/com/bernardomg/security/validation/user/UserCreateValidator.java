
package com.bernardomg.security.validation.user;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.security.model.User;
import com.bernardomg.security.validation.user.rule.UserUsernameNotExistsValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class UserCreateValidator implements Validator<User> {

    private final Validator<User> validator;

    public UserCreateValidator(final UserUsernameNotExistsValidationRule userUsernameNotExistsValidationRule) {
        super();

        validator = new RuleValidator<>(Arrays.asList(userUsernameNotExistsValidationRule));
    }

    @Override
    public final void validate(final User user) {
        validator.validate(user);
    }

}
