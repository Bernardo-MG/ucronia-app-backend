
package com.bernardomg.security.validation.role;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.security.validation.role.rule.RoleIdExistsValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class RoleUpdateValidator implements Validator<Long> {

    private final Validator<Long> validator;

    public RoleUpdateValidator(final RoleIdExistsValidationRule roleIdExistsValidationRule) {
        super();

        validator = new RuleValidator<>(Arrays.asList(roleIdExistsValidationRule));
    }

    @Override
    public final void validate(final Long id) {
        validator.validate(id);
    }

}
