
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
@DisplayName("PasswordRecoveryService - token generation on recovery start")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/privilege/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/user/alternative.sql", "/db/queries/security/relationship/role_privilege.sql",
        "/db/queries/security/relationship/user_role.sql" })
public class ITPasswordRecoveryServiceStartAuth {

    @Autowired
    private PasswordRecoveryService service;

    public ITPasswordRecoveryServiceStartAuth() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when trying to edit another user")
    @WithMockUser(username = "admin")
    public final void testStartPasswordRecovery_AnotherUser() {
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
    @DisplayName("Throws an exception when the user is not authenticated")
    public final void testStartPasswordRecovery_NotAuthenticated() {
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
