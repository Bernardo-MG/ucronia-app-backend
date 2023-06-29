
package com.bernardomg.security.signup.test.validation.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.signup.model.DtoSignUp;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@IntegrationTest
@DisplayName("DtoSignUp validation")
public class TestDtoSignUpValid {

    @Autowired
    private Validator validator;

    @Test
    @DisplayName("A DTO with an invalid email is invalid")
    public void validate_invalid_email() {
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
