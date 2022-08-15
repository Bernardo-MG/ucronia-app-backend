
package com.bernardomg.association.balance.validation;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.association.balance.validation.rule.PaymentMonthRangeValidationRule;
import com.bernardomg.association.payment.model.Payment;
import com.bernardomg.validation.error.RuleValidator;
import com.bernardomg.validation.error.Validator;

@Component
public final class PaymentValidator implements Validator<Payment> {

    private final Validator<Payment> validator;

    public PaymentValidator() {
        super();

        validator = new RuleValidator<>(Arrays.asList(new PaymentMonthRangeValidationRule()));
    }

    @Override
    public final void validate(final Payment period) {
        validator.validate(period);
    }

}
