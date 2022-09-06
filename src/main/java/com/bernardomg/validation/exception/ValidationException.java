
package com.bernardomg.validation.exception;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.bernardomg.validation.error.ValidationFailure;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 5252694690217611607L;

    private static final String getMessage(final Collection<ValidationFailure> fails) {
        return fails.stream()
            .map(ValidationFailure::getError)
            .collect(Collectors.joining(","));
    }

    private final Collection<ValidationFailure> failures;

    public ValidationException(final Collection<ValidationFailure> fails) {
        super(getMessage(fails));

        failures = fails;
    }

    public ValidationException(final ValidationFailure err) {
        super(err.getError());

        failures = Arrays.asList(err);
    }

}
