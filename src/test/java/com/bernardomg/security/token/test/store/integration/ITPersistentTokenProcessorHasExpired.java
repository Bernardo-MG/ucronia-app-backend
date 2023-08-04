
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

        store = new PersistentTokenStore(tokenRepository, tokenService);
    }

    @Test
    @DisplayName("A valid token exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testExists_existing() {
        final String  token;
        final Boolean exists;

        token = TokenConstants.TOKEN;

        exists = store.exists(token);

        Assertions.assertThat(exists)
            .isTrue();
    }

    @Test
    @DisplayName("A not existing token doesn't exist")
    void testExists_notExisting() {
        final String  token;
        final Boolean exists;

        token = TokenConstants.TOKEN;

        exists = store.exists(token);

        Assertions.assertThat(exists)
            .isFalse();
    }

}
