
package com.bernardomg.security.token.test.store.integration;

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

    private final PersistentTokenStore store;

    @Autowired
    public ITPersistentTokenProcessorExists(final TokenRepository tokenRepository, final TokenService tokenService) {
        super();

        store = new PersistentTokenStore(tokenRepository, tokenService);
    }

    @Test
    @DisplayName("A consumed token is invalid")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testIsValid_Consumed() {
        final String  token;
        final Boolean valid;

        token = TokenConstants.TOKEN;

        valid = store.isValid(token);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("An expired token is invalid")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testIsValid_Expired() {
        final String  token;
        final Boolean valid;

        token = TokenConstants.TOKEN;

        valid = store.isValid(token);

        Assertions.assertThat(valid)
            .isFalse();
    }

    @Test
    @DisplayName("A valid token is valid")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testIsValid_Valid() {
        final String  token;
        final Boolean valid;

        token = TokenConstants.TOKEN;

        valid = store.isValid(token);

        Assertions.assertThat(valid)
            .isTrue();
    }

}
