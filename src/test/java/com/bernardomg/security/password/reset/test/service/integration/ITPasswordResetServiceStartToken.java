
package com.bernardomg.security.password.reset.test.service.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PasswordRecoveryService - token generation on recovery start")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/role_permission.sql", "/db/queries/security/relationship/user_role.sql" })
class ITPasswordResetServiceStartToken {

    @Autowired
    private PasswordResetService service;

    @Autowired
    private TokenRepository      tokenRepository;

    public ITPasswordResetServiceStartToken() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Starting password recovery with an existing user generates a token")
    void testStartPasswordRecovery_Exists_Token() {
        final Optional<PersistentToken> token;

        service.startPasswordRecovery("email@somewhere.com");

        token = tokenRepository.findAll()
            .stream()
            .findFirst();

        Assertions.assertThat(token)
            .isPresent();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Starting password recovery with a not existing user doesn't generate a token")
    void testStartPasswordRecovery_NotExists_NoToken() {
        final Optional<PersistentToken> token;

        try {
            service.startPasswordRecovery("email2@somewhere.com");
        } catch (final Exception e) {

        }

        token = tokenRepository.findAll()
            .stream()
            .findFirst();

        Assertions.assertThat(token)
            .isNotPresent();
    }

}
