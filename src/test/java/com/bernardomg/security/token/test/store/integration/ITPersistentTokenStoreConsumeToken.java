
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - decode")
class ITPersistentTokenStoreConsumeToken {

    private final PersistentTokenStore store;

    private final TokenRepository      tokenRepository;

    @Autowired
    public ITPersistentTokenStoreConsumeToken(final TokenRepository tokenRepo, final TokenService tokenService) {
        super();

        store = new PersistentTokenStore(tokenRepo, tokenService, 1000);

        tokenRepository = tokenRepo;
    }

    @Test
    @DisplayName("Consuming a token which is already consumed keeps the status as consumed")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/consumed.sql" })
    void testConsume_AlreadyConsumed_Consumed() {
        final PersistentToken persistedToken;

        store.consumeToken(TokenConstants.TOKEN);

        persistedToken = tokenRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(persistedToken.getConsumed())
            .isTrue();
    }

    @Test
    @DisplayName("Consuming a token changes the status to consumed")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testConsume_Consumes() {
        final PersistentToken persistedToken;

        store.consumeToken(TokenConstants.TOKEN);

        persistedToken = tokenRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(persistedToken.getConsumed())
            .isTrue();
    }

    @Test
    @DisplayName("Consuming a token doesn't create any new token")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testConsume_NotCreate() {
        final long   count;

        store.consumeToken(TokenConstants.TOKEN);

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isOne();
    }

    @Test
    @DisplayName("Consuming a token that doesn't exist doesn't create a new token")
    void testConsume_NotExists_NotCreate() {
        final long   count;

        store.consumeToken(TokenConstants.TOKEN);

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isZero();
    }

}
