
package com.bernardomg.association.member.validation;

import java.util.Arrays;

import com.bernardomg.association.member.model.MemberPeriod;
import com.bernardomg.association.member.validation.rule.PeriodMonthRangeValidationRule;
import com.bernardomg.association.member.validation.rule.PeriodRangeOrderValidationRule;
import com.bernardomg.validation.error.RuleValidator;
import com.bernardomg.validation.error.Validator;

public final class MemberPeriodValidator implements Validator<MemberPeriod> {

    private final Validator<MemberPeriod> validator;

    public MemberPeriodValidator() {
        super();

        validator = new RuleValidator<>(
            Arrays.asList(new PeriodRangeOrderValidationRule(), new PeriodMonthRangeValidationRule()));
    }

    @Override
    public final void validate(final MemberPeriod period) {
        validator.validate(period);
    }

}
