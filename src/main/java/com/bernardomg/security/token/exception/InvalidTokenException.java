
package com.bernardomg.security.token.exception;

public abstract class InvalidTokenException extends RuntimeException {

    private static final long serialVersionUID = -3466160863479056525L;

    private final String      token;

    public InvalidTokenException(final String message, final String tkn) {
        super(message);

        token = tkn;
    }

    public final String getToken() {
        return token;
    }

}
