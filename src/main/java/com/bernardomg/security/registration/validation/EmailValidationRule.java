
package com.bernardomg.security.registration.validation;

import java.util.Optional;
import java.util.regex.Pattern;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.validation.ValidationRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class EmailValidationRule implements ValidationRule<String> {

    private final Pattern emailPattern;

    private final String  emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public EmailValidationRule() {
        super();

        emailPattern = Pattern.compile(emailRegex);
    }

    @Override
    public final Optional<Failure> test(final String email) {
        final Failure           failure;
        final Optional<Failure> result;

        // Verify the email matches the valid pattern
        if (!emailPattern.matcher(email)
            .matches()) {
            log.error("Email {} doesn't follow a valid pattern", email);
            failure = FieldFailure.of("error.email.invalid", "roleForm", "memberId", email);
            result = Optional.of(failure);
        } else {
            result = Optional.empty();
        }

        return result;
    }

}
