
package com.bernardomg.validation.exception;

import java.util.Collection;

import com.bernardomg.validation.error.ValidationError;

public class ValidationException extends RuntimeException {

    private static final long                 serialVersionUID = 5252694690217611607L;

    private final Collection<ValidationError> errors;

    public ValidationException(final Collection<ValidationError> errs) {
        super("validationError");

        errors = errs;
    }

    public final Collection<ValidationError> getErrors() {
        return errors;
    }

}
