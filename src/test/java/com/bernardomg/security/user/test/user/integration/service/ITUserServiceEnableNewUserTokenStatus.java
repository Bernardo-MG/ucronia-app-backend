
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.token.exception.ConsumedTokenException;
import com.bernardomg.security.token.exception.ExpiredTokenException;
import com.bernardomg.security.token.exception.MissingTokenException;
import com.bernardomg.security.token.test.config.UserRegisteredConsumedToken;
import com.bernardomg.security.token.test.config.UserRegisteredExpiredToken;
import com.bernardomg.security.token.test.config.UserRegisteredToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.exception.UserEnabledException;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.security.user.test.config.OnlyUser;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("User service - enable new user - token status")
class ITUserServiceEnableNewUserTokenStatus {

    @Autowired
    private UserService service;

    public ITUserServiceEnableNewUserTokenStatus() {
        super();
    }

    @Test
    @DisplayName("Enabling a new user with a user already enabled throws an exception")
    @OnlyUser
    @UserRegisteredToken
    void testEnableNewUser_AlreadyEnabled() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.activateNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, UserEnabledException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User admin is enabled");
    }

    @Test
    @DisplayName("Enabling a new user with a consumed token throws an exception")
    @OnlyUser
    @UserRegisteredConsumedToken
    void testEnableNewUser_Consumed() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.activateNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, ConsumedTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Consumed token " + TokenConstants.TOKEN);
    }

    @Test
    @DisplayName("Enabling a new user with an expired token throws an exception")
    @OnlyUser
    @UserRegisteredExpiredToken
    void testEnableNewUser_Expired() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.activateNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, ExpiredTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Expired token " + TokenConstants.TOKEN);
    }

    @Test
    @DisplayName("Enabling a new user with no token throws an exception")
    @OnlyUser
    void testEnableNewUser_Missing() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.activateNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, MissingTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Missing token " + TokenConstants.TOKEN);
    }

}
