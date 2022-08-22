
package com.bernardomg.association.test.fee.unit.validation;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.fee.model.DtoFeeForm;
import com.bernardomg.association.fee.model.FeeForm;
import com.bernardomg.association.fee.validation.rule.FeeRangeValidationRule;
import com.bernardomg.validation.error.ValidationError;
import com.bernardomg.validation.error.ValidationRule;

@DisplayName("Fee range validation rule")
public class TestFeeRangeValidationRule {

    private final ValidationRule<FeeForm> validator = new FeeRangeValidationRule();

    public TestFeeRangeValidationRule() {
        super();
    }

    @Test
    @DisplayName("Rejects the end month when it is above limits")
    public final void testValidator_EndMonthAbove() throws Exception {
        final Collection<ValidationError> error;
        final DtoFeeForm                  period;

        period = new DtoFeeForm();
        period.setMonth(13);

        error = validator.test(period);

        Assertions.assertEquals(1, error.size());
        Assertions.assertEquals("error.fee.invalidMonth", error.iterator()
            .next()
            .getError());
    }

    @Test
    @DisplayName("Rejects the end month when it is below limits")
    public final void testValidator_EndMonthBelow() throws Exception {
        final Collection<ValidationError> error;
        final DtoFeeForm                  period;

        period = new DtoFeeForm();
        period.setMonth(0);

        error = validator.test(period);

        Assertions.assertEquals(1, error.size());
        Assertions.assertEquals("error.fee.invalidMonth", error.iterator()
            .next()
            .getError());
    }

    @Test
    @DisplayName("Accepts a valid month")
    public final void testValidator_Valid() throws Exception {
        final Collection<ValidationError> error;
        final DtoFeeForm                  period;

        period = new DtoFeeForm();
        period.setMonth(3);

        error = validator.test(period);

        Assertions.assertEquals(0, error.size());
    }

}
