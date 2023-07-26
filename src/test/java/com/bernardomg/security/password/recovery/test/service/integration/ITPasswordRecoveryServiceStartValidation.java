
package com.bernardomg.security.password.recovery.test.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;

@IntegrationTest
@DisplayName("PasswordRecoveryService - recovery start - validation")
class ITPasswordRecoveryServiceStartValidation {

    @Autowired
    private PasswordRecoveryService service;

    public ITPasswordRecoveryServiceStartValidation() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Throws a validation exception with the correct info when the email doesn't match the user email")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/credentials_expired.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testStartPasswordRecovery_InvalidEmail() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.startPasswordRecovery("email2@somewhere.com");

        exception = Assertions.catchThrowableOfType(executable, UsernameNotFoundException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Couldn't change password for email email2@somewhere.com, as no user exists for it");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Throws a validation exception with the correct info when there is no user")
    void testStartPasswordRecovery_NoUser() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.startPasswordRecovery("email@somewhere.com");

        exception = Assertions.catchThrowableOfType(executable, UsernameNotFoundException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Couldn't change password for email email@somewhere.com, as no user exists for it");
    }

}
