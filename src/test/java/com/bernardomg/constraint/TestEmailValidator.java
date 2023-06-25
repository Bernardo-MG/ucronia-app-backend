
package com.bernardomg.constraint;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintValidator;

@DisplayName("EmailValidator")
public class TestEmailValidator {

    private final ConstraintValidator<Email, String> validator = new EmailValidator();

    @Test
    @DisplayName("With an empty email the validator returns false")
    public void testValid_Empty() {
        final Boolean valid;
        final String  email;

        email = "";
        valid = validator.isValid(email, null);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("With an email missing the at char the validator returns false")
    public void testValid_MissingAt() {
        final Boolean valid;
        final String  email;

        email = "someonesomewhere.com";
        valid = validator.isValid(email, null);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("With an email missing the point the validator returns false")
    public void testValid_MissingPoint() {
        final Boolean valid;
        final String  email;

        email = "someone@somewherecom";
        valid = validator.isValid(email, null);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("With a valid email the validator returns true")
    public void testValid_Valid() {
        final Boolean valid;
        final String  email;

        email = "someone@somewhere.com";
        valid = validator.isValid(email, null);

        Assertions.assertThat(valid)
            .isTrue();
    }

}
