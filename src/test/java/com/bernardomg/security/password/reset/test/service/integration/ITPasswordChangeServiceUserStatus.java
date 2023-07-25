
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.change.service.PasswordChangeService;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password - user status")
class ITPasswordChangeServiceUserStatus {

    @Autowired
    private PasswordChangeService service;

    public ITPasswordChangeServiceUserStatus() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a user with expired credentials gives a failure")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/credentials_expired.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testChangePassword_CredentialsExpired_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.changePasswordForUserInSession("1234", "abc");

        exception = Assertions.catchThrowableOfType(executable, UsernameNotFoundException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User admin is not enabled");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a disabled user gives a failure")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/disabled.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testChangePassword_Disabled_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.changePasswordForUserInSession("1234", "abc");

        exception = Assertions.catchThrowableOfType(executable, UsernameNotFoundException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User admin is not enabled");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a expired user gives a failure")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/expired.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testChangePassword_Expired_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.changePasswordForUserInSession("1234", "abc");

        exception = Assertions.catchThrowableOfType(executable, UsernameNotFoundException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User admin is not enabled");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a locked user gives a failure")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/locked.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testChangePassword_Locked_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.changePasswordForUserInSession("1234", "abc");

        exception = Assertions.catchThrowableOfType(executable, UsernameNotFoundException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User admin is not enabled");
    }

}
