
package com.bernardomg.exception;

import java.io.Serializable;

public class HasRelationshipException extends RuntimeException {

    private static final long  serialVersionUID = -1175181381996027472L;

    /**
     * Id which caused the exception.
     */
    private final Serializable id;

    public HasRelationshipException(final String resource, final Serializable id) {
        super(String.format("Has relationship for id %s for %s", id, resource));

        this.id = id;
    }

    /**
     * Returns the id which caused the exception.
     *
     * @return the id which caused the exception
     */
    public final Serializable getId() {
        return id;
    }

}
