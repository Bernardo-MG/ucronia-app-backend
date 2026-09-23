
package com.bernardomg.content.domain.exception;

public final class ContentEmptyException extends RuntimeException {

    private static final long serialVersionUID = -730336251687748969L;

    public ContentEmptyException() {
        super("Content can't be empty");
    }

}
