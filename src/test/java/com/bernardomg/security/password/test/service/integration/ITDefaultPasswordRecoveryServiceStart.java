
package com.bernardomg.security.password.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.service.PasswordRecoveryService;

@IntegrationTest
@DisplayName("PasswordRecoveryService - recovery start")
public class ITDefaultPasswordRecoveryServiceStart {

    @Autowired
    private PasswordRecoveryService service;

    public ITDefaultPasswordRecoveryServiceStart() {
        super();
    }

    @Test
    @DisplayName("Starting password recovery for a user with expired credentials gives an OK")
    @Sql({ "/db/queries/security/user/credentials_expired.sql" })
    public final void testStartPasswordRecovery_CredentialsExpired() {
        final Boolean status;

        status = service.startPasswordRecovery("email@somewhere.com");

        Assertions.assertTrue(status);
    }

    @Test
    @DisplayName("Starting password recovery for a disabled user gives an OK")
    @Sql({ "/db/queries/security/user/disabled.sql" })
    public final void testStartPasswordRecovery_Disabled() {
        final Boolean status;

        status = service.startPasswordRecovery("email@somewhere.com");

        Assertions.assertTrue(status);
    }

    @Test
    @DisplayName("Starting password recovery with an existing user gives an OK")
    @Sql({ "/db/queries/security/user/single.sql" })
    public final void testStartPasswordRecovery_Enabled() {
        final Boolean status;

        status = service.startPasswordRecovery("email@somewhere.com");

        Assertions.assertTrue(status);
    }

    @Test
    @DisplayName("Starting password recovery for an expired user gives an OK")
    @Sql({ "/db/queries/security/user/expired.sql" })
    public final void testStartPasswordRecovery_Expired() {
        final Boolean status;

        status = service.startPasswordRecovery("email@somewhere.com");

        Assertions.assertTrue(status);
    }

    @Test
    @DisplayName("Starting password recovery for a locked user gives an OK")
    @Sql({ "/db/queries/security/user/locked.sql" })
    public final void testStartPasswordRecovery_Locked() {
        final Boolean status;

        status = service.startPasswordRecovery("email@somewhere.com");

        Assertions.assertTrue(status);
    }

}
