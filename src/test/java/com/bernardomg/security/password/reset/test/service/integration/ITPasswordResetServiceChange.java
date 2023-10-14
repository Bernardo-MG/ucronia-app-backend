
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.test.config.PasswordResetToken;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.test.config.ExpiredPasswordUser;
import com.bernardomg.security.user.test.config.ValidUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password")
class ITPasswordResetServiceChange {

    @Autowired
    private PasswordResetService service;

    @Autowired
    private TokenRepository      tokenRepository;

    @Autowired
    private UserRepository       userRepository;

    public ITPasswordResetServiceChange() {
        super();
    }

    @Test
    @DisplayName("Changing password with a valid user changes the password")
    @ValidUser
    @PasswordResetToken
    void testChangePassword_Changed() {
        final PersistentUser user;

        service.changePassword(TokenConstants.TOKEN, "abc");

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertThat(user.getPassword())
            .isNotEqualTo("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW");
    }

    @Test
    @DisplayName("Changing password with an existing user marks the token as consumed")
    @ValidUser
    @PasswordResetToken
    void testChangePassword_ConsumesToken() {
        final Boolean consumed;

        service.changePassword(TokenConstants.TOKEN, "abc");

        consumed = tokenRepository.findById(1L)
            .get()
            .isConsumed();

        Assertions.assertThat(consumed)
            .isTrue();
    }

    @Test
    @DisplayName("Changing password with expired password resets the flag")
    @ExpiredPasswordUser
    @PasswordResetToken
    void testChangePassword_ExpiredPassword() {
        final PersistentUser user;

        service.changePassword(TokenConstants.TOKEN, "abc");

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertThat(user.getPasswordExpired())
            .isFalse();
    }

}
