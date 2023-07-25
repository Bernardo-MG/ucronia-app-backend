
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.change.service.PasswordChangeService;
import com.bernardomg.security.password.exception.InvalidPasswordChangeException;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

@IntegrationTest
@DisplayName("PasswordChangeService - change password")
class ITPasswordChangeService {

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
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testChangePassword_Existing_Changed() {
        final PersistentUser user;

        service.changePasswordForUserInSession("1234", "abc");

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertThat(user.getPassword())
            .isNotEqualTo("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an incorrect password gives a failure")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testChangePassword_IncorrectPassword_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.changePasswordForUserInSession("def", "abc");

        exception = Assertions.catchThrowableOfType(executable, BadCredentialsException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Received a password which doesn't match the one stored for admin");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an existing user changes the password")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testChangePassword_Long_Changed() {
        final PersistentUser user;

        service.changePasswordForUserInSession("1234",
            "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertThat(user.getPassword())
            .isNotEqualTo("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a not existing user gives a failure")
    void testChangePassword_NotExistingUser_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.changePasswordForUserInSession("1234", "abc");

        exception = Assertions.catchThrowableOfType(executable, InvalidPasswordChangeException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Couldn't change password for user, as it doesn't exist");
    }

}
