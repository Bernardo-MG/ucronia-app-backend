
package com.bernardomg.security.user.test.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.test.util.model.UsersCreate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("ValidatedUserCreate validation")
public class TestValidatedUserCreateValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO with an invalid email is invalid")
    public void validate_invalidEmail() {
        final UserCreate                           userCreate;
        final Set<ConstraintViolation<UserCreate>> errors;
        final ConstraintViolation<UserCreate>      error;

        userCreate = UsersCreate.invalidEmail();

        errors = validator.validate(userCreate);

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
        final UserCreate                           userCreate;
        final Set<ConstraintViolation<UserCreate>> errors;
        final ConstraintViolation<UserCreate>      error;

        userCreate = UsersCreate.missingEmail();

        errors = validator.validate(userCreate);

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
    @DisplayName("A DTO missing the enabled flag is invalid")
    public void validate_noEnabled() {
        final UserCreate                           userCreate;
        final Set<ConstraintViolation<UserCreate>> errors;
        final ConstraintViolation<UserCreate>      error;

        userCreate = UsersCreate.missingEnabled();

        errors = validator.validate(userCreate);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("enabled");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A valid DTO is valid")
    public void validate_valid() {
        final UserCreate                           userCreate;
        final Set<ConstraintViolation<UserCreate>> errors;

        userCreate = UsersCreate.enabled();

        errors = validator.validate(userCreate);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
