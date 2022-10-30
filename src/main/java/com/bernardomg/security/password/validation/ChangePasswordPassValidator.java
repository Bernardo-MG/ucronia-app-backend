
package com.bernardomg.security.password.validation;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.security.password.validation.rule.PasswordNotEmptyValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class ChangePasswordPassValidator implements Validator<String> {

    private final Validator<String> validator;

    public ChangePasswordPassValidator(final PasswordNotEmptyValidationRule passwordNotEmptyValidationRule) {
        super();

        validator = new RuleValidator<>(Arrays.asList(passwordNotEmptyValidationRule));
    }

    @Override
    public final void validate(final String user) {
        validator.validate(user);
    }

}
