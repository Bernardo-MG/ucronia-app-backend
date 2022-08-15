
package com.bernardomg.association.memberperiod.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.association.memberperiod.model.MemberPeriod;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

public final class PeriodRangeOrderValidationRule implements ValidationRule<MemberPeriod> {

    public PeriodRangeOrderValidationRule() {
        super();
    }

    @Override
    public Collection<ValidationError> test(final MemberPeriod period) {
        final Collection<ValidationError> result;
        final ValidationError             error;

        result = new ArrayList<>();
        if (period.getStartYear() > period.getEndYear()) {
            // Starting year after end year
            error = ValidationError.of("error.memberPeriod.startYearAfterEndYear");
            result.add(error);
        } else if ((period.getStartYear()
            .equals(period.getEndYear())) && (period.getStartMonth() > period.getEndMonth())) {
            // Same year
            // Starting month after end month
            error = ValidationError.of("error.memberPeriod.startMonthAfterEndMonth");
            result.add(error);
        }

        return result;
    }

}
