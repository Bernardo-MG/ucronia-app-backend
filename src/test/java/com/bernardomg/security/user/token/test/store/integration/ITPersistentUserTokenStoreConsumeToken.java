
package com.bernardomg.security.user.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.security.user.token.config.property.UserTokenProperties;
import com.bernardomg.security.user.token.exception.ConsumedTokenException;
import com.bernardomg.security.user.token.exception.MissingTokenException;
import com.bernardomg.security.user.token.persistence.model.PersistentUserToken;
import com.bernardomg.security.user.token.persistence.repository.UserTokenRepository;
import com.bernardomg.security.user.token.store.PersistentUserTokenStore;
import com.bernardomg.security.user.token.test.config.annotation.ConsumedToken;
import com.bernardomg.security.user.token.test.config.annotation.UserRegisteredToken;
import com.bernardomg.security.user.token.test.config.annotation.ValidToken;
import com.bernardomg.security.user.token.test.config.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentUserTokenStore - consume")
class ITPersistentUserTokenStoreConsumeToken {

    private PersistentUserTokenStore store;

    @Autowired
    private UserTokenProperties      tokenProperties;

    @Autowired
    private UserRepository           userRepository;

    @Autowired
    private UserTokenRepository      userTokenRepository;

    @BeforeEach
    public void initialize() {
        store = new PersistentUserTokenStore(userTokenRepository, userRepository, TokenConstants.SCOPE,
            tokenProperties.getValidity());
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
    @DisplayName("Consuming an out of scope token throws an exception")
    @OnlyUser
    @UserRegisteredToken
    void testConsume_OutOfScope() {
        final ThrowingCallable executable;

        executable = () -> store.consumeToken(TokenConstants.TOKEN);

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(MissingTokenException.class);
    }

}
