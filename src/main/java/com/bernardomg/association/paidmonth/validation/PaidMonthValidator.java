
package com.bernardomg.association.paidmonth.validation;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.association.paidmonth.model.PaidMonth;
import com.bernardomg.association.paidmonth.validation.rule.PaidMonthContainedInPeriodValidationRule;
import com.bernardomg.association.paidmonth.validation.rule.PaidMonthMemberExistsValidationRule;
import com.bernardomg.association.paidmonth.validation.rule.PaidMonthRangeValidationRule;
import com.bernardomg.validation.error.RuleValidator;
import com.bernardomg.validation.error.Validator;

@Component
public final class PaidMonthValidator implements Validator<PaidMonth> {

    private final Validator<PaidMonth> validator;

    public PaidMonthValidator(final PaidMonthMemberExistsValidationRule periodMemberExists,
            final PaidMonthContainedInPeriodValidationRule paidMonthContainedInPeriod) {
        super();

        validator = new RuleValidator<>(
            Arrays.asList(new PaidMonthRangeValidationRule(), periodMemberExists, paidMonthContainedInPeriod));
    }

    @Override
    public final void validate(final PaidMonth period) {
        validator.validate(period);
    }

}
