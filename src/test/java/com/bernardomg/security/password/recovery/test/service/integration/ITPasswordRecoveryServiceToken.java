
package com.bernardomg.security.password.recovery.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.model.PasswordRecoveryStatus;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.security.test.constant.TokenConstants;

@IntegrationTest
@DisplayName("PasswordRecoveryService - Token verification")
class ITPasswordRecoveryServiceToken {

    @Autowired
    private PasswordRecoveryService service;

    public ITPasswordRecoveryServiceToken() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("An expired token is not verifiable")
    @Sql({ "/db/queries/security/token/expired.sql" })
    void testValidateToken_Expired() {
        final PasswordRecoveryStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.getSuccessful())
            .isFalse();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("A not expired token but after expiration date is not verifiable")
    @Sql({ "/db/queries/security/token/not_expired_after_expiration.sql" })
    void testValidateToken_NotExpiredAfterExpiration() {
        final PasswordRecoveryStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.getSuccessful())
            .isFalse();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("A valid token is verifiable")
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testValidateToken_Valid() {
        final PasswordRecoveryStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.getSuccessful())
            .isTrue();
    }

}
