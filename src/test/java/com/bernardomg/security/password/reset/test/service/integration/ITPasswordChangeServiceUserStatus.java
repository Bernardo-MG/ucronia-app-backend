
package com.bernardomg.security.password.reset.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.change.model.PasswordChangeStatus;
import com.bernardomg.security.password.change.service.PasswordChangeService;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password - user status")
public class ITPasswordChangeServiceUserStatus {

    @Autowired
    private PasswordChangeService service;

    public ITPasswordChangeServiceUserStatus() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a user with expired credentials gives a failure")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/credentials_expired.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_CredentialsExpired_Status() {
        final PasswordChangeStatus status;

        status = service.changePassword("admin", "1234", "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a disabled user gives a failure")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/disabled.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_Disabled_Status() {
        final PasswordChangeStatus status;

        status = service.changePassword("admin", "1234", "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an enabled user gives a success")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_Enabled_Status() {
        final PasswordChangeStatus status;

        status = service.changePassword("admin", "1234", "abc");

        Assertions.assertTrue(status.getSuccessful());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a expired user gives a failure")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/expired.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_Expired_Status() {
        final PasswordChangeStatus status;

        status = service.changePassword("admin", "1234", "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a locked user gives a failure")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/locked.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_Locked_Status() {
        final PasswordChangeStatus status;

        status = service.changePassword("admin", "1234", "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a not existing user gives a failure")
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_NotExistingUser_Status() {
        final PasswordChangeStatus status;

        status = service.changePassword("admin", "1234", "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

}
