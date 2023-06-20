
package com.bernardomg.security.password.change.test.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.model.PasswordRecoveryStatus;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

@IntegrationTest
@DisplayName("Full password recovery process")
public class ITFullPasswordRecoveryProcess {

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;

    @Autowired
    private TokenRepository         tokenRepository;

    @Autowired
    private UserRepository          userRepository;

    public ITFullPasswordRecoveryProcess() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Can follow the password recovery from start to end")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testRecoverPassword_Valid() {
        final PasswordRecoveryStatus recoveryStatus;
        final PasswordRecoveryStatus changeStatus;
        final PasswordRecoveryStatus validTokenStatus;
        final String                 token;
        final PersistentUser         user;

        recoveryStatus = passwordRecoveryService.startPasswordRecovery("email@somewhere.com");

        Assertions.assertThat(recoveryStatus.getSuccessful())
            .isTrue();

        token = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getToken();

        validTokenStatus = passwordRecoveryService.validateToken(token);

        Assertions.assertThat(validTokenStatus.getSuccessful())
            .isTrue();

        changeStatus = passwordRecoveryService.changePassword(token, "abc");

        Assertions.assertThat(changeStatus.getSuccessful())
            .isTrue();

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertThat(user.getPassword())
            .isNotEqualTo("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW");
    }

}
