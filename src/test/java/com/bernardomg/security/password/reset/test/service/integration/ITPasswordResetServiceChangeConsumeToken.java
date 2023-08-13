
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

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
    @DisplayName("Changing password with an existing user marks the token as consumed")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/password_reset.sql" })
    void testChangePassword_Existing_ConsumeToken() {
        final Boolean consumedBefore;
        final Boolean consumedAfter;

        consumedBefore = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .isConsumed();

        service.changePassword(TokenConstants.TOKEN, "abc");

        consumedAfter = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .isConsumed();

        Assertions.assertThat(consumedBefore)
            .isFalse();

        Assertions.assertThat(consumedAfter)
            .isTrue();
    }

}
