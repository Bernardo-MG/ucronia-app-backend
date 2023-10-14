
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.security.token.test.config.ValidToken;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenStore - revoke existing tokens")
class ITPersistentTokenStoreRevokeTokens {

    @Autowired
    private PersistentTokenStore store;

    @Autowired
    private TokenRepository      tokenRepository;

    @Test
    @DisplayName("Revokes an already revoked token")
    @OnlyUser
    @ValidToken
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
    @OnlyUser
    @ValidToken
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
    @OnlyUser
    @ValidToken
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
    @OnlyUser
    @ValidToken
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
