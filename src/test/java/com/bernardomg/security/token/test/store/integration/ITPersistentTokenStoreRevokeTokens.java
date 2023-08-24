
package com.bernardomg.security.token.test.store.integration;

import java.time.Duration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - revoke existing tokens")
class ITPersistentTokenStoreRevokeTokens {

    private final PersistentTokenStore store;

    private final TokenRepository      tokenRepository;

    @Autowired
    public ITPersistentTokenStoreRevokeTokens(final TokenRepository tokenRepo) {
        super();

        store = new PersistentTokenStore(tokenRepo, Duration.ofHours(1));
        tokenRepository = tokenRepo;
    }

    @Test
    @DisplayName("Revokes an already revoked token")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testRevokeExistingTokens_AlreadyRevoked_Revoked() {
        final PersistentToken token;

        store.revokeExistingTokens(1l, "scope");

        token = tokenRepository.findAll()
            .iterator()
            .next();
        Assertions.assertThat(token.isRevoked())
            .isTrue();
    }

    @Test
    @DisplayName("Does not revoke a token for the wrong scope")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testRevokeExistingTokens_InvalidScope_NotRevoked() {
        final PersistentToken token;

        store.revokeExistingTokens(1l, "abc");

        token = tokenRepository.findAll()
            .iterator()
            .next();
        Assertions.assertThat(token.isRevoked())
            .isFalse();
    }

    @Test
    @DisplayName("Does not revoke a token for a not existing user")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testRevokeExistingTokens_NotExistingUser_NotRevoked() {
        final PersistentToken token;

        store.revokeExistingTokens(2l, "scope");

        token = tokenRepository.findAll()
            .iterator()
            .next();
        Assertions.assertThat(token.isRevoked())
            .isFalse();
    }

    @Test
    @DisplayName("Revokes an existing token")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testRevokeExistingTokens_Revoked() {
        final PersistentToken token;

        store.revokeExistingTokens(1l, "scope");

        token = tokenRepository.findAll()
            .iterator()
            .next();
        Assertions.assertThat(token.isRevoked())
            .isTrue();
    }

}
