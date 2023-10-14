
package com.bernardomg.security.token.test.store.integration;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.token.config.property.TokenProperties;
import com.bernardomg.security.token.persistence.model.PersistentUserToken;
import com.bernardomg.security.token.persistence.repository.UserTokenRepository;
import com.bernardomg.security.token.store.PersistentUserTokenStore;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentUserTokenStore - create token")
class ITPersistentUserTokenStoreCreateToken {

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
    @DisplayName("After generating a token a new token is persisted")
    @OnlyUser
    void testCreateToken_Persisted() {
        final long count;

        store.createToken(1l, "admin");

        count = userTokenRepository.count();
        Assertions.assertThat(count)
            .isOne();
    }

    @Test
    @DisplayName("After generating a token said token data is persisted")
    @OnlyUser
    void testCreateToken_PersistedData() {
        final PersistentUserToken token;
        final LocalDateTime       lower;
        final LocalDateTime       upper;

        lower = LocalDateTime.now();

        store.createToken(1l, "admin");

        token = userTokenRepository.findAll()
            .iterator()
            .next();

        upper = LocalDateTime.now()
            .plusSeconds(1);

        Assertions.assertThat(token.getToken())
            .isNotNull();
        Assertions.assertThat(token.getScope())
            .isEqualTo("scope");
        Assertions.assertThat(token.getExpirationDate())
            .isAfter(lower)
            .isBefore(upper);
        Assertions.assertThat(token.isConsumed())
            .isFalse();
        Assertions.assertThat(token.isRevoked())
            .isFalse();
    }

    @Test
    @DisplayName("After generating a token it returns said token")
    @OnlyUser
    void testCreateToken_Return() {
        final String token;

        token = store.createToken(1l, "admin");

        Assertions.assertThat(token)
            .isNotNull();
    }

    @Test
    @DisplayName("Can generate tokens when the username doesn't match the user's")
    @OnlyUser
    void testCreateToken_UserNameNotExisting() {
        final long count;

        // TODO: then, just take the username from the user id
        store.createToken(1l, "abc");

        count = userTokenRepository.count();
        Assertions.assertThat(count)
            .isOne();
    }

    @Test
    @DisplayName("When generating a token for and invalid user id, then an exception is thrown")
    @OnlyUser
    void testCreateToken_UserNotExisting() {
        final ThrowingCallable executable;

        executable = () -> {
            store.createToken(2l, "admin");
            userTokenRepository.flush();
        };

        // TODO: Does this make sense? Throw a custom exception
        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(Exception.class);
    }

}
