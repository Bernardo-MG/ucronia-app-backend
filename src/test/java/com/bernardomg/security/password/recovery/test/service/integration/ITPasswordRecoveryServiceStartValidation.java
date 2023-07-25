
package com.bernardomg.security.password.recovery.test.service.integration;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

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
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/user/alternative.sql", "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testStartPasswordRecovery_InvalidEmail() {
        final ThrowingCallable executable;
        final FieldFailure     failure;

        executable = () -> service.startPasswordRecovery("email2@somewhere.com");

        failure = FieldFailure.of("email.invalid", "email", "invalid", "email2@somewhere.com");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Throws a validation exception with the correct info when there is no user")
    void testStartPasswordRecovery_NoUser() {
        final ThrowingCallable executable;
        final FieldFailure     failure;

        executable = () -> service.startPasswordRecovery("email@somewhere.com");

        failure = FieldFailure.of("email.invalid", "email", "invalid", "email@somewhere.com");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

}
