
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.token.config.property.TokenProperties;
import com.bernardomg.security.token.persistence.model.PersistentUserToken;
import com.bernardomg.security.token.persistence.repository.UserTokenRepository;
import com.bernardomg.security.token.store.PersistentUserTokenStore;
import com.bernardomg.security.token.test.config.ValidToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentUserTokenStore - revoke existing tokens")
class ITPersistentUserTokenStoreRevokeTokens {

    private PersistentUserTokenStore store;

    @Autowired
    private TokenProperties          tokenProperties;

    @Autowired
    private UserTokenRepository      userTokenRepository;

    @BeforeEach
    public void initialize() {
        store = new PersistentUserTokenStore(userTokenRepository, TokenConstants.SCOPE, tokenProperties.getValidity());
    }

    @Test
    @DisplayName("Revokes an already revoked token")
    @OnlyUser
    @ValidToken
    void testRevokeExistingTokens_AlreadyRevoked_Revoked() {
        final PersistentUserToken token;

        store.revokeExistingTokens(1l);

        token = userTokenRepository.findAll()
            .iterator()
            .next();
        Assertions.assertThat(token.isRevoked())
            .isTrue();
    }

    @Test
    @DisplayName("Does not revoke a token for a not existing user")
    @OnlyUser
    @ValidToken
    void testRevokeExistingTokens_NotExistingUser_NotRevoked() {
        final PersistentUserToken token;

        store.revokeExistingTokens(2l);

        token = userTokenRepository.findAll()
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
        final PersistentUserToken token;

        store.revokeExistingTokens(1l);

        token = userTokenRepository.findAll()
            .iterator()
            .next();
        Assertions.assertThat(token.isRevoked())
            .isTrue();
    }

}
