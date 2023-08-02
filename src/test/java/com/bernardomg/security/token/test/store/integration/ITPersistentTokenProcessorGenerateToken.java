
package com.bernardomg.security.token.test.store.integration;

import java.util.Calendar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - generate token")
class ITPersistentTokenProcessorGenerateToken {

    private final PersistentTokenStore store;

    private final TokenRepository      tokenRepository;

    @Autowired
    public ITPersistentTokenProcessorGenerateToken(final TokenRepository tokenRepo, final TokenService tokenService) {
        super();

        store = new PersistentTokenStore(tokenRepo, tokenService);
        tokenRepository = tokenRepo;
    }

    @Test
    @DisplayName("After generating a token a new token is persisted")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testGenerateToken_Persisted() {
        final long count;

        store.generateToken(1l, "admin");

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isOne();
    }

    @Test
    @DisplayName("After generating a token said token data is persisted")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testGenerateToken_PersistedData() {
        final PersistentToken token;

        store.generateToken(1l, "admin");

        token = tokenRepository.findAll()
            .iterator()
            .next();
        Assertions.assertThat(token.getToken())
            .isNotNull();
        Assertions.assertThat(token.getExpirationDate())
            .isGreaterThan(Calendar.getInstance());
        Assertions.assertThat(token.getConsumed())
            .isFalse();
    }

    @Test
    @DisplayName("After generating a token it returns said token")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testGenerateToken_Return() {
        final String token;

        token = store.generateToken(1l, "admin");

        Assertions.assertThat(token)
            .isNotNull();
    }

}
