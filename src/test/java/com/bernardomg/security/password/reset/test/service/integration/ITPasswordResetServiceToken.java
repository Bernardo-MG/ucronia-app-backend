
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.user.test.config.ValidUser;
import com.bernardomg.security.user.token.model.UserTokenStatus;
import com.bernardomg.security.user.token.test.config.annotation.PasswordResetConsumedToken;
import com.bernardomg.security.user.token.test.config.annotation.PasswordResetExpiredToken;
import com.bernardomg.security.user.token.test.config.annotation.PasswordResetToken;
import com.bernardomg.security.user.token.test.config.constant.TokenConstants;
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
    @ValidUser
    @PasswordResetConsumedToken
    void testValidateToken_Consumed() {
        final UserTokenStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.isValid())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("An expired token is not valid")
    @ValidUser
    @PasswordResetExpiredToken
    void testValidateToken_Expired() {
        final UserTokenStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.isValid())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("A valid token is valid")
    @ValidUser
    @PasswordResetToken
    void testValidateToken_Valid() {
        final UserTokenStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.isValid())
            .isTrue();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

}
