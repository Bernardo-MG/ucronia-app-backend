
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.exception.ExpiredTokenException;
import com.bernardomg.security.token.exception.MissingTokenException;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password - token status")
class ITPasswordResetServiceChangeTokenStatus {

    @Autowired
    private PasswordResetService service;

    public ITPasswordResetServiceChangeTokenStatus() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an expired token gives a failure")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testChangePassword_ExpiredToken() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.changePassword(TokenConstants.TOKEN, "abc");

        exception = Assertions.catchThrowableOfType(executable, ExpiredTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Expired token " + TokenConstants.TOKEN);
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a not existing token gives a failure")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testChangePassword_NotExistingToken() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.changePassword(TokenConstants.TOKEN, "abc");

        exception = Assertions.catchThrowableOfType(executable, MissingTokenException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Missing token " + TokenConstants.TOKEN);
    }

}
