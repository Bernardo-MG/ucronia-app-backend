
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - has expired")
class ITPersistentTokenProcessorHasExpired {

    private final PersistentTokenStore store;

    @Autowired
    public ITPersistentTokenProcessorHasExpired(final TokenRepository tokenRepository,
            final TokenService tokenService) {
        super();

        store = new PersistentTokenStore(tokenRepository, tokenService, 1800);
    }

    @Test
    @DisplayName("A valid token exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testExists_existing() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN, TokenConstants.PURPOSE);

        Assertions.assertThat(exists)
            .isTrue();
    }

    @Test
    @DisplayName("A not existing token doesn't exist")
    void testExists_notExisting() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN, TokenConstants.PURPOSE);

        Assertions.assertThat(exists)
            .isFalse();
    }

    @Test
    @DisplayName("A token for the wrong purpose doesn't exist")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testExists_WrongPurpose() {
        final Boolean exists;

        exists = store.exists(TokenConstants.TOKEN, "abc");

        Assertions.assertThat(exists)
            .isFalse();
    }

}
