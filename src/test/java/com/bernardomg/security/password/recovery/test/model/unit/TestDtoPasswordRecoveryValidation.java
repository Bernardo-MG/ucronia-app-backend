
package com.bernardomg.security.password.recovery.test.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.password.recovery.model.DtoPasswordRecovery;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("DtoPasswordRecovery validation")
public class TestDtoPasswordRecoveryValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO with an invalid email is invalid")
    public void validate_invalidEmail() {
        final DtoPasswordRecovery                           passwordRecovery;
        final Set<ConstraintViolation<DtoPasswordRecovery>> errors;
        final ConstraintViolation<DtoPasswordRecovery>      error;

        passwordRecovery = new DtoPasswordRecovery();
        passwordRecovery.setEmail("abc");

        errors = validator.validate(passwordRecovery);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("email");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo("abc");
    }

    @Test
    @DisplayName("A DTO missing the email is invalid")
    public void validate_noEmail() {
        final DtoPasswordRecovery                           passwordRecovery;
        final Set<ConstraintViolation<DtoPasswordRecovery>> errors;
        final ConstraintViolation<DtoPasswordRecovery>      error;

        passwordRecovery = new DtoPasswordRecovery();
        passwordRecovery.setEmail(null);

        errors = validator.validate(passwordRecovery);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("email");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A valid DTO is valid")
    public void validate_valid() {
        final DtoPasswordRecovery                           passwordRecovery;
        final Set<ConstraintViolation<DtoPasswordRecovery>> errors;

        passwordRecovery = new DtoPasswordRecovery();
        passwordRecovery.setEmail("email@somewhere.com");

        errors = validator.validate(passwordRecovery);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
