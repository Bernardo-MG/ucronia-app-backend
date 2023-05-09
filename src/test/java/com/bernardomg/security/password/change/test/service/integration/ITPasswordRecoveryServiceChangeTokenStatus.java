
package com.bernardomg.security.password.change.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.model.PasswordRecoveryStatus;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.security.test.constant.TokenConstants;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password - token status")
public class ITPasswordRecoveryServiceChangeTokenStatus {

    @Autowired
    private PasswordRecoveryService service;

    public ITPasswordRecoveryServiceChangeTokenStatus() {
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
    public final void testChangePassword_ExpiredToken_Status() {
        final PasswordRecoveryStatus status;

        status = service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a not existing token gives a failure")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_NotExistingToken_Status() {
        final PasswordRecoveryStatus status;

        status = service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

}
