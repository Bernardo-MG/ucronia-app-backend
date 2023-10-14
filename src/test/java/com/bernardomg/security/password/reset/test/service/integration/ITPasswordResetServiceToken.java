
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.token.model.TokenStatus;
import com.bernardomg.security.token.test.config.ConsumedToken;
import com.bernardomg.security.token.test.config.ExpiredToken;
import com.bernardomg.security.token.test.config.PasswordResetToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.test.config.ValidUser;
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
    @ConsumedToken
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
    @ValidUser
    @ExpiredToken
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
    @ValidUser
    @PasswordResetToken
    void testValidateToken_Valid() {
        final TokenStatus status;

        status = service.validateToken(TokenConstants.TOKEN);

        Assertions.assertThat(status.getValid())
            .isTrue();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

}
