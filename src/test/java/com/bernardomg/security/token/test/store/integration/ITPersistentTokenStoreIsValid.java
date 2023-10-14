
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.security.token.test.config.ExpiredToken;
import com.bernardomg.security.token.test.config.RevokedToken;
import com.bernardomg.security.token.test.config.ValidToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - is valid")
class ITPersistentTokenStoreIsValid {

    @Autowired
    private PersistentTokenStore store;

    @Test
    @DisplayName("A consumed token is invalid")
    @OnlyUser
    @ExpiredToken
    void testIsValid_Consumed() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("An expired token is invalid")
    @OnlyUser
    @ExpiredToken
    void testIsValid_Expired() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("An expired token is invalid")
    @OnlyUser
    @RevokedToken
    void testIsValid_Revoked() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("A valid token is valid")
    @OnlyUser
    @ValidToken
    void testIsValid_Valid() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(valid)
            .isTrue();
    }

    @Test
    @DisplayName("A token for the wrong scope is invalid")
    @OnlyUser
    @ValidToken
    void testIsValid_WrongScope() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN, "abc");

        Assertions.assertThat(valid)
            .isFalse();
    }

}
