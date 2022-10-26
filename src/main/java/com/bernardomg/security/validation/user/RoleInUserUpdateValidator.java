
package com.bernardomg.security.validation.user;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.security.validation.user.rule.UserRoleIdExistsValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class RoleInUserUpdateValidator implements Validator<Long> {

    private final Validator<Long> validator;

    public RoleInUserUpdateValidator(final UserRoleIdExistsValidationRule roleIdExistsValidationRule) {
        super();

        validator = new RuleValidator<>(Arrays.asList(roleIdExistsValidationRule));
    }

    @Override
    public final void validate(final Long id) {
        validator.validate(id);
    }

}
