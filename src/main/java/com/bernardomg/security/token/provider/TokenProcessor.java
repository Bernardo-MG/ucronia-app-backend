
package com.bernardomg.security.token.provider;

import org.springframework.security.core.token.Token;

import com.bernardomg.security.token.TokenDecoder;
import com.bernardomg.security.token.TokenValidator;

public interface TokenProcessor extends TokenProvider, TokenValidator, TokenDecoder<Token> {

    public boolean exists(final String token);

}
