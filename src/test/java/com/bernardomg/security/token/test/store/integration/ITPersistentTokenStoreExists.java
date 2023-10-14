
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.security.token.test.config.ExpiredToken;
import com.bernardomg.security.token.test.config.RevokedToken;
import com.bernardomg.security.token.test.config.ValidToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - exists")
class ITPersistentTokenStoreExists {

    @Autowired
    private PersistentTokenStore store;

    @Test
    @DisplayName("A valid token exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    @ValidToken
    void testExists_existing() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(exists)
            .isTrue();
    }

    @Test
    @DisplayName("An expired token exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    @ExpiredToken
    void testExists_expired() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(exists)
            .isTrue();
    }

    @Test
    @DisplayName("A not existing token doesn't exist")
    void testExists_notExisting() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(exists)
            .isFalse();
    }

    @Test
    @DisplayName("A revoked token exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    @RevokedToken
    void testExists_revoked() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(exists)
            .isTrue();
    }

    @Test
    @DisplayName("A token for the wrong scope doesn't exist")
    @Sql({ "/db/queries/security/user/single.sql" })
    @ValidToken
    void testExists_WrongScope() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN, "abc");

        Assertions.assertThat(exists)
            .isFalse();
    }

}
