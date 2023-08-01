
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
@DisplayName("PasswordRecoveryService - change password - token consumed")
class ITPasswordResetServiceChangeConsumeToken {

    @Autowired
    private PasswordResetService service;

    @Autowired
    private TokenRepository      tokenRepository;

    public ITPasswordResetServiceChangeConsumeToken() {
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
        final Boolean consumedBefore;
        final Boolean consumedAfter;

        consumedBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getConsumed();

        service.changePassword(TokenConstants.TOKEN, "abc");

        consumedAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getConsumed();

        Assertions.assertThat(consumedBefore)
            .isFalse();

        Assertions.assertThat(consumedAfter)
            .isTrue();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a not existing user doesn't mark the token as expired")
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testChangePassword_NotExistingUser_NotConsumeToken() {
        final Boolean          consumedBefore;
        final Boolean          consumedAfter;
        final ThrowingCallable executable;

        consumedBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getConsumed();

        executable = () -> service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.catchThrowableOfType(executable, UserDisabledException.class);

        consumedAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getConsumed();

        Assertions.assertThat(consumedBefore)
            .isFalse();

        Assertions.assertThat(consumedAfter)
            .isFalse();
    }

}
