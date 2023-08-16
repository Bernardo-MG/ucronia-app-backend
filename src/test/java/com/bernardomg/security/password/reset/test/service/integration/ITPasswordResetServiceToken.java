
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.token.model.TokenStatus;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PasswordRecoveryService - Token verification")
class ITPasswordResetServiceToken {

    @Autowired
    private PasswordResetService service;

    public ITPasswordResetServiceToken() {
        super();
    }

    @Test
    @DisplayName("A consumed token is not valid")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/consumed.sql" })
    void testValidateToken_Consumed() {
        final TokenStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.getValid())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("An expired token is not valid")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testValidateToken_Expired() {
        final TokenStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.getValid())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("A valid token is valid")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/password_reset.sql" })
    void testValidateToken_Valid() {
        final TokenStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.getValid())
            .isTrue();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

}
