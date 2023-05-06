
package com.bernardomg.security.password.change.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password - token expiration")
public class ITPasswordRecoveryServiceChangeExpireToken {

    @Autowired
    private PasswordRecoveryService service;

    @Autowired
    private TokenRepository         tokenRepository;

    public ITPasswordRecoveryServiceChangeExpireToken() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an existing user marks the token as expired")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
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

        service.changePassword(TokenConstants.TOKEN, "abc");

        expiredAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();

        Assertions.assertFalse(expiredBefore);
        Assertions.assertTrue(expiredAfter);
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an incorrect password doesn't mark the token as expired")
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_IncorrectPassword_NotExpireToken() {
        final Boolean expiredBefore;
        final Boolean expiredAfter;

        expiredBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();

        service.changePassword(TokenConstants.TOKEN, "abc");

        expiredAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();

        Assertions.assertFalse(expiredBefore);
        Assertions.assertFalse(expiredAfter);
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a not existing user doesn't mark the token as expired")
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_NotExistingUser_NotExpireToken() {
        final Boolean expiredBefore;
        final Boolean expiredAfter;

        expiredBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();

        service.changePassword(TokenConstants.TOKEN, "abc");

        expiredAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();

        Assertions.assertFalse(expiredBefore);
        Assertions.assertFalse(expiredAfter);
    }

}
