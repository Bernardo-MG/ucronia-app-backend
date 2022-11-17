
package com.bernardomg.security.password.reset.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.change.service.PasswordChangeService;

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
    @DisplayName("Throws an exception when the user is not authenticated")
    public final void testStartPasswordRecovery_NotAuthenticated() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.changePassword("1234", "abc");

        exception = Assertions.assertThrows(UsernameNotFoundException.class, executable);

        Assertions.assertEquals("user", exception.getMessage());
    }

}
