
package com.bernardomg.association.test.balance.unit.validation;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.balance.model.DtoPayment;
import com.bernardomg.association.balance.model.Payment;
import com.bernardomg.association.balance.model.PaymentType;
import com.bernardomg.association.balance.validation.rule.PaymentMonthRangeValidationRule;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

@DisplayName("Paid month range validation rule")
public class TestPaymentMonthRangeValidationRule {

    private final ValidationRule<Payment> validator = new PaymentMonthRangeValidationRule();

    public TestPaymentMonthRangeValidationRule() {
        super();
    }

    @Test
    @DisplayName("Rejects the end month when it is above limits")
    public final void testValidator_EndMonthAbove() throws Exception {
        final Collection<ValidationError> error;
        final DtoPayment                  payment;

        payment = new DtoPayment();
        payment.setDescription("Payment");
        payment.setType(PaymentType.INCOME);
        payment.setQuantity(1l);
        payment.setDay(2);
        payment.setMonth(13);
        payment.setYear(4);

        error = validator.test(payment);

        Assertions.assertEquals(1, error.size());
        Assertions.assertEquals("error.payment.invalidMonth", error.iterator()
            .next()
            .getError());
    }

    @Test
    @DisplayName("Rejects the end month when it is below limits")
    public final void testValidator_EndMonthBelow() throws Exception {
        final Collection<ValidationError> error;
        final DtoPayment                  payment;

        payment = new DtoPayment();
        payment.setDescription("Payment");
        payment.setType(PaymentType.INCOME);
        payment.setQuantity(1l);
        payment.setDay(2);
        payment.setMonth(0);
        payment.setYear(4);

        error = validator.test(payment);

        Assertions.assertEquals(1, error.size());
        Assertions.assertEquals("error.payment.invalidMonth", error.iterator()
            .next()
            .getError());
    }

    @Test
    @DisplayName("Accepts a valid month")
    public final void testValidator_Valid() throws Exception {
        final Collection<ValidationError> error;
        final DtoPayment                  payment;

        payment = new DtoPayment();
        payment.setDescription("Payment");
        payment.setType(PaymentType.INCOME);
        payment.setQuantity(1l);
        payment.setDay(2);
        payment.setMonth(3);
        payment.setYear(4);

        error = validator.test(payment);

        Assertions.assertEquals(0, error.size());
    }

}
