
package com.bernardomg.security.password.reset.test.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.token.model.TokenStatus;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Full password reset process")
class ITFullPasswordResetProcess {

    @Autowired
    private PasswordEncoder      passwordEncoder;

    @Autowired
    private PasswordResetService service;

    @Autowired
    private TokenRepository      tokenRepository;

    @Autowired
    private UserRepository       userRepository;

    public ITFullPasswordResetProcess() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Can follow the password recovery from start to end")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/permission/crud.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testResetPassword_Valid() {
        final TokenStatus    validTokenStatus;
        final String         token;
        final PersistentUser user;

        // Start password reset
        service.startPasswordReset("email@somewhere.com");

        // Validate new token
        token = tokenRepository.findAll()
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
