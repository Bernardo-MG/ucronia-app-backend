
package com.bernardomg.constraint;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public final class EmailValidator implements ConstraintValidator<Email, String> {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private final Pattern       emailPattern;

    public EmailValidator() {
        super();

        emailPattern = Pattern.compile(EMAIL_REGEX);
    }

    @Override
    public final boolean isValid(final String value, final ConstraintValidatorContext context) {
        return emailPattern.matcher(value)
            .matches();
    }

}
