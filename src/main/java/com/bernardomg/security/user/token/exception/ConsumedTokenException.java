
package com.bernardomg.security.user.token.exception;

public final class ConsumedTokenException extends InvalidTokenException {

    private static final long serialVersionUID = -3466160863479056525L;

    public ConsumedTokenException(final String token) {
        super(String.format("Consumed token %s", token), token);
    }

}
