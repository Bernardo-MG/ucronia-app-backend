
package com.bernardomg.association.balance.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.association.balance.model.Payment;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

public final class PaymentMonthRangeValidationRule implements ValidationRule<Payment> {

    public PaymentMonthRangeValidationRule() {
        super();
    }

    @Override
    public Collection<ValidationError> test(final Payment payment) {
        final Collection<ValidationError> result;
        ValidationError                   error;

        result = new ArrayList<>();
        if ((payment.getMonth() < 1) || (payment.getMonth() > 12)) {
            // Start month out of range
            error = ValidationError.of("error.payment.invalidMonth");
            result.add(error);
        }

        return result;
    }

}
