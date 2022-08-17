
package com.bernardomg.association.test.paidmonth.unit.validation;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.paidmonth.model.DtoPaidMonth;
import com.bernardomg.association.paidmonth.model.PaidMonth;
import com.bernardomg.association.paidmonth.validation.rule.PaidMonthRangeValidationRule;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

@DisplayName("Paid month range validation rule")
public class TestPaidMonthRangeValidationRule {

    private final ValidationRule<PaidMonth> validator = new PaidMonthRangeValidationRule();

    public TestPaidMonthRangeValidationRule() {
        super();
    }

    @Test
    @DisplayName("Rejects the end month when it is above limits")
    public final void testValidator_EndMonthAbove() throws Exception {
        final Collection<ValidationError> error;
        final DtoPaidMonth                period;

        period = new DtoPaidMonth();
        period.setMonth(13);

        error = validator.test(period);

        Assertions.assertEquals(1, error.size());
        Assertions.assertEquals("error.paidMonth.invalidMonth", error.iterator()
            .next()
            .getError());
    }

    @Test
    @DisplayName("Rejects the end month when it is below limits")
    public final void testValidator_EndMonthBelow() throws Exception {
        final Collection<ValidationError> error;
        final DtoPaidMonth                period;

        period = new DtoPaidMonth();
        period.setMonth(0);

        error = validator.test(period);

        Assertions.assertEquals(1, error.size());
        Assertions.assertEquals("error.paidMonth.invalidMonth", error.iterator()
            .next()
            .getError());
    }

    @Test
    @DisplayName("Accepts a valid month")
    public final void testValidator_Valid() throws Exception {
        final Collection<ValidationError> error;
        final DtoPaidMonth                period;

        period = new DtoPaidMonth();
        period.setMonth(3);

        error = validator.test(period);

        Assertions.assertEquals(0, error.size());
    }

}
