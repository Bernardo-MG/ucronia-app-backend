
package com.bernardomg.validation.exception;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.bernardomg.validation.error.ValidationFailure;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 5252694690217611607L;

    private static final String getMessage(final Collection<ValidationFailure> errs) {
        return errs.stream()
            .map(ValidationFailure::getError)
            .collect(Collectors.joining(","));
    }

    private final Collection<ValidationFailure> errors;

    public ValidationException(final Collection<ValidationFailure> errs) {
        super(getMessage(errs));

        errors = errs;
    }

    public ValidationException(final ValidationFailure err) {
        super(err.getError());

        errors = Arrays.asList(err);
    }

    public final Collection<ValidationFailure> getErrors() {
        return errors;
    }

}
