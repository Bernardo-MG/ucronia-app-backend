
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("User service - enable new user - token status")
class ITUserServiceEnableNewUser {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService     service;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository  userRepository;

    public ITUserServiceEnableNewUser() {
        super();
    }

    @Test
    @DisplayName("Enabling a new user consumes the token")
    @Sql({ "/db/queries/security/user/disabled.sql" })
    @Sql({ "/db/queries/security/token/user_registered.sql" })
    void testEnableNewUser_ConsumesToken() {
        final Boolean consumed;

        service.activateNewUser(TokenConstants.TOKEN, "1234");

        consumed = tokenRepository.findById(1L)
            .get()
            .isConsumed();

        Assertions.assertThat(consumed)
            .isTrue();
    }

    @Test
    @DisplayName("Enabling a new user sets it as enabled")
    @Sql({ "/db/queries/security/user/disabled.sql" })
    @Sql({ "/db/queries/security/token/user_registered.sql" })
    void testEnableNewUser_Enabled() {
        final PersistentUser user;

        service.activateNewUser(TokenConstants.TOKEN, "1234");

        user = userRepository.findById(1L)
            .get();

        Assertions.assertThat(user.getEnabled())
            .isTrue();
    }

    @Test
    @DisplayName("Enabling a new user sets it's password")
    @Sql({ "/db/queries/security/user/disabled.sql" })
    @Sql({ "/db/queries/security/token/user_registered.sql" })
    void testEnableNewUser_Password() {
        final PersistentUser user;

        service.activateNewUser(TokenConstants.TOKEN, "1234");

        user = userRepository.findById(1L)
            .get();

        Assertions.assertThat(passwordEncoder.matches("1234", user.getPassword()))
            .isTrue();
    }

    @Test
    @DisplayName("Enabling a new user sets password expired flag ot false")
    @Sql({ "/db/queries/security/user/disabled.sql" })
    @Sql({ "/db/queries/security/token/user_registered.sql" })
    void testEnableNewUser_PasswordReset() {
        final PersistentUser user;

        service.activateNewUser(TokenConstants.TOKEN, "1234");

        user = userRepository.findById(1L)
            .get();

        Assertions.assertThat(user.getPasswordExpired())
            .isFalse();
    }

}
