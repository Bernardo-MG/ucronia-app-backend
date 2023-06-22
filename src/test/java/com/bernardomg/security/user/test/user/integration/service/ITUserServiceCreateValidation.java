
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.ValidatedUserCreate;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@IntegrationTest
@DisplayName("User service - create validation")
public class ITUserServiceCreateValidation {

    @Autowired
    private UserService service;

    public ITUserServiceCreateValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the email already exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testCreate_ExistingEmail() {
        final UserCreate       data;
        final ThrowingCallable executable;
        final FieldFailure     failure;

        data = getUser("abc", "email@somewhere.com");

        executable = () -> service.create(data);

        failure = FieldFailure.of("email.existing", "email", "existing", "email@somewhere.com");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the username already exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testCreate_ExistingUsername() {
        final UserCreate       data;
        final ThrowingCallable executable;
        final FieldFailure     failure;

        data = getUser("admin", "email2@somewhere.com");

        executable = () -> service.create(data);

        failure = FieldFailure.of("username.existing", "username", "existing", "admin");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the email doesn't match the valid pattern")
    public void testCreate_invalidEmail() {
        final UserCreate       data;
        final ThrowingCallable executable;
        final FieldFailure     failure;

        data = getUser("admin", "abc");

        executable = () -> service.create(data);

        failure = FieldFailure.of("email.invalid", "email", "invalid", "abc");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    private final UserCreate getUser(final String username, final String email) {
        return ValidatedUserCreate.builder()
            .username(username)
            .name("Admin")
            .email(email)
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

}
