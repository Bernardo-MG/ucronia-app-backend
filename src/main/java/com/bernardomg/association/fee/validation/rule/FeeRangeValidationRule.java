
package com.bernardomg.association.fee.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.association.fee.model.FeeForm;
import com.bernardomg.validation.error.ValidationFailure;
import com.bernardomg.validation.error.ValidationRule;

public final class FeeRangeValidationRule implements ValidationRule<FeeForm> {

    public FeeRangeValidationRule() {
        super();
    }

    @Override
    public Collection<ValidationFailure> test(final FeeForm period) {
        final Collection<ValidationFailure> result;
        ValidationFailure                   error;

        result = new ArrayList<>();
        if ((period.getMonth() < 1) || (period.getMonth() > 12)) {
            // Start month out of range
            error = ValidationFailure.of("error.fee.invalidMonth");
            result.add(error);
        }

        return result;
    }

    @Override
    public final String toString() {
        return this.getClass()
            .getName();
    }

}
