
package com.bernardomg.association.member.validation;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.association.member.model.MemberPeriod;
import com.bernardomg.association.member.validation.rule.PeriodMemberExistsValidationRule;
import com.bernardomg.association.member.validation.rule.PeriodMonthRangeValidationRule;
import com.bernardomg.association.member.validation.rule.PeriodNotOverlappedValidationRule;
import com.bernardomg.association.member.validation.rule.PeriodRangeOrderValidationRule;
import com.bernardomg.validation.error.RuleValidator;
import com.bernardomg.validation.error.Validator;

@Component
public final class MemberPeriodValidator implements Validator<MemberPeriod> {

    private final Validator<MemberPeriod> validator;

    public MemberPeriodValidator(final PeriodMemberExistsValidationRule periodMemberExists,
            final PeriodNotOverlappedValidationRule periodNotOverlapped) {
        super();

        validator = new RuleValidator<>(Arrays.asList(new PeriodRangeOrderValidationRule(),
            new PeriodMonthRangeValidationRule(), periodMemberExists, periodNotOverlapped));
    }

    @Override
    public final void validate(final MemberPeriod period) {
        validator.validate(period);
    }

}
