
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
import com.bernardomg.security.token.test.config.UserRegisteredToken;
import com.bernardomg.security.token.test.config.ValidToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentUserTokenStore - is valid")
class ITPersistentUserTokenStoreIsValid {

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
    @DisplayName("A consumed token is invalid")
    @OnlyUser
    @ExpiredToken
    void testIsValid_Consumed() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("An expired token is invalid")
    @OnlyUser
    @ExpiredToken
    void testIsValid_Expired() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("An out of scope token is invalid")
    @OnlyUser
    @UserRegisteredToken
    void testIsValid_outOfScope() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("An expired token is invalid")
    @OnlyUser
    @RevokedToken
    void testIsValid_Revoked() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("A valid token is valid")
    @OnlyUser
    @ValidToken
    void testIsValid_Valid() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN);

        Assertions.assertThat(valid)
            .isTrue();
    }

}
