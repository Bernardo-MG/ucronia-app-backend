
package com.bernardomg.security.exception;

public class InvalidPasswordException extends RuntimeException {

    private static final long serialVersionUID = -6065092822899002541L;

    public InvalidPasswordException() {
        super("Incorrect password");
    }
}
