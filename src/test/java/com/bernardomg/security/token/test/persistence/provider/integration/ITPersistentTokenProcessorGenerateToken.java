
package com.bernardomg.security.token.test.persistence.provider.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.token.persistence.provider.PersistentTokenStore;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - generate token")
class ITPersistentTokenProcessorGenerateToken {

    private final PersistentTokenStore provider;

    @Autowired
    public ITPersistentTokenProcessorGenerateToken(final TokenRepository tokenRepository,
            final TokenService tokenService) {
        super();

        provider = new PersistentTokenStore(tokenRepository, tokenService);
    }

    @Test
    @DisplayName("Generates a token")
    void testGenerateToken() {
        final String token;

        token = provider.generateToken("admin");

        Assertions.assertThat(token)
            .isNotNull();
    }

}
