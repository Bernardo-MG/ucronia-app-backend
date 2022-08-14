
package com.bernardomg.association.member.validation;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.association.member.model.MemberPeriod;
import com.bernardomg.validation.DefaultValidationError;
import com.bernardomg.validation.ValidationError;
import com.bernardomg.validation.ValidationRule;

public final class PeriodRangeOrderValidationRule implements ValidationRule<MemberPeriod> {

    public PeriodRangeOrderValidationRule() {
        super();
    }

    @Override
    public Collection<ValidationError> test(final MemberPeriod period) {
        final Collection<ValidationError> result;
        DefaultValidationError            error;

        result = new ArrayList<>();
        if (period.getStartYear() > period.getEndYear()) {
            // Starting year after end year
            error = new DefaultValidationError();
            error.setError("error.memberPeriod.startYearAfterEndYear");
            result.add(error);
        } else if ((period.getStartYear()
            .equals(period.getEndYear())) && (period.getStartMonth() > period.getEndMonth())) {
            // Same year
            // Starting month after end month
            error = new DefaultValidationError();
            error.setError("error.memberPeriod.startMonthAfterEndMonth");
            result.add(error);
        }

        return result;
    }

}
