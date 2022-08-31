
package com.bernardomg.validation.error;

/**
 * TODO: Should include the field related to the error.
 *
 * @author Bernardo
 *
 */
public interface ValidationFailure {

    public static ValidationFailure of(final String code) {
        final DefaultValidationFailure error;

        error = new DefaultValidationFailure();
        error.setError(code);

        return error;
    }

    public String getError();

}
