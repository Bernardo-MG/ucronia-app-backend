
package com.bernardomg.association.test.fee.validation.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.util.model.FeesUpdate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@IntegrationTest
@DisplayName("ValidatedFeeUpdate validation")
public class TestValidatedFeeUpdateValidation {

    @Autowired
    private Validator validator;

    @Test
    @DisplayName("A DTO missing the date is invalid")
    public void validate_missingDate() {
        final FeeUpdate                           request;
        final Set<ConstraintViolation<FeeUpdate>> errors;
        final ConstraintViolation<FeeUpdate>      error;

        request = FeesUpdate.missingDate();

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
        final FeeUpdate                           request;
        final Set<ConstraintViolation<FeeUpdate>> errors;
        final ConstraintViolation<FeeUpdate>      error;

        request = FeesUpdate.missingMemberId();

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
        final FeeUpdate                           request;
        final Set<ConstraintViolation<FeeUpdate>> errors;
        final ConstraintViolation<FeeUpdate>      error;

        request = FeesUpdate.missingPaid();

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
        final FeeUpdate                           request;
        final Set<ConstraintViolation<FeeUpdate>> errors;

        request = FeesUpdate.paid();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
