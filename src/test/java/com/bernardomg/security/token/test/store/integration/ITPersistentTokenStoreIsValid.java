
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - is valid")
class ITPersistentTokenStoreIsValid {

    @Autowired
    private PersistentTokenStore store;

    @Test
    @DisplayName("A consumed token is invalid")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testIsValid_Consumed() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("An expired token is invalid")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testIsValid_Expired() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("An expired token is invalid")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/revoked.sql" })
    void testIsValid_Revoked() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("A valid token is valid")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testIsValid_Valid() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN, TokenConstants.SCOPE);

        Assertions.assertThat(valid)
            .isTrue();
    }

    @Test
    @DisplayName("A token for the wrong scope is invalid")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testIsValid_WrongScope() {
        final Boolean valid;

        valid = store.isValid(TokenConstants.TOKEN, "abc");

        Assertions.assertThat(valid)
            .isFalse();
    }

}
