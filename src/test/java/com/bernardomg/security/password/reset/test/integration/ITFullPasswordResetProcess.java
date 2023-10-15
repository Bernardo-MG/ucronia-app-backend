
package com.bernardomg.security.password.reset.test.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.test.config.ValidUser;
import com.bernardomg.security.user.token.model.UserTokenStatus;
import com.bernardomg.security.user.token.persistence.repository.UserTokenRepository;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Full password reset process")
class ITFullPasswordResetProcess {

    @Autowired
    private PasswordEncoder      passwordEncoder;

    @Autowired
    private PasswordResetService service;

    @Autowired
    private UserRepository       userRepository;

    @Autowired
    private UserTokenRepository  userTokenRepository;

    public ITFullPasswordResetProcess() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Can follow the password recovery from start to end")
    @ValidUser
    void testResetPassword() {
        final UserTokenStatus validTokenStatus;
        final String          token;
        final PersistentUser  user;

        // Start password reset
        service.startPasswordReset("email@somewhere.com");

        // Validate new token
        token = userTokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getToken();

        validTokenStatus = service.validateToken(token);

        Assertions.assertThat(validTokenStatus.getValid())
            .isTrue();
        Assertions.assertThat(validTokenStatus.getUsername())
            .isEqualTo("admin");

        // Change password
        service.changePassword(token, "abc");

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertThat(passwordEncoder.matches("abc", user.getPassword()))
            .isTrue();
    }

}
