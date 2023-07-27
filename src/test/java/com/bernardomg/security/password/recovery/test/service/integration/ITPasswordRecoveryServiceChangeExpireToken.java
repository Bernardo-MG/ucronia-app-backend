
package com.bernardomg.security.password.recovery.test.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password - token expiration")
class ITPasswordRecoveryServiceChangeExpireToken {

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
    void testChangePassword_Existing_ExpireToken() {
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

        Assertions.assertThat(expiredBefore)
            .isFalse();
        Assertions.assertThat(expiredAfter)
            .isTrue();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an incorrect password doesn't mark the token as expired")
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testChangePassword_IncorrectPassword_NotExpireToken() {
        final Boolean          expiredBefore;
        final Boolean          expiredAfter;
        final ThrowingCallable executable;

        expiredBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();

        executable = () -> service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.catchThrowableOfType(executable, UsernameNotFoundException.class);

        expiredAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();

        Assertions.assertThat(expiredBefore)
            .isFalse();
        Assertions.assertThat(expiredAfter)
            .isFalse();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a not existing user doesn't mark the token as expired")
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testChangePassword_NotExistingUser_NotExpireToken() {
        final Boolean          expiredBefore;
        final Boolean          expiredAfter;
        final ThrowingCallable executable;

        expiredBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();

        executable = () -> service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.catchThrowableOfType(executable, UsernameNotFoundException.class);

        expiredAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();

        Assertions.assertThat(expiredBefore)
            .isFalse();
        Assertions.assertThat(expiredAfter)
            .isFalse();
    }

}
