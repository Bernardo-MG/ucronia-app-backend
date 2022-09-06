
package com.bernardomg.association.fee.validation;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.association.fee.model.FeeForm;
import com.bernardomg.association.fee.validation.rule.FeeMemberExistsValidationRule;
import com.bernardomg.association.fee.validation.rule.FeeRangeValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class FeeValidator implements Validator<FeeForm> {

    private final Validator<FeeForm> validator;

    public FeeValidator(final FeeMemberExistsValidationRule periodMemberExists) {
        super();

        validator = new RuleValidator<>(Arrays.asList(new FeeRangeValidationRule(), periodMemberExists));
    }

    @Override
    public final void validate(final FeeForm period) {
        validator.validate(period);
    }

}
