
package com.bernardomg.security.user.test.validation.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.test.util.model.UsersCreate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@IntegrationTest
@DisplayName("ValidatedUserCreate validation")
public class TestValidatedUserCreateValid {

    @Autowired
    private Validator validator;

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
    @DisplayName("A DTO with no email is invalid")
    public void validate_noEmail() {
        final UserCreate                           userCreate;
        final Set<ConstraintViolation<UserCreate>> errors;
        final ConstraintViolation<UserCreate>      error;

        userCreate = UsersCreate.noEmail();

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
    @DisplayName("A DTO with no enabled flag is invalid")
    public void validate_noEnabled() {
        final UserCreate                           userCreate;
        final Set<ConstraintViolation<UserCreate>> errors;
        final ConstraintViolation<UserCreate>      error;

        userCreate = UsersCreate.noEnabled();

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
