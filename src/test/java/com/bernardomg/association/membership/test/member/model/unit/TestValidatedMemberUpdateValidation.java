
package com.bernardomg.association.membership.test.member.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.membership.member.model.request.MemberUpdate;
import com.bernardomg.association.membership.test.member.util.model.MembersUpdate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("ValidatedMemberUpdate validation")
class TestValidatedMemberUpdateValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO missing the name is invalid")
    void validate_missingName() {
        final MemberUpdate                           request;
        final Set<ConstraintViolation<MemberUpdate>> errors;
        final ConstraintViolation<MemberUpdate>      error;

        request = MembersUpdate.missingName();

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
        final MemberUpdate                           request;
        final Set<ConstraintViolation<MemberUpdate>> errors;

        request = MembersUpdate.valid();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
