
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.security.user.test.config.NewlyCreated;
import com.bernardomg.security.user.token.persistence.repository.UserTokenRepository;
import com.bernardomg.security.user.token.test.config.annotation.UserRegisteredToken;
import com.bernardomg.security.user.token.test.config.constant.UserTokenConstants;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("User service - enable new user - token status")
class ITUserServiceEnableNewUser {

    @Autowired
    private PasswordEncoder     passwordEncoder;

    @Autowired
    private UserService         service;

    @Autowired
    private UserRepository      userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    public ITUserServiceEnableNewUser() {
        super();
    }

    @Test
    @DisplayName("Enabling a new user consumes the token")
    @NewlyCreated
    @UserRegisteredToken
    void testEnableNewUser_ConsumesToken() {
        final Boolean consumed;

        service.activateNewUser(UserTokenConstants.TOKEN, "1234");

        consumed = userTokenRepository.findById(1L)
            .get()
            .isConsumed();

        Assertions.assertThat(consumed)
            .isTrue();
    }

    @Test
    @DisplayName("Enabling a new user sets it as enabled")
    @NewlyCreated
    @UserRegisteredToken
    void testEnableNewUser_Enabled() {
        final PersistentUser user;

        service.activateNewUser(UserTokenConstants.TOKEN, "1234");

        user = userRepository.findById(1L)
            .get();

        Assertions.assertThat(user.getEnabled())
            .isTrue();
    }

    @Test
    @DisplayName("Enabling a new user sets it's password")
    @NewlyCreated
    @UserRegisteredToken
    void testEnableNewUser_Password() {
        final PersistentUser user;

        service.activateNewUser(UserTokenConstants.TOKEN, "1234");

        user = userRepository.findById(1L)
            .get();

        Assertions.assertThat(passwordEncoder.matches("1234", user.getPassword()))
            .isTrue();
    }

    @Test
    @DisplayName("Enabling a new user sets password expired flag ot false")
    @NewlyCreated
    @UserRegisteredToken
    void testEnableNewUser_PasswordReset() {
        final PersistentUser user;

        service.activateNewUser(UserTokenConstants.TOKEN, "1234");

        user = userRepository.findById(1L)
            .get();

        Assertions.assertThat(user.getPasswordExpired())
            .isFalse();
    }

}
