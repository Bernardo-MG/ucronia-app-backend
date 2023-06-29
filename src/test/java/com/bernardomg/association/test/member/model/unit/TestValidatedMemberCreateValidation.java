
package com.bernardomg.association.test.member.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.member.model.request.MemberCreate;
import com.bernardomg.association.test.member.util.model.MembersCreate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("ValidatedMemberCreate validation")
public class TestValidatedMemberCreateValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO missing the active flag is invalid")
    public void validate_missingActive() {
        final MemberCreate                           request;
        final Set<ConstraintViolation<MemberCreate>> errors;
        final ConstraintViolation<MemberCreate>      error;

        request = MembersCreate.missingActive();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("active");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A DTO missing the name is invalid")
    public void validate_missingName() {
        final MemberCreate                           request;
        final Set<ConstraintViolation<MemberCreate>> errors;
        final ConstraintViolation<MemberCreate>      error;

        request = MembersCreate.missingName();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("name");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A valid DTO is valid")
    public void validate_valid() {
        final MemberCreate                           request;
        final Set<ConstraintViolation<MemberCreate>> errors;

        request = MembersCreate.active();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
