
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.exception.ConsumedTokenException;
import com.bernardomg.security.token.exception.MissingTokenException;
import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.store.PersistentTokenStore;
import com.bernardomg.security.token.test.config.ConsumedToken;
import com.bernardomg.security.token.test.config.ValidToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentTokenProcessor - decode")
class ITPersistentTokenStoreConsumeToken {

    @Autowired
    private PersistentTokenStore store;

    @Autowired
    private TokenRepository      tokenRepository;

    @Test
    @DisplayName("Consuming a token which is already consumed throws an exception")
    @Sql({ "/db/queries/security/user/single.sql" })
    @ConsumedToken
    void testConsume_AlreadyConsumed_Exception() {
        final ThrowingCallable executable;

        executable = () -> store.consumeToken(TokenConstants.TOKEN);

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(ConsumedTokenException.class);
    }

    @Test
    @DisplayName("Consuming a token changes the status to consumed")
    @Sql({ "/db/queries/security/user/single.sql" })
    @ValidToken
    void testConsume_Consumes() {
        final PersistentToken persistedToken;

        store.consumeToken(TokenConstants.TOKEN);

        persistedToken = tokenRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(persistedToken.isConsumed())
            .isTrue();
    }

    @Test
    @DisplayName("Consuming a token doesn't create any new token")
    @Sql({ "/db/queries/security/user/single.sql" })
    @ValidToken
    void testConsume_NotCreate() {
        final long count;

        store.consumeToken(TokenConstants.TOKEN);

        count = tokenRepository.count();
        Assertions.assertThat(count)
            .isOne();
    }

    @Test
    @DisplayName("Consuming a token that doesn't exist throws an exception")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testConsume_NotExisting_Exception() {
        final ThrowingCallable executable;

        executable = () -> store.consumeToken(TokenConstants.TOKEN);

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(MissingTokenException.class);
    }

}
