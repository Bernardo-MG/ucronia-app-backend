
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.exception.InvalidTokenException;
import com.bernardomg.security.token.exception.MissingTokenException;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.exception.UserEnabledException;
import com.bernardomg.security.user.service.UserService;
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
    @DisplayName("Enabling a new user with an expired token throws an exception")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/user_registered_consumed.sql" })
    void testEnableNewUser_Consumed() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.enableNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, InvalidTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Invalid token " + TokenConstants.TOKEN);
    }

    @Test
    @DisplayName("Enabling a new user with an expired token throws an exception")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/user_registered_expired.sql" })
    void testEnableNewUser_Expired() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.enableNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, InvalidTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Invalid token " + TokenConstants.TOKEN);
    }

    @Test
    @DisplayName("Enabling a new user with no token throws an exception")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testEnableNewUser_Missing() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.enableNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, MissingTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Missing token " + TokenConstants.TOKEN);
    }

    @Test
    @DisplayName("Enabling a new user with a user already enabled throws an exception")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/user_registered.sql" })
    void testEnableNewUser_AlreadyEnabled() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.enableNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, UserEnabledException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User admin is enabled");
    }

}
