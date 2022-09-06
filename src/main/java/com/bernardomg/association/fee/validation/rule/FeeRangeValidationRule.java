
package com.bernardomg.association.fee.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.association.fee.model.FeeForm;
import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.validation.ValidationRule;

public final class FeeRangeValidationRule implements ValidationRule<FeeForm> {

    public FeeRangeValidationRule() {
        super();
    }

    @Override
    public Collection<Failure> test(final FeeForm period) {
        final Collection<Failure> result;
        Failure                   error;

        result = new ArrayList<>();
        if ((period.getMonth() < 1) || (period.getMonth() > 12)) {
            // Start month out of range
            error = Failure.of("error.fee.invalidMonth");
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
