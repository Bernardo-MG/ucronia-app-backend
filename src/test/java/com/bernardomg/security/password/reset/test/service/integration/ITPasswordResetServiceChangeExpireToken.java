
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.exception.UserDisabledException;
import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password - token expiration")
class ITPasswordResetServiceChangeExpireToken {

    @Autowired
    private PasswordResetService service;

    @Autowired
    private TokenRepository      tokenRepository;

    public ITPasswordResetServiceChangeExpireToken() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an existing user marks the token as consumed")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testChangePassword_Existing_ConsumeToken() {
        final Boolean expiredBefore;
        final Boolean expiredAfter;
        final Boolean consumedBefore;
        final Boolean consumedAfter;

        expiredBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();
        consumedBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getConsumed();

        service.changePassword(TokenConstants.TOKEN, "abc");

        expiredAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();
        consumedAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getConsumed();

        Assertions.assertThat(expiredBefore)
            .isFalse();
        Assertions.assertThat(consumedBefore)
            .isFalse();

        Assertions.assertThat(expiredAfter)
            .isFalse();
        Assertions.assertThat(consumedAfter)
            .isTrue();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an incorrect password doesn't mark the token as expired")
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testChangePassword_IncorrectPassword_NotConsumeToken() {
        final Boolean          expiredBefore;
        final Boolean          expiredAfter;
        final Boolean          consumedBefore;
        final Boolean          consumedAfter;
        final ThrowingCallable executable;

        expiredBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();
        consumedBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getConsumed();

        executable = () -> service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.catchThrowableOfType(executable, UserDisabledException.class);

        expiredAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();
        consumedAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getConsumed();

        Assertions.assertThat(expiredBefore)
            .isFalse();
        Assertions.assertThat(consumedBefore)
            .isFalse();

        Assertions.assertThat(expiredAfter)
            .isFalse();
        Assertions.assertThat(consumedAfter)
            .isTrue();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a not existing user doesn't mark the token as expired")
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testChangePassword_NotExistingUser_NotConsumeToken() {
        final Boolean          expiredBefore;
        final Boolean          expiredAfter;
        final Boolean          consumedBefore;
        final Boolean          consumedAfter;
        final ThrowingCallable executable;

        expiredBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();
        consumedBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getConsumed();

        executable = () -> service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.catchThrowableOfType(executable, UserDisabledException.class);

        expiredAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getExpired();
        consumedAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getConsumed();

        Assertions.assertThat(expiredBefore)
            .isFalse();
        Assertions.assertThat(consumedBefore)
            .isFalse();

        Assertions.assertThat(expiredAfter)
            .isFalse();
        Assertions.assertThat(consumedAfter)
            .isTrue();
    }

}
