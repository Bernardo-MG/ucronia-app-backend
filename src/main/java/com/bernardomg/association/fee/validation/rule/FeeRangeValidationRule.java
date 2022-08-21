
package com.bernardomg.association.fee.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.association.fee.model.Fee;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

public final class FeeRangeValidationRule implements ValidationRule<Fee> {

    public FeeRangeValidationRule() {
        super();
    }

    @Override
    public Collection<ValidationError> test(final Fee period) {
        final Collection<ValidationError> result;
        ValidationError                   error;

        result = new ArrayList<>();
        if ((period.getMonth() < 1) || (period.getMonth() > 12)) {
            // Start month out of range
            error = ValidationError.of("error.fee.invalidMonth");
            result.add(error);
        }

        return result;
    }

}
