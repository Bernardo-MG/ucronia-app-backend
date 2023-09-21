
package com.bernardomg.association.membership.test.fee.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.membership.fee.model.request.FeesPayment;
import com.bernardomg.association.membership.test.fee.util.model.FeesCreate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("ValidatedFeeCreate validation")
class TestValidatedFeeCreateValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO missing the fee dates is invalid")
    void validate_missingFeeDates() {
        final FeesPayment                           request;
        final Set<ConstraintViolation<FeesPayment>> errors;
        final ConstraintViolation<FeesPayment>      error;

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
        final FeesPayment                           request;
        final Set<ConstraintViolation<FeesPayment>> errors;
        final ConstraintViolation<FeesPayment>      error;

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
        final FeesPayment                           request;
        final Set<ConstraintViolation<FeesPayment>> errors;
        final ConstraintViolation<FeesPayment>      error;

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
    @DisplayName("A DTO with a null fee date is invalid")
    void validate_nullFeeDate() {
        final FeesPayment                           request;
        final Set<ConstraintViolation<FeesPayment>> errors;
        final ConstraintViolation<FeesPayment>      error;

        request = FeesCreate.nullFeeDate();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("feeDates[].<iterable element>");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A valid DTO is valid")
    void validate_valid() {
        final FeesPayment                           request;
        final Set<ConstraintViolation<FeesPayment>> errors;

        request = FeesCreate.valid();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
