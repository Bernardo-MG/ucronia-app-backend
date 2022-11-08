
package com.bernardomg.security.registration.validation;

import java.util.ArrayList;
import java.util.Collection;
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
    public final Collection<Failure> test(final String email) {
        final Collection<Failure> failures;
        final Failure             error;

        failures = new ArrayList<>();

        // Verify the email matches the valid pattern
        if (!emailPattern.matcher(email)
            .matches()) {
            log.error("Email {} doesn't follow a valid pattern", email);
            error = FieldFailure.of("error.email.invalid", "roleForm", "memberId", email);
            failures.add(error);
        }

        return failures;
    }

}
