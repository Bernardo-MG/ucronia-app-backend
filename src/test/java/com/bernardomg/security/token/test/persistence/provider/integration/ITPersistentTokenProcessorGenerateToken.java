
package com.bernardomg.security.token.test.persistence.provider.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.store.PersistentTokenStore;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - generate token")
class ITPersistentTokenProcessorGenerateToken {

    private final PersistentTokenStore provider;

    private final TokenRepository      tokenRepository;

    @Autowired
    public ITPersistentTokenProcessorGenerateToken(final TokenRepository tokenRepo, final TokenService tokenService) {
        super();

        provider = new PersistentTokenStore(tokenRepo, tokenService);
        tokenRepository = tokenRepo;
    }

    @Test
    @DisplayName("After generating a token a new token is persisted")
    void testGenerateToken_Persisted() {
        final long count;

        provider.generateToken("admin");

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isOne();
    }

    @Test
    @DisplayName("After generating a token said token data is persisted")
    void testGenerateToken_PersistedData() {
        final PersistentToken token;

        provider.generateToken("admin");

        token = tokenRepository.findAll()
            .iterator()
            .next();
        Assertions.assertThat(token.getToken())
            .isNotNull();
        Assertions.assertThat(token.getExpirationDate())
            .isNotNull();
        Assertions.assertThat(token.getConsumed())
            .isFalse();
        Assertions.assertThat(token.getExpired())
            .isFalse();
    }

    @Test
    @DisplayName("After generating a token it returns said token")
    void testGenerateToken_Return() {
        final String token;

        token = provider.generateToken("admin");

        Assertions.assertThat(token)
            .isNotNull();
    }

}
