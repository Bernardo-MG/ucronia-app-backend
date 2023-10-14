
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.token.config.property.TokenProperties;
import com.bernardomg.security.token.persistence.repository.UserTokenRepository;
import com.bernardomg.security.token.store.PersistentUserTokenStore;
import com.bernardomg.security.token.test.config.ExpiredToken;
import com.bernardomg.security.token.test.config.RevokedToken;
import com.bernardomg.security.token.test.config.ValidToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentUserTokenStore - exists")
class ITPersistentUserTokenStoreExists {

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
    @DisplayName("A valid token exists")
    @OnlyUser
    @ValidToken
    void testExists_existing() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN);

        Assertions.assertThat(exists)
            .isTrue();
    }

    @Test
    @DisplayName("An expired token exists")
    @OnlyUser
    @ExpiredToken
    void testExists_expired() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN);

        Assertions.assertThat(exists)
            .isTrue();
    }

    @Test
    @DisplayName("A not existing token doesn't exist")
    void testExists_notExisting() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN);

        Assertions.assertThat(exists)
            .isFalse();
    }

    @Test
    @DisplayName("A revoked token exists")
    @OnlyUser
    @RevokedToken
    void testExists_revoked() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN);

        Assertions.assertThat(exists)
            .isTrue();
    }

}
