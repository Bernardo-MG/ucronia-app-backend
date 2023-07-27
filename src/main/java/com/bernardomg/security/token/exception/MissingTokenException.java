package com.bernardomg.security.token.exception;


public final class MissingTokenException extends RuntimeException {

    private static final long serialVersionUID = -3466160863479056525L;
    
    private final String token;
    
    public MissingTokenException(final String tkn){
        super(String.format("Missing token %s", tkn));
        
        this.token = tkn;
    }
    
    public final String getToken() {
        return token;
    }

}
