
package com.bernardomg.security.exception;

public final class UserDisabledException extends RuntimeException {

    private static final long serialVersionUID = -7226031317959127168L;

    public UserDisabledException(final String msg) {
        super(msg);
    }

}
