
package com.bernardomg.security.password.reset.test.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.change.service.PasswordChangeService;

@IntegrationTest
@DisplayName("PasswordChangeService - change password")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/user/alternative.sql", "/db/queries/security/relationship/role_permission.sql",
        "/db/queries/security/relationship/user_role.sql" })
class ITPasswordChangeServiceAuth {

    @Autowired
    private PasswordChangeService service;

    public ITPasswordChangeServiceAuth() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the user is not authenticated")
    void testStartPasswordRecovery_NotAuthenticated() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.changePassword("admin", "1234", "abc");

        exception = Assertions.catchThrowableOfType(executable, UsernameNotFoundException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("user");
    }

}
