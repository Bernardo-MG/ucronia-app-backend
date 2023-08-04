
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.password.reset.service.PasswordResetService;
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
    @WithMockUser(username = "admin")
    @DisplayName("A consumed token is not valid")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/consumed.sql" })
    void testValidateToken_Consumed() {
        final boolean status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status)
            .isFalse();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("An expired token is not valid")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testValidateToken_Expired() {
        final boolean status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status)
            .isFalse();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("A valid token is valid")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testValidateToken_Valid() {
        final boolean status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status)
            .isTrue();
    }

}
