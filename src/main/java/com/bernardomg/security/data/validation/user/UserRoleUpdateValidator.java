
package com.bernardomg.security.data.validation.user;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.validation.user.rule.UserExistsValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class UserRoleUpdateValidator implements Validator<User> {

    private final Validator<User> validator;

    public UserRoleUpdateValidator(final UserExistsValidationRule userExistsValidationRule) {
        super();

        validator = new RuleValidator<>(Arrays.asList(userExistsValidationRule));
    }

    @Override
    public final void validate(final User user) {
        validator.validate(user);
    }

}
