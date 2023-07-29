
package com.bernardomg.security.password.reset.test.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

@IntegrationTest
@DisplayName("Full password recovery process")
class ITFullPasswordResetProcess {

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
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testRecoverPassword_Valid() {
        final boolean        validTokenStatus;
        final String         token;
        final PersistentUser user;

        service.startPasswordRecovery("email@somewhere.com");

        token = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getToken();

        validTokenStatus = service.validateToken(token);

        Assertions.assertThat(validTokenStatus)
            .isTrue();

        service.changePassword(token, "abc");

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertThat(user.getPassword())
            .isNotEqualTo("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW");
    }

}
