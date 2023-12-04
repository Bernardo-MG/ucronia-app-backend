
package com.bernardomg.association.membership.test.member.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.membership.member.model.request.MemberCreate;
import com.bernardomg.association.membership.test.member.util.model.MembersCreate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("ValidatedMemberCreate validation")
class TestValidatedMemberCreateValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO missing the name is invalid")
    void validate_missingName() {
        final MemberCreate                           request;
        final Set<ConstraintViolation<MemberCreate>> errors;
        final ConstraintViolation<MemberCreate>      error;

        request = MembersCreate.missingName();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("name");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A valid DTO is valid")
    void validate_valid() {
        final MemberCreate                           request;
        final Set<ConstraintViolation<MemberCreate>> errors;

        request = MembersCreate.valid();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
