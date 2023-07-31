
package com.bernardomg.security.token.test.persistence.provider.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.store.PersistentTokenStore;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - has expired")
class ITPersistentTokenProcessorHasExpired {

    private final PersistentTokenStore validator;

    @Autowired
    public ITPersistentTokenProcessorHasExpired(final TokenRepository tokenRepository,
            final TokenService tokenService) {
        super();

        validator = new PersistentTokenStore(tokenRepository, tokenService);
    }

    @Test
    @DisplayName("A valid token exists")
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testExists_existing() {
        final String  token;
        final Boolean exists;

        token = TokenConstants.TOKEN;

        exists = validator.exists(token);

        Assertions.assertThat(exists)
            .isTrue();
    }

    @Test
    @DisplayName("A not existing token doesn't exist")
    void testExists_notExisting() {
        final String  token;
        final Boolean exists;

        token = TokenConstants.TOKEN;

        exists = validator.exists(token);

        Assertions.assertThat(exists)
            .isFalse();
    }

}
