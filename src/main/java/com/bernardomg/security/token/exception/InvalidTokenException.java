
package com.bernardomg.security.token.exception;

public final class InvalidTokenException extends RuntimeException {

    private static final long serialVersionUID = -3466160863479056525L;

    private final String      token;

    public InvalidTokenException(final String tkn) {
        super(String.format("Invalid token %s", tkn));

        token = tkn;
    }

    public final String getToken() {
        return token;
    }

}
