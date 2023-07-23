
package com.bernardomg.security.password.recovery.test.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.password.recovery.model.request.DtoPasswordRecoveryRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("DtoPasswordRecovery validation")
class TestDtoPasswordRecoveryValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO with an invalid email is invalid")
    void validate_invalidEmail() {
        final DtoPasswordRecoveryRequest                           passwordRecovery;
        final Set<ConstraintViolation<DtoPasswordRecoveryRequest>> errors;
        final ConstraintViolation<DtoPasswordRecoveryRequest>      error;

        passwordRecovery = new DtoPasswordRecoveryRequest();
        passwordRecovery.setEmail("abc");

        errors = validator.validate(passwordRecovery);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("email");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo("abc");
    }

    @Test
    @DisplayName("A DTO missing the email is invalid")
    void validate_noEmail() {
        final DtoPasswordRecoveryRequest                           passwordRecovery;
        final Set<ConstraintViolation<DtoPasswordRecoveryRequest>> errors;
        final ConstraintViolation<DtoPasswordRecoveryRequest>      error;

        passwordRecovery = new DtoPasswordRecoveryRequest();
        passwordRecovery.setEmail(null);

        errors = validator.validate(passwordRecovery);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("email");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A valid DTO is valid")
    void validate_valid() {
        final DtoPasswordRecoveryRequest                           passwordRecovery;
        final Set<ConstraintViolation<DtoPasswordRecoveryRequest>> errors;

        passwordRecovery = new DtoPasswordRecoveryRequest();
        passwordRecovery.setEmail("email@somewhere.com");

        errors = validator.validate(passwordRecovery);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
