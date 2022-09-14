
package com.bernardomg.validation.exception;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.bernardomg.mvc.error.model.Failure;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 5252694690217611607L;

    private static final String getMessage(final Collection<Failure> fails) {
        return fails.stream()
            .map(Failure::getMessage)
            .collect(Collectors.joining(","));
    }

    private final Collection<Failure> failures;

    public ValidationException(final Collection<Failure> fails) {
        super(getMessage(fails));

        failures = fails;
    }

    public ValidationException(final Failure err) {
        super(err.getMessage());

        failures = Arrays.asList(err);
    }

}
