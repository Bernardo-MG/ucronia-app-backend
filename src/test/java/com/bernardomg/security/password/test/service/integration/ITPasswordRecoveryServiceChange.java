
package com.bernardomg.security.password.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.password.service.PasswordRecoveryService;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password")
public class ITPasswordRecoveryServiceChange {

    @Autowired
    private PasswordRecoveryService service;

    @Autowired
    private TokenRepository         tokenRepository;

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

        service.changePassword(TokenConstants.TOKEN, "1234", "abc");

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertEquals("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW", user.getPassword());
    }

    @Test
    @DisplayName("Changing password with an existing user marks the token as expired")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_Existing_ExpireToken() {
        final Boolean expiredBefore;
        final Boolean expiredAfter;

        expiredBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();

        service.changePassword(TokenConstants.TOKEN, "1234", "abc");

        expiredAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();

        Assertions.assertFalse(expiredBefore);
        Assertions.assertTrue(expiredAfter);
    }

    @Test
    @DisplayName("Changing password with an existing user gives an OK")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_Existing_Status() {
        final Boolean status;

        status = service.changePassword(TokenConstants.TOKEN, "1234", "abc");

        Assertions.assertTrue(status);
    }

    @Test
    @DisplayName("Changing password with an expired token gives a failure")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/expired.sql" })
    public final void testChangePassword_ExpiredToken_Status() {
        final Boolean status;

        status = service.changePassword(TokenConstants.TOKEN, "1234", "abc");

        Assertions.assertFalse(status);
    }

    @Test
    @DisplayName("Changing password with an incorrect password gives a failure")
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_IncorrectPassword_Status() {
        final Boolean status;

        status = service.changePassword(TokenConstants.TOKEN, "def", "abc");

        Assertions.assertFalse(status);
    }

    @Test
    @DisplayName("Changing password with a not existing token gives a failure")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_NotExistingToken_Status() {
        final Boolean status;

        status = service.changePassword(TokenConstants.TOKEN, "1234", "abc");

        Assertions.assertFalse(status);
    }

    @Test
    @DisplayName("Changing password with a not existing user gives a failure")
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_NotExistingUser_Status() {
        final Boolean status;

        status = service.changePassword(TokenConstants.TOKEN, "1234", "abc");

        Assertions.assertFalse(status);
    }

    @Test
    @DisplayName("Changing password with a valid token after expiration date gives a failure")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/not_expired_after_expiration.sql" })
    public final void testChangePassword_TokenAfterExpirationDate_Status() {
        final Boolean status;

        status = service.changePassword(TokenConstants.TOKEN, "1234", "abc");

        Assertions.assertFalse(status);
    }

}
