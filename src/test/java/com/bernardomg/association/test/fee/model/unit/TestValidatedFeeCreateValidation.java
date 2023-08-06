
package com.bernardomg.association.test.fee.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.fee.model.request.FeeCreate;
import com.bernardomg.association.test.fee.util.model.FeesCreate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("ValidatedFeeCreate validation")
class TestValidatedFeeCreateValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO missing the description is invalid")
    void validate_missingDescription() {
        final FeeCreate                           request;
        final Set<ConstraintViolation<FeeCreate>> errors;
        final ConstraintViolation<FeeCreate>      error;

        request = FeesCreate.missingDescription();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("description");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A DTO missing the fee dates is invalid")
    void validate_missingFeeDates() {
        final FeeCreate                           request;
        final Set<ConstraintViolation<FeeCreate>> errors;
        final ConstraintViolation<FeeCreate>      error;

        request = FeesCreate.missingFeeDates();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("feeDates");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A DTO missing the member id is invalid")
    void validate_missingMemberId() {
        final FeeCreate                           request;
        final Set<ConstraintViolation<FeeCreate>> errors;
        final ConstraintViolation<FeeCreate>      error;

        request = FeesCreate.missingMemberId();

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
    @DisplayName("A DTO missing the payment date is invalid")
    void validate_missingPaymentDate() {
        final FeeCreate                           request;
        final Set<ConstraintViolation<FeeCreate>> errors;
        final ConstraintViolation<FeeCreate>      error;

        request = FeesCreate.missingPaymentDate();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("paymentDate");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A valid DTO is valid")
    void validate_valid() {
        final FeeCreate                           request;
        final Set<ConstraintViolation<FeeCreate>> errors;

        request = FeesCreate.valid();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
