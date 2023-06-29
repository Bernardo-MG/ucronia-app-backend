
package com.bernardomg.association.test.fee.validation.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.model.request.FeeCreate;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.util.model.FeesCreate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@IntegrationTest
@DisplayName("ValidatedFeeCreate validation")
public class TestValidatedFeeCreateValidation {

    @Autowired
    private Validator validator;

    @Test
    @DisplayName("A DTO missing the date is invalid")
    public void validate_missingDate() {
        final FeeCreate                           request;
        final Set<ConstraintViolation<FeeCreate>> errors;
        final ConstraintViolation<FeeCreate>      error;

        request = FeesCreate.missingDate();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("date");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A DTO missing the member id is invalid")
    public void validate_missingMemberId() {
        final FeeCreate                           request;
        final Set<ConstraintViolation<FeeCreate>> errors;
        final ConstraintViolation<FeeCreate>      error;

        request = FeesCreate.missingMemberId();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("memberId");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A DTO missing the paid flag is invalid")
    public void validate_missingPaidFlag() {
        final FeeCreate                           request;
        final Set<ConstraintViolation<FeeCreate>> errors;
        final ConstraintViolation<FeeCreate>      error;

        request = FeesCreate.missingPaid();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("paid");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A valid DTO is valid")
    public void validate_valid() {
        final FeeCreate                           request;
        final Set<ConstraintViolation<FeeCreate>> errors;

        request = FeesCreate.paid();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
