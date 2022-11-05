
package com.bernardomg.security.data.validation.role;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.validation.role.rule.RoleNameNotExistsValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class RoleCreateValidator implements Validator<Role> {

    private final Validator<Role> validator;

    public RoleCreateValidator(final RoleNameNotExistsValidationRule roleNameNotExistsValidationRule) {
        super();

        validator = new RuleValidator<>(Arrays.asList(roleNameNotExistsValidationRule));
    }

    @Override
    public final void validate(final Role role) {
        validator.validate(role);
    }

}
