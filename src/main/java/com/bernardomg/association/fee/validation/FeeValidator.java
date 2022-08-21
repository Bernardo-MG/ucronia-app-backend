
package com.bernardomg.association.fee.validation;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.association.fee.model.Fee;
import com.bernardomg.association.fee.validation.rule.FeeMemberExistsValidationRule;
import com.bernardomg.association.fee.validation.rule.FeeRangeValidationRule;
import com.bernardomg.validation.error.RuleValidator;
import com.bernardomg.validation.error.Validator;

@Component
public final class FeeValidator implements Validator<Fee> {

    private final Validator<Fee> validator;

    public FeeValidator(final FeeMemberExistsValidationRule periodMemberExists) {
        super();

        validator = new RuleValidator<>(Arrays.asList(new FeeRangeValidationRule(), periodMemberExists));
    }

    @Override
    public final void validate(final Fee period) {
        validator.validate(period);
    }

}
