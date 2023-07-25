
package com.bernardomg.security.password.recovery.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.model.PasswordRecoveryStatus;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password")
class ITPasswordRecoveryServiceChange {

    @Autowired
    private PasswordRecoveryService service;

    @Autowired
    private UserRepository          userRepository;

    public ITPasswordRecoveryServiceChange() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an existing user changes the password")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    void testChangePassword_Existing_Changed() {
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
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a valid token after expiration date gives a failure")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/not_expired_after_expiration.sql" })
    void testChangePassword_TokenAfterExpirationDate_Status() {
        final PasswordRecoveryStatus status;

        status = service.changePassword(TokenConstants.TOKEN, "abc");

        Assertions.assertThat(status.getSuccessful())
            .isFalse();
    }

}
