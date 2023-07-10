package com.bernardomg.exception;


public final class InvalidIdException extends RuntimeException {

    private static final long serialVersionUID = -1589290420417721821L;
    
    private final Object id;

    public InvalidIdException(final Object id) {
        super();
        
        this.id = id;
    }

    public InvalidIdException(final String message, final Object identifier) {
        super(message);
        
        id = identifier;
    }

    
    public Object getId() {
        return id;
    }

}
