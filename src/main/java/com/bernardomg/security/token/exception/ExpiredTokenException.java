
package com.bernardomg.security.token.exception;

public final class ExpiredTokenException extends InvalidTokenException {

    private static final long serialVersionUID = -3466160863479056525L;

    public ExpiredTokenException(final String token) {
        super(String.format("Expired token %s", token), token);
    }

}
