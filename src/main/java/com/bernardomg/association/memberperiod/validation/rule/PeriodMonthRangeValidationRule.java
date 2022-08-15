
package com.bernardomg.association.memberperiod.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.association.memberperiod.model.MemberPeriod;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

public final class PeriodMonthRangeValidationRule implements ValidationRule<MemberPeriod> {

    public PeriodMonthRangeValidationRule() {
        super();
    }

    @Override
    public Collection<ValidationError> test(final MemberPeriod period) {
        final Collection<ValidationError> result;
        ValidationError                   error;

        result = new ArrayList<>();
        if ((period.getStartMonth() < 1) || (period.getStartMonth() > 12)) {
            // Start month out of range
            error = ValidationError.of("error.memberPeriod.invalidStartMonth");
            result.add(error);
        }
        if ((period.getEndMonth() < 1) || (period.getEndMonth() > 12)) {
            // Start month out of range
            error = ValidationError.of("error.memberPeriod.invalidEndMonth");
            result.add(error);
        }

        return result;
    }

}
