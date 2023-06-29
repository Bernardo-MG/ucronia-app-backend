
package com.bernardomg.security.signup.test.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.security.signup.model.DtoSignUp;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("DtoSignUp validation")
public class TestDtoSignUpValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO with an invalid email is invalid")
    public void validate_invalidEmail() {
        final DtoSignUp                           signUp;
        final Set<ConstraintViolation<DtoSignUp>> errors;
        final ConstraintViolation<DtoSignUp>      error;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("abc");

        errors = validator.validate(signUp);

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
    public void validate_missingEmail() {
        final DtoSignUp                           signUp;
        final Set<ConstraintViolation<DtoSignUp>> errors;
        final ConstraintViolation<DtoSignUp>      error;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail(null);

        errors = validator.validate(signUp);

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
    @DisplayName("A DTO missing the username is invalid")
    public void validate_missingUsername() {
        final DtoSignUp                           signUp;
        final Set<ConstraintViolation<DtoSignUp>> errors;
        final ConstraintViolation<DtoSignUp>      error;

        signUp = new DtoSignUp();
        signUp.setUsername(null);
        signUp.setEmail("email@somewhere.com");

        errors = validator.validate(signUp);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("username");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A valid DTO is valid")
    public void validate_valid() {
        final DtoSignUp                           signUp;
        final Set<ConstraintViolation<DtoSignUp>> errors;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("email@somewhere.com");

        errors = validator.validate(signUp);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
