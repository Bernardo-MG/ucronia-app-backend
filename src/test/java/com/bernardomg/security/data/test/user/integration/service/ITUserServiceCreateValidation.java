
package com.bernardomg.security.data.test.user.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.ImmutableUser;
import com.bernardomg.security.data.service.UserService;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

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
        final ImmutableUser         data;
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        data = getUser("abc", "email@somewhere.com");

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(FieldFailureException.class, executable);

        Assertions.assertEquals(1, exception.getFailures()
            .size());

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertEquals("existing", failure.getCode());
        Assertions.assertEquals("email", failure.getField());
        Assertions.assertEquals("email.existing", failure.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the username already exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testCreate_ExistingUsername() {
        final ImmutableUser         data;
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        data = getUser("admin", "email2@somewhere.com");

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(FieldFailureException.class, executable);

        Assertions.assertEquals(1, exception.getFailures()
            .size());

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertEquals("existing", failure.getCode());
        Assertions.assertEquals("username", failure.getField());
        Assertions.assertEquals("username.existing", failure.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the email doesn't match the valid pattern")
    public void testCreate_invalidEmail() {
        final ImmutableUser         data;
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        data = getUser("admin", "abc");

        executable = () -> service.create(data);

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

    private final ImmutableUser getUser(final String username, final String email) {
        return ImmutableUser.builder()
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
