
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.user.exception.UserDisabledException;
import com.bernardomg.security.user.exception.UserExpiredException;
import com.bernardomg.security.user.exception.UserLockedException;
import com.bernardomg.security.user.exception.UserNotFoundException;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PasswordRecoveryService - recovery start - authentication")
class ITPasswordResetServiceStartAuth {

    @Autowired
    private PasswordResetService service;

    public ITPasswordResetServiceStartAuth() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Starting password recovery for a user with expired credentials throws an exception")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/credentials_expired.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testStartPasswordRecovery_CredentialsExpired_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.startPasswordRecovery("email@somewhere.com");

        exception = Assertions.catchThrowableOfType(executable, UserExpiredException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User admin is expired");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Starting password recovery for a disabled user throws an exception")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/disabled.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testStartPasswordRecovery_Disabled_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.startPasswordRecovery("email@somewhere.com");

        exception = Assertions.catchThrowableOfType(executable, UserDisabledException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User admin is disabled");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Starting password recovery for an expired user throws an exception")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/expired.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testStartPasswordRecovery_Expired_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.startPasswordRecovery("email@somewhere.com");

        exception = Assertions.catchThrowableOfType(executable, UserExpiredException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User admin is expired");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Starting password recovery for a locked user throws an exception")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/locked.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testStartPasswordRecovery_Locked_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.startPasswordRecovery("email@somewhere.com");

        exception = Assertions.catchThrowableOfType(executable, UserLockedException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User admin is locked");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Starting password recovery for a locked user throws an exception")
    void testStartPasswordRecovery_NotExisting_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.startPasswordRecovery("email@somewhere.com");

        exception = Assertions.catchThrowableOfType(executable, UserNotFoundException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Couldn't change password for email email@somewhere.com, as no user exists for it");
    }

}