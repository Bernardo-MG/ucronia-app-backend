
package com.bernardomg.security.password.reset.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.password.change.model.PasswordChangeStatus;
import com.bernardomg.security.password.change.service.PasswordChangeService;

@IntegrationTest
@DisplayName("PasswordChangeService - change password")
public class ITPasswordChangeService {

    @Autowired
    private PasswordChangeService service;

    @Autowired
    private UserRepository        userRepository;

    public ITPasswordChangeService() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an existing user changes the password")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_Existing_Changed() {
        final PersistentUser user;

        service.changePassword("admin", "1234", "abc");

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertNotEquals("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW", user.getPassword());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an existing user gives an OK")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_Existing_Status() {
        final PasswordChangeStatus status;

        status = service.changePassword("admin", "1234", "abc");

        Assertions.assertTrue(status.getSuccessful());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an incorrect password gives a failure")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_IncorrectPassword_Status() {
        final PasswordChangeStatus status;

        status = service.changePassword("admin", "def", "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a not existing user gives a failure")
    public final void testChangePassword_NotExistingUser_Status() {
        final PasswordChangeStatus status;

        status = service.changePassword("admin", "1234", "abc");

        Assertions.assertFalse(status.getSuccessful());
    }

}
