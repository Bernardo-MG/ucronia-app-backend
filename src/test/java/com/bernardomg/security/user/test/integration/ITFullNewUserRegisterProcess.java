
package com.bernardomg.security.user.test.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.ValidatedUserCreate;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Full new user register process")
class ITFullNewUserRegisterProcess {

    @Autowired
    private UserService     service;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository  userRepository;

    public ITFullNewUserRegisterProcess() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Can follow the new user process from start to end")
    void testResetPassword_Valid() {
        final boolean        validTokenStatus;
        final String         token;
        final PersistentUser user;
        final UserCreate     newUser;

        // Register new user
        newUser = ValidatedUserCreate.builder()
            .email("email@somewhere.com")
            .name("username")
            .build();
        service.registerNewUser(newUser);

        // Validate new token
        token = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getToken();

        validTokenStatus = service.validateToken(token);

        Assertions.assertThat(validTokenStatus)
            .isTrue();

        // Enable new user
        service.enableNewUser(token, "username");

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertThat(user.getEnabled())
            .isTrue();
    }

}
