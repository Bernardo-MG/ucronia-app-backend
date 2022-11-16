
package com.bernardomg.security.password.change.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.password.recovery.model.PasswordRecoveryStatus;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.security.test.constant.TokenConstants;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password")
public class ITPasswordRecoveryServiceChange {

    @Autowired
    private PasswordRecoveryService service;

    @Autowired
    private UserRepository          userRepository;

    public ITPasswordRecoveryServiceChange() {
        super();
    }

    @Test
    @DisplayName("Changing password with an existing user changes the password")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_Existing_Changed() {
        final PersistentUser user;

        service.changePassword(TokenConstants.TOKEN, "abc");

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertNotEquals("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW", user.getPassword());
    }

    @Test
    @DisplayName("Changing password with an existing user gives an OK")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_Existing_Status() {
        final PasswordRecoveryStatus status;

        status = service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.assertTrue(status.getSuccessful());
    }

    @Test
    @DisplayName("Changing password with an expired token gives a failure")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/expired.sql" })
    public final void testChangePassword_ExpiredToken_Status() {
        final PasswordRecoveryStatus status;

        status = service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @DisplayName("Changing password with a not existing token gives a failure")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_NotExistingToken_Status() {
        final PasswordRecoveryStatus status;

        status = service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @DisplayName("Changing password with a not existing user gives a failure")
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_NotExistingUser_Status() {
        final PasswordRecoveryStatus status;

        status = service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @DisplayName("Changing password with a valid token after expiration date gives a failure")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/not_expired_after_expiration.sql" })
    public final void testChangePassword_TokenAfterExpirationDate_Status() {
        final PasswordRecoveryStatus status;

        status = service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

}
