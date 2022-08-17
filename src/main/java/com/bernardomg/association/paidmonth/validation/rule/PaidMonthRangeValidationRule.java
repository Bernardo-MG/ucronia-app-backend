
package com.bernardomg.association.paidmonth.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.association.paidmonth.model.PaidMonth;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

public final class PaidMonthRangeValidationRule implements ValidationRule<PaidMonth> {

    public PaidMonthRangeValidationRule() {
        super();
    }

    @Override
    public Collection<ValidationError> test(final PaidMonth period) {
        final Collection<ValidationError> result;
        ValidationError                   error;

        result = new ArrayList<>();
        if ((period.getMonth() < 1) || (period.getMonth() > 12)) {
            // Start month out of range
            error = ValidationError.of("error.paidMonth.invalidMonth");
            result.add(error);
        }

        return result;
    }

}
