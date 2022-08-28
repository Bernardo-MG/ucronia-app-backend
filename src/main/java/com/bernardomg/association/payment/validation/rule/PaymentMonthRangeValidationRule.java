
package com.bernardomg.association.payment.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.association.payment.model.Payment;
import com.bernardomg.validation.error.ValidationFailure;
import com.bernardomg.validation.error.ValidationRule;

public final class PaymentMonthRangeValidationRule implements ValidationRule<Payment> {

    public PaymentMonthRangeValidationRule() {
        super();
    }

    @Override
    public Collection<ValidationFailure> test(final Payment payment) {
        final Collection<ValidationFailure> result;
        ValidationFailure                   error;

        result = new ArrayList<>();
        if ((payment.getMonth() < 1) || (payment.getMonth() > 12)) {
            // Start month out of range
            error = ValidationFailure.of("error.payment.invalidMonth");
            result.add(error);
        }

        return result;
    }

}
