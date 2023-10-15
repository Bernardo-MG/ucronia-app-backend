
package com.bernardomg.security.token.exception;

public final class OutOfScopeTokenException extends InvalidTokenException {

    private static final long serialVersionUID = -3466160863479056525L;

    public OutOfScopeTokenException(final String token, final String expectedScope, final String receivedScope) {
        super(
            String.format("Wrong scope for token %s. Received %s but expected %s", token, receivedScope, expectedScope),
            token);
    }

}
