
package com.bernardomg.validation;

public interface ValidationError {

    public static ValidationError of(final String code) {
        final DefaultValidationError error;

        error = new DefaultValidationError();
        error.setError("error.memberPeriod.startMonthAfterEndMonth");

        return error;
    }

    public String getError();

}
