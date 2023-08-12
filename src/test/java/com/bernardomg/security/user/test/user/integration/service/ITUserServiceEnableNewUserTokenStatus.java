
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.exception.MissingTokenException;
import com.bernardomg.security.token.test.constant.TokenConstants;
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
    @Sql({ "/db/queries/security/token/password_reset_consumed.sql" })
    void testEnableNewUser_Consumed() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.enableNewUser(TokenConstants.TOKEN, "admin");

        exception = Assertions.catchThrowableOfType(executable, MissingTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Missing token " + TokenConstants.TOKEN);
    }

    @Test
    @DisplayName("Enabling a new user with an expired token throws an exception")
    @Sql({ "/db/queries/security/user/single.sql" })
    @Sql({ "/db/queries/security/token/password_reset_expired.sql" })
    void testEnableNewUser_Expired() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.enableNewUser(TokenConstants.TOKEN, "admin");

        exception = Assertions.catchThrowableOfType(executable, MissingTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Missing token " + TokenConstants.TOKEN);
    }

    @Test
    @DisplayName("Enabling a new user with no token throws an exception")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testEnableNewUser_Missing() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.enableNewUser(TokenConstants.TOKEN, "admin");

        exception = Assertions.catchThrowableOfType(executable, MissingTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Missing token " + TokenConstants.TOKEN);
    }

}
