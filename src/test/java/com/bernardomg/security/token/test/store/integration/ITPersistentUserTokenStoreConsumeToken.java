
package com.bernardomg.security.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.token.config.property.TokenProperties;
import com.bernardomg.security.token.exception.ConsumedTokenException;
import com.bernardomg.security.token.exception.MissingTokenException;
import com.bernardomg.security.token.persistence.model.PersistentUserToken;
import com.bernardomg.security.token.persistence.repository.UserTokenRepository;
import com.bernardomg.security.token.store.PersistentUserTokenStore;
import com.bernardomg.security.token.test.config.ConsumedToken;
import com.bernardomg.security.token.test.config.UserRegisteredToken;
import com.bernardomg.security.token.test.config.ValidToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentUserTokenStore - consume")
class ITPersistentUserTokenStoreConsumeToken {

    private PersistentUserTokenStore store;

    @Autowired
    private TokenProperties          tokenProperties;

    @Autowired
    private UserTokenRepository      userTokenRepository;

    @BeforeEach
    public void initialize() {
        store = new PersistentUserTokenStore(userTokenRepository, TokenConstants.SCOPE, tokenProperties.getValidity());
    }

    @Test
    @DisplayName("Consuming a token which is already consumed throws an exception")
    @OnlyUser
    @ConsumedToken
    void testConsume_AlreadyConsumed_Exception() {
        final ThrowingCallable executable;

        executable = () -> store.consumeToken(TokenConstants.TOKEN);

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(ConsumedTokenException.class);
    }

    @Test
    @DisplayName("Consuming a token changes the status to consumed")
    @OnlyUser
    @ValidToken
    void testConsume_Consumes() {
        final PersistentUserToken persistedToken;

        store.consumeToken(TokenConstants.TOKEN);

        persistedToken = userTokenRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(persistedToken.isConsumed())
            .isTrue();
    }

    @Test
    @DisplayName("Consuming a token doesn't create any new token")
    @OnlyUser
    @ValidToken
    void testConsume_NotCreate() {
        final long count;

        store.consumeToken(TokenConstants.TOKEN);

        count = userTokenRepository.count();
        Assertions.assertThat(count)
            .isOne();
    }

    @Test
    @DisplayName("Consuming a token that doesn't exist throws an exception")
    @OnlyUser
    void testConsume_NotExisting_Exception() {
        final ThrowingCallable executable;

        executable = () -> store.consumeToken(TokenConstants.TOKEN);

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(MissingTokenException.class);
    }

    @Test
    @DisplayName("Consuming an out of scope token doesn't change the status to consumed")
    @OnlyUser
    @UserRegisteredToken
    void testConsume_OutOfScope() {
        final PersistentUserToken persistedToken;

        store.consumeToken(TokenConstants.TOKEN);

        persistedToken = userTokenRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(persistedToken.isConsumed())
            .isTrue();
    }

}
