
package com.bernardomg.security.user.test.validation.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.request.UserUpdate;
import com.bernardomg.security.user.test.util.model.UsersUpdate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@IntegrationTest
@DisplayName("ValidatedUserUpdate validation")
public class TestValidatedUserUpdateValid {

    @Autowired
    private Validator validator;

    @Test
    @DisplayName("A DTO with an invalid email is invalid")
    public void validate_invalidEmail() {
        final UserUpdate                           userUpdate;
        final Set<ConstraintViolation<UserUpdate>> errors;
        final ConstraintViolation<UserUpdate>      error;

        userUpdate = UsersUpdate.invalidEmail();

        errors = validator.validate(userUpdate);

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
        final UserUpdate                           userUpdate;
        final Set<ConstraintViolation<UserUpdate>> errors;
        final ConstraintViolation<UserUpdate>      error;

        userUpdate = UsersUpdate.noEmail();

        errors = validator.validate(userUpdate);

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
        final UserUpdate                           userUpdate;
        final Set<ConstraintViolation<UserUpdate>> errors;
        final ConstraintViolation<UserUpdate>      error;

        userUpdate = UsersUpdate.noEnabled();

        errors = validator.validate(userUpdate);

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
    @DisplayName("A DTO with no id is invalid")
    public void validate_noId() {
        final UserUpdate                           userUpdate;
        final Set<ConstraintViolation<UserUpdate>> errors;
        final ConstraintViolation<UserUpdate>      error;

        userUpdate = UsersUpdate.noId();

        errors = validator.validate(userUpdate);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("id");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A valid DTO is valid")
    public void validate_valid() {
        final UserUpdate                           userUpdate;
        final Set<ConstraintViolation<UserUpdate>> errors;

        userUpdate = UsersUpdate.enabled();

        errors = validator.validate(userUpdate);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
