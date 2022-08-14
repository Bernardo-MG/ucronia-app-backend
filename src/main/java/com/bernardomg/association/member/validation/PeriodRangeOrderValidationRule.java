
package com.bernardomg.association.member.validation;

import java.util.Optional;

import com.bernardomg.association.member.model.MemberPeriod;
import com.bernardomg.validation.DefaultValidationError;
import com.bernardomg.validation.ValidationError;
import com.bernardomg.validation.ValidationRule;

public final class PeriodRangeOrderValidationRule implements ValidationRule<MemberPeriod> {

    public PeriodRangeOrderValidationRule() {
        super();
    }

    @Override
    public Optional<ValidationError> test(final MemberPeriod period) {
        final Optional<ValidationError> result;
        final DefaultValidationError    error;

        if (period.getStartYear() > period.getEndYear()) {
            // Starting year after end year
            error = new DefaultValidationError();
            error.setError("error.memberPeriod.startYearAfterEndYear");
            result = Optional.of(error);
        } else if ((period.getStartYear().equals(period.getEndYear())) && (period.getStartMonth() > period.getEndMonth())) {
            // Same year
            // Starting month after end month
            error = new DefaultValidationError();
            error.setError("error.memberPeriod.startMonthAfterEndMonth");
            result = Optional.of(error);
        } else {
            result = Optional.empty();
        }

        return result;
    }

}
