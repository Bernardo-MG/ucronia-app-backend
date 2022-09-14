
package com.bernardomg.association.test.fee.unit.validation;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.fee.model.DtoFeeForm;
import com.bernardomg.association.fee.model.FeeForm;
import com.bernardomg.association.fee.validation.rule.FeeRangeValidationRule;
import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.validation.ValidationRule;

@DisplayName("Fee range validation rule")
public class TestFeeRangeValidationRule {

    private final ValidationRule<FeeForm> validator = new FeeRangeValidationRule();

    public TestFeeRangeValidationRule() {
        super();
    }

    @Test
    @DisplayName("Rejects the end month when it is above limits")
    public final void testValidator_EndMonthAbove() throws Exception {
        final Collection<Failure> failures;
        final DtoFeeForm          fee;
        final FieldFailure        failure;

        fee = new DtoFeeForm();
        fee.setMonth(13);

        failures = validator.test(fee);

        Assertions.assertEquals(1, failures.size());

        failure = (FieldFailure) failures.iterator()
            .next();
        Assertions.assertEquals("error.fee.invalidMonth", failure.getMessage());
        Assertions.assertEquals("feeForm", failure.getObject());
        Assertions.assertEquals("month", failure.getField());
        Assertions.assertEquals(13, failure.getValue());
    }

    @Test
    @DisplayName("Rejects the end month when it is below limits")
    public final void testValidator_EndMonthBelow() throws Exception {
        final Collection<Failure> failures;
        final DtoFeeForm          fee;
        final FieldFailure        failure;

        fee = new DtoFeeForm();
        fee.setMonth(0);

        failures = validator.test(fee);

        Assertions.assertEquals(1, failures.size());

        failure = (FieldFailure) failures.iterator()
            .next();
        Assertions.assertEquals("error.fee.invalidMonth", failure.getMessage());
        Assertions.assertEquals("feeForm", failure.getObject());
        Assertions.assertEquals("month", failure.getField());
        Assertions.assertEquals(0, failure.getValue());
    }

    @Test
    @DisplayName("Accepts a valid month")
    public final void testValidator_Valid() throws Exception {
        final Collection<Failure> failures;
        final DtoFeeForm          fee;

        fee = new DtoFeeForm();
        fee.setMonth(3);

        failures = validator.test(fee);

        Assertions.assertEquals(0, failures.size());
    }

}
