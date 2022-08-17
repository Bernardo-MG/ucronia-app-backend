
package com.bernardomg.validation.exception;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.bernardomg.validation.error.ValidationError;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 5252694690217611607L;

    private static final String getMessage(final Collection<ValidationError> errs) {
        return errs.stream()
            .map(ValidationError::getError)
            .collect(Collectors.joining(","));
    }

    private final Collection<ValidationError> errors;

    public ValidationException(final Collection<ValidationError> errs) {
        super(getMessage(errs));

        errors = errs;
    }

    public ValidationException(final ValidationError err) {
        super(err.getError());

        errors = Arrays.asList(err);
    }

    public final Collection<ValidationError> getErrors() {
        return errors;
    }

}
