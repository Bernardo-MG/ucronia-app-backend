
package com.bernardomg.validation.error;

/**
 * TODO: Should include the field related to the error.
 *
 * @author Bernardo
 *
 */
public interface ValidationError {

    public static ValidationError of(final String code) {
        final DefaultValidationError error;

        error = new DefaultValidationError();
        error.setError(code);

        return error;
    }

    public String getError();

}
