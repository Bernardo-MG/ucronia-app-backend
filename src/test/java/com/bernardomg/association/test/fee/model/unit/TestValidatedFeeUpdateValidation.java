
package com.bernardomg.association.test.fee.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.test.fee.util.model.FeesUpdate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("ValidatedFeeUpdate validation")
class TestValidatedFeeUpdateValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO missing the date is invalid")
    void validate_missingDate() {
        final FeeUpdate                           request;
        final Set<ConstraintViolation<FeeUpdate>> errors;
        final ConstraintViolation<FeeUpdate>      error;

        request = FeesUpdate.missingDate();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("date");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A DTO missing the member id is invalid")
    void validate_missingMemberId() {
        final FeeUpdate                           request;
        final Set<ConstraintViolation<FeeUpdate>> errors;
        final ConstraintViolation<FeeUpdate>      error;

        request = FeesUpdate.missingMemberId();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("memberId");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A DTO missing the paid flag is invalid")
    void validate_missingPaidFlag() {
        final FeeUpdate                           request;
        final Set<ConstraintViolation<FeeUpdate>> errors;
        final ConstraintViolation<FeeUpdate>      error;

        request = FeesUpdate.missingPaid();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("paid");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A valid DTO is valid")
    void validate_valid() {
        final FeeUpdate                           request;
        final Set<ConstraintViolation<FeeUpdate>> errors;

        request = FeesUpdate.paid();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
