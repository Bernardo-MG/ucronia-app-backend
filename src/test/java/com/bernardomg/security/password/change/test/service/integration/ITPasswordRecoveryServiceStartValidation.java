
package com.bernardomg.security.password.change.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

@IntegrationTest
@DisplayName("PasswordRecoveryService - recovery start - validation")
public class ITPasswordRecoveryServiceStartValidation {

    @Autowired
    private PasswordRecoveryService service;

    public ITPasswordRecoveryServiceStartValidation() {
        super();
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Throws a validation exception with the correct info when the email doesn't match the user email")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/privilege/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/user/alternative.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testStartPasswordRecovery_InvalidEmail() {
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        executable = () -> service.startPasswordRecovery("email2@somewhere.com");

        exception = Assertions.assertThrows(FieldFailureException.class, executable);

        Assertions.assertEquals(1, exception.getFailures()
            .size());

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertEquals("invalid", failure.getCode());
        Assertions.assertEquals("email", failure.getField());
        Assertions.assertEquals("email.invalid", failure.getMessage());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Throws a validation exception with the correct info when there is no user")
    public final void testStartPasswordRecovery_NoUser() {
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        executable = () -> service.startPasswordRecovery("email@somewhere.com");

        exception = Assertions.assertThrows(FieldFailureException.class, executable);

        Assertions.assertEquals(1, exception.getFailures()
            .size());

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertEquals("invalid", failure.getCode());
        Assertions.assertEquals("email", failure.getField());
        Assertions.assertEquals("email.invalid", failure.getMessage());
    }

}
