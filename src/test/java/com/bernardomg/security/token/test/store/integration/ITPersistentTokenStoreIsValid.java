
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.token.config.property.TokenProperties;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.security.token.test.config.ExpiredToken;
import com.bernardomg.security.token.test.config.RevokedToken;
import com.bernardomg.security.token.test.config.ValidToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenStore - is valid")
class ITPersistentTokenStoreIsValid {

    private PersistentTokenStore store;

    @Autowired
    private TokenProperties      tokenProperties;

    @Autowired
    private TokenRepository      tokenRepository;

    @BeforeEach
    public void initialize() {
        store = new PersistentTokenStore(tokenRepository, TokenConstants.SCOPE, tokenProperties.getValidity());
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
