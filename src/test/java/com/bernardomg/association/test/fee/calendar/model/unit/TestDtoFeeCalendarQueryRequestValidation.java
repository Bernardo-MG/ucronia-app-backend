
package com.bernardomg.association.test.fee.calendar.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.fee.calendar.model.request.DtoFeeCalendarQueryRequest;
import com.bernardomg.association.test.fee.calendar.util.model.FeeCalendarsQuery;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("DtoFeeCalendarQueryRequest validation")
class TestDtoFeeCalendarQueryRequestValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO missing the active flag is invalid")
    void validate_noActive() {
        final DtoFeeCalendarQueryRequest                           request;
        final Set<ConstraintViolation<DtoFeeCalendarQueryRequest>> errors;
        final ConstraintViolation<DtoFeeCalendarQueryRequest>      error;

        request = FeeCalendarsQuery.missingOnlyActive();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("onlyActive");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A valid DTO is valid")
    void validate_valid() {
        final DtoFeeCalendarQueryRequest                           request;
        final Set<ConstraintViolation<DtoFeeCalendarQueryRequest>> errors;

        request = FeeCalendarsQuery.onlyActive();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
