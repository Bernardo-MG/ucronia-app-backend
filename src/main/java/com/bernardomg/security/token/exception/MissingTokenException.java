
package com.bernardomg.security.token.exception;

public final class MissingTokenException extends InvalidTokenException {

    private static final long serialVersionUID = -3466160863479056525L;

    public MissingTokenException(final String token) {
        super(String.format("Missing token %s", token), token);
    }

}
