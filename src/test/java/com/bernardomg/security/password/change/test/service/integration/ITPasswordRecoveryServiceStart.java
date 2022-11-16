
package com.bernardomg.security.password.change.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.model.PasswordRecoveryStatus;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;

@IntegrationTest
@DisplayName("PasswordRecoveryService - recovery start")
public class ITPasswordRecoveryServiceStart {

    @Autowired
    private PasswordRecoveryService service;

    public ITPasswordRecoveryServiceStart() {
        super();
    }

    @Test
    @DisplayName("Starting password recovery for a user with expired credentials gives an OK")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/credentials_expired.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testStartPasswordRecovery_CredentialsExpired() {
        final PasswordRecoveryStatus status;

        status = service.startPasswordRecovery("email@somewhere.com");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @DisplayName("Starting password recovery for a disabled user gives an OK")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/disabled.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testStartPasswordRecovery_Disabled() {
        final PasswordRecoveryStatus status;

        status = service.startPasswordRecovery("email@somewhere.com");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @DisplayName("Starting password recovery with an existing user gives an OK")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testStartPasswordRecovery_Enabled() {
        final PasswordRecoveryStatus status;

        status = service.startPasswordRecovery("email@somewhere.com");

        Assertions.assertTrue(status.getSuccessful());
    }

    @Test
    @DisplayName("Starting password recovery for an expired user gives an OK")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/expired.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testStartPasswordRecovery_Expired() {
        final PasswordRecoveryStatus status;

        status = service.startPasswordRecovery("email@somewhere.com");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @DisplayName("Starting password recovery for a locked user gives an OK")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/locked.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testStartPasswordRecovery_Locked() {
        final PasswordRecoveryStatus status;

        status = service.startPasswordRecovery("email@somewhere.com");

        Assertions.assertFalse(status.getSuccessful());
    }

}
