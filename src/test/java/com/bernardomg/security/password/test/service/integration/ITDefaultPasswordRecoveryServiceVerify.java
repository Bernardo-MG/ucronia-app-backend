
package com.bernardomg.security.password.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.service.PasswordRecoveryService;

@IntegrationTest
@DisplayName("PasswordRecoveryService - Token verification")
public class ITDefaultPasswordRecoveryServiceVerify {

    @Autowired
    private PasswordRecoveryService service;

    public ITDefaultPasswordRecoveryServiceVerify() {
        super();
    }

    @Test
    @DisplayName("An expired token is not verifiable")
    @Sql({ "/db/queries/security/token/expired.sql" })
    public final void testVerifyToken_Expired() {
        final Boolean valid;

        valid = service.verifyToken("token");

        Assertions.assertFalse(valid);
    }

    @Test
    @DisplayName("A not expired token but after expiration date is not verifiable")
    @Sql({ "/db/queries/security/token/not_expired_after_expiration.sql" })
    public final void testVerifyToken_NotExpiredAfterExpiration() {
        final Boolean valid;

        valid = service.verifyToken("token");

        Assertions.assertFalse(valid);
    }

    @Test
    @DisplayName("A valid token is verifiable")
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testVerifyToken_Valid() {
        final Boolean valid;

        valid = service.verifyToken("token");

        Assertions.assertTrue(valid);
    }

}
