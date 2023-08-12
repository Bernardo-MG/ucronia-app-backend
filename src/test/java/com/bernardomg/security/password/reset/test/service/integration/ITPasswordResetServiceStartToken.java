
package com.bernardomg.security.password.reset.test.service.integration;

import java.util.Calendar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PasswordRecoveryService - token generation on recovery start")
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
    @DisplayName("Starting password recovery generates a token")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testStartPasswordRecovery_CreatedToken() {
        final long count;

        service.startPasswordRecovery("email@somewhere.com");

        count = tokenRepository.count();

        Assertions.assertThat(count)
            .isOne();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Starting password recovery populates the created token")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testStartPasswordRecovery_TokenData() {
        final PersistentToken token;

        service.startPasswordRecovery("email@somewhere.com");

        token = tokenRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(token.getToken())
            .isNotNull();
        Assertions.assertThat(token.getScope())
            .isEqualTo("password_reset");
        Assertions.assertThat(token.getExpirationDate())
            .isGreaterThan(Calendar.getInstance());
        Assertions.assertThat(token.isConsumed())
            .isFalse();
        Assertions.assertThat(token.isRevoked())
            .isFalse();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Starting password recovery with an existing token for the user generates a new token")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/password_reset.sql" })
    void testStartPasswordRecovery_TokenExists_CreatedToken() {
        final long count;

        service.startPasswordRecovery("email@somewhere.com");

        count = tokenRepository.count();

        Assertions.assertThat(count)
            .isEqualTo(2);
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Starting password recovery with an existing token for the user revokes the older one")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/password_reset.sql" })
    void testStartPasswordRecovery_TokenExists_ExpiresToken() {
        final PersistentToken token;

        service.startPasswordRecovery("email@somewhere.com");

        token = tokenRepository.findOneByToken(TokenConstants.TOKEN)
            .get();

        Assertions.assertThat(token.isRevoked())
            .isTrue();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Starting password recovery with a not existing user doesn't generate a token")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testStartPasswordRecovery_UserNotExists_NoToken() {
        final boolean exists;

        try {
            service.startPasswordRecovery("email2@somewhere.com");
        } catch (final Exception e) {

        }

        exists = tokenRepository.exists(Example.of(new PersistentToken()));

        Assertions.assertThat(exists)
            .isFalse();
    }

}
