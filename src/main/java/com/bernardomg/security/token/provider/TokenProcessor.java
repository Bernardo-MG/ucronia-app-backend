
package com.bernardomg.security.token.provider;

import java.util.Optional;

import org.springframework.security.core.token.Token;

import com.bernardomg.security.token.TokenDecoder;
import com.bernardomg.security.token.TokenValidator;

public interface TokenProcessor extends TokenProvider, TokenValidator, TokenDecoder<Optional<Token>> {

}
