
package com.bernardomg.security.password.recovery.test.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.password.recovery.model.PasswordRecoveryStatus;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

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
    @DisplayName("Can follow the password recovery from start to end")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testRecoverPassword_Valid() {
        final PasswordRecoveryStatus recoveryStatus;
        final PasswordRecoveryStatus changeStatus;
        final PasswordRecoveryStatus validTokenStatus;
        final String                 token;
        final PersistentUser         user;

        recoveryStatus = passwordRecoveryService.startPasswordRecovery("email@somewhere.com");

        Assertions.assertTrue(recoveryStatus.getSuccessful());

        token = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getToken();

        validTokenStatus = passwordRecoveryService.validateToken(token);

        Assertions.assertTrue(validTokenStatus.getSuccessful());

        changeStatus = passwordRecoveryService.changePassword(token, "1234", "abc");

        Assertions.assertTrue(changeStatus.getSuccessful());

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertNotEquals("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW", user.getPassword());
    }

}
