
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
@DisplayName("PersistentTokenProcessor - is valid")
class ITPersistentTokenProcessorExists {

    private final PersistentTokenStore validator;

    @Autowired
    public ITPersistentTokenProcessorExists(final TokenRepository tokenRepository, final TokenService tokenService) {
        super();

        validator = new PersistentTokenStore(tokenRepository, tokenService);
    }

    @Test
    @DisplayName("A token after the expiration date is invalid")
    @Sql({ "/db/queries/security/token/not_expired_after_expiration.sql" })
    void testIsValid_AfterExpirationDate() {
        final String  token;
        final Boolean valid;

        token = TokenConstants.TOKEN;

        valid = validator.isValid(token);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("A consumed token is invalid")
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testIsValid_Consumed() {
        final String  token;
        final Boolean valid;

        token = TokenConstants.TOKEN;

        valid = validator.isValid(token);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("An expired token is invalid")
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testIsValid_Expired() {
        final String  token;
        final Boolean valid;

        token = TokenConstants.TOKEN;

        valid = validator.isValid(token);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("A valid token is valid")
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testIsValid_Valid() {
        final String  token;
        final Boolean valid;

        token = TokenConstants.TOKEN;

        valid = validator.isValid(token);

        Assertions.assertThat(valid)
            .isTrue();
    }

}
