
package com.bernardomg.security.user.token.test.store.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.security.user.token.config.property.TokenProperties;
import com.bernardomg.security.user.token.exception.MissingTokenException;
import com.bernardomg.security.user.token.persistence.repository.UserTokenRepository;
import com.bernardomg.security.user.token.store.PersistentUserTokenStore;
import com.bernardomg.security.user.token.test.config.UserRegisteredToken;
import com.bernardomg.security.user.token.test.config.ValidToken;
import com.bernardomg.security.user.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PersistentUserTokenStore - get username")
class ITPersistentUserTokenStoreGetUsername {

    private PersistentUserTokenStore store;

    @Autowired
    private TokenProperties          tokenProperties;

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
    @DisplayName("Extracts the username from a token")
    @OnlyUser
    @ValidToken
    void testGetUsername() {
        final String subject;

        subject = store.getUsername(TokenConstants.TOKEN);

        Assertions.assertThat(subject)
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Extracts no username from a not existing token")
    @OnlyUser
    void testGetUsername_notExisting() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> store.getUsername(TokenConstants.TOKEN);

        exception = Assertions.catchThrowableOfType(executable, MissingTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Missing token " + TokenConstants.TOKEN);
    }

    @Test
    @DisplayName("Extracts no username from an out of scope token")
    @OnlyUser
    @UserRegisteredToken
    void testGetUsername_outOfScope() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> store.getUsername(TokenConstants.TOKEN);

        exception = Assertions.catchThrowableOfType(executable, MissingTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Missing token " + TokenConstants.TOKEN);
    }

}