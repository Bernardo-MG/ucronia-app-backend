
package com.bernardomg.association.membership.test.calendar.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.membership.calendar.model.request.DtoFeeCalendarQueryRequest;
import com.bernardomg.association.membership.test.calendar.util.model.FeeCalendarsQuery;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("DtoFeeCalendarQueryRequest validation")
class TestDtoFeeCalendarQueryRequestValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

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
