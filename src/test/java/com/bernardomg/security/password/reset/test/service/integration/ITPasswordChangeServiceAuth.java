
package com.bernardomg.security.password.reset.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.change.service.PasswordChangeService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("PasswordChangeService - change password")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/user/single.sql", "/db/queries/security/user/alternative.sql",
        "/db/queries/security/relationship/role_privilege.sql", "/db/queries/security/relationship/user_role.sql" })
public class ITPasswordChangeServiceAuth {

    @Autowired
    private PasswordChangeService service;

    public ITPasswordChangeServiceAuth() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when trying to edit another user")
    @WithMockUser(username = "admin")
    public final void testStartPasswordRecovery_AnotherUser() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.changePassword("user", "1234", "abc");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.user.unauthorized", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the user is not authenticated")
    public final void testStartPasswordRecovery_NotAuthenticated() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.changePassword("admin", "1234", "abc");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.user.unauthorized", exception.getMessage());
    }

}
