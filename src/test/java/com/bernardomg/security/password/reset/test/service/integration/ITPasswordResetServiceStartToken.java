
package com.bernardomg.security.password.reset.test.service.integration;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.token.persistence.model.PersistentToken;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.test.config.PasswordResetToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.test.config.ValidUser;
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
    @DisplayName("Starting password recovery generates a token")
    @ValidUser
    void testStartPasswordReset_CreatedToken() {
        final long count;

        service.startPasswordReset("email@somewhere.com");

        count = tokenRepository.count();

        Assertions.assertThat(count)
            .isOne();
    }

    @Test
    @DisplayName("Starting password recovery populates the created token")
    @ValidUser
    void testStartPasswordReset_TokenData() {
        final PersistentToken token;

        service.startPasswordReset("email@somewhere.com");

        token = tokenRepository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(token.getToken())
            .isNotNull();
        Assertions.assertThat(token.getScope())
            .isEqualTo("password_reset");
        Assertions.assertThat(token.getExpirationDate())
            .isAfter(LocalDateTime.now());
        Assertions.assertThat(token.isConsumed())
            .isFalse();
        Assertions.assertThat(token.isRevoked())
            .isFalse();
    }

    @Test
    @DisplayName("Starting password recovery with an existing token for the user generates a new token")
    @ValidUser
    @PasswordResetToken
    void testStartPasswordReset_TokenExists_CreatedToken() {
        final long count;

        service.startPasswordReset("email@somewhere.com");

        count = tokenRepository.count();

        Assertions.assertThat(count)
            .isEqualTo(2);
    }

    @Test
    @DisplayName("Starting password recovery with an existing token for the user revokes the older one")
    @ValidUser
    @PasswordResetToken
    void testStartPasswordReset_TokenExists_ExpiresToken() {
        final PersistentToken token;

        service.startPasswordReset("email@somewhere.com");

        token = tokenRepository.findOneByToken(TokenConstants.TOKEN)
            .get();

        Assertions.assertThat(token.isRevoked())
            .isTrue();
    }

    @Test
    @DisplayName("Starting password recovery with a not existing user doesn't generate a token")
    @ValidUser
    void testStartPasswordReset_UserNotExists_NoToken() {
        final boolean exists;

        try {
            service.startPasswordReset("email2@somewhere.com");
        } catch (final Exception e) {

        }

        exists = tokenRepository.exists(Example.of(new PersistentToken()));

        Assertions.assertThat(exists)
            .isFalse();
    }

}
