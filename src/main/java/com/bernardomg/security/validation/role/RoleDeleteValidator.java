
package com.bernardomg.security.validation.role;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.security.validation.role.rule.RoleIdExistsValidationRule;
import com.bernardomg.security.validation.role.rule.RoleIdNoUserValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class RoleDeleteValidator implements Validator<Long> {

    private final Validator<Long> validator;

    public RoleDeleteValidator(final RoleIdExistsValidationRule roleIdExistsValidationRule,
            final RoleIdNoUserValidationRule roleIdNoUserValidationRule) {
        super();

        validator = new RuleValidator<>(Arrays.asList(roleIdExistsValidationRule, roleIdNoUserValidationRule));
    }

    @Override
    public final void validate(final Long id) {
        validator.validate(id);
    }

}
