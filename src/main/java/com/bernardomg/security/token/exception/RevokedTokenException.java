
package com.bernardomg.security.token.exception;

public final class RevokedTokenException extends InvalidTokenException {

    private static final long serialVersionUID = -3466160863479056525L;

    public RevokedTokenException(final String token) {
        super(String.format("Revoked token %s", token), token);
    }

}
