
package com.bernardomg.association.member.test.domain.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.member.domain.model.MemberChange;
import com.bernardomg.association.member.test.config.factory.MemberChanges;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("ValidatedMemberCreate validation")
class TestMemberChangesValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO missing the name is invalid")
    void validate_missingName() {
        final MemberChange                           request;
        final Set<ConstraintViolation<MemberChange>> errors;
        final ConstraintViolation<MemberChange>      error;

        request = MemberChanges.missingName();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("name.firstName");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A valid DTO is valid")
    void validate_valid() {
        final MemberChange                           request;
        final Set<ConstraintViolation<MemberChange>> errors;

        request = MemberChanges.valid();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
