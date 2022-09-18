
package com.bernardomg.association.crud.fee.validation;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.association.crud.fee.model.FeeForm;
import com.bernardomg.association.crud.fee.validation.rule.FeeMemberExistsValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class FeeValidator implements Validator<FeeForm> {

    private final Validator<FeeForm> validator;

    public FeeValidator(final FeeMemberExistsValidationRule periodMemberExists) {
        super();

        validator = new RuleValidator<>(Arrays.asList(periodMemberExists));
    }

    @Override
    public final void validate(final FeeForm period) {
        validator.validate(period);
    }

}
