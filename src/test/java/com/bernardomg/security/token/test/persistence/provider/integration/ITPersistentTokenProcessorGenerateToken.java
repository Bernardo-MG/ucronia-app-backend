
package com.bernardomg.security.token.test.persistence.provider.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.token.persistence.provider.PersistentTokenProcessor;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.provider.TokenProvider;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - generate token")
public class ITPersistentTokenProcessorGenerateToken {

    private final TokenProvider provider;

    @Autowired
    public ITPersistentTokenProcessorGenerateToken(final TokenRepository tokenRepository,
            final TokenService tokenService) {
        super();

        provider = new PersistentTokenProcessor(tokenRepository, tokenService);
    }

    @Test
    @DisplayName("Generates a token")
    public final void testGenerateToken() {
        final String token;

        token = provider.generateToken("admin");

        Assertions.assertNotNull(token);
    }

}
