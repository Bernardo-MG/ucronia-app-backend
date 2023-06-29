
package com.bernardomg.association.test.member.validation.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.member.model.request.MemberUpdate;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.member.util.model.MembersUpdate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@IntegrationTest
@DisplayName("ValidatedMemberUpdate validation")
public class TestValidatedMemberUpdateValidation {

    @Autowired
    private Validator validator;

    @Test
    @DisplayName("A DTO missing the active flag is invalid")
    public void validate_missingActive() {
        final MemberUpdate                           request;
        final Set<ConstraintViolation<MemberUpdate>> errors;
        final ConstraintViolation<MemberUpdate>      error;

        request = MembersUpdate.missingActive();

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
        final MemberUpdate                           request;
        final Set<ConstraintViolation<MemberUpdate>> errors;
        final ConstraintViolation<MemberUpdate>      error;

        request = MembersUpdate.missingName();

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
        final MemberUpdate                           request;
        final Set<ConstraintViolation<MemberUpdate>> errors;

        request = MembersUpdate.active();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
