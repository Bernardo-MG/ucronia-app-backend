
package com.bernardomg.security.token.test.persistence.provider.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.persistence.provider.PersistentTokenProcessor;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - has expired")
class ITPersistentTokenProcessorHasExpired {

    private final PersistentTokenProcessor validator;

    @Autowired
    public ITPersistentTokenProcessorHasExpired(final TokenRepository tokenRepository,
            final TokenService tokenService) {
        super();

        validator = new PersistentTokenProcessor(tokenRepository, tokenService);
    }

    @Test
    @DisplayName("A token after the expiration date has expired")
    @Sql({ "/db/queries/security/token/not_expired_after_expiration.sql" })
    void testHasExpired_AfterExpirationDate() {
        final String  token;
        final Boolean expired;

        token = TokenConstants.TOKEN;

        expired = validator.hasExpired(token);

        Assertions.assertThat(expired)
            .isTrue();
    }

    @Test
    @DisplayName("A expired token has expired")
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testHasExpired_Expired() {
        final String  token;
        final Boolean expired;

        token = TokenConstants.TOKEN;

        expired = validator.hasExpired(token);

        Assertions.assertThat(expired)
            .isTrue();
    }

    @Test
    @DisplayName("A valid token has not expired")
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testHasExpired_Valid() {
        final String  token;
        final Boolean expired;

        token = TokenConstants.TOKEN;

        expired = validator.hasExpired(token);

        Assertions.assertThat(expired)
            .isFalse();
    }

}
