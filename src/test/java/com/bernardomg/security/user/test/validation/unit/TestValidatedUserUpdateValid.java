
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
    public void validate_invalid_email() {
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
