
package com.bernardomg.security.user.exception;

public final class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7226031317959127168L;

    public UserNotFoundException(final String msg) {
        super(msg);
    }

}
