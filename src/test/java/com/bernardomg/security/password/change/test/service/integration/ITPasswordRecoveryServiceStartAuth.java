
package com.bernardomg.security.password.change.test.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

@IntegrationTest
@DisplayName("PasswordRecoveryService - token generation on recovery start")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/user/alternative.sql", "/db/queries/security/relationship/role_permission.sql",
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
        final ThrowingCallable      executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        executable = () -> service.startPasswordRecovery("email2@somewhere.com");

        exception = Assertions.catchThrowableOfType(executable, FieldFailureException.class);

        Assertions.assertThat(exception.getFailures())
            .hasSize(1);

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertThat(failure.getCode())
            .isEqualTo("invalid");
        Assertions.assertThat(failure.getField())
            .isEqualTo("email");
        Assertions.assertThat(failure.getMessage())
            .isEqualTo("email.invalid");
    }

    @Test
    @DisplayName("Throws an exception when the user is not authenticated")
    public final void testStartPasswordRecovery_NotAuthenticated() {
        final ThrowingCallable      executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        executable = () -> service.startPasswordRecovery("email@somewhere.com");

        exception = Assertions.catchThrowableOfType(executable, FieldFailureException.class);

        Assertions.assertThat(exception.getFailures())
            .hasSize(1);

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertThat(failure.getCode())
            .isEqualTo("invalid");
        Assertions.assertThat(failure.getField())
            .isEqualTo("email");
        Assertions.assertThat(failure.getMessage())
            .isEqualTo("email.invalid");
    }

}
