
package com.bernardomg.security.signup.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.signup.service.SignUpService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("UserRegistrationService - validation")
@Sql({ "/db/queries/security/user/single.sql" })
public class ITUserRegistrationServiceValidation {

    @Autowired
    private SignUpService service;

    public ITUserRegistrationServiceValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the email is empty")
    public void testChangePassword_EmptyEmail() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.signUp("abc", "");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.email.invalid", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the username already exists")
    public void testChangePassword_ExistingUsername() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.signUp("admin", "email@somewhere.com");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the email doesn't match the email pattern")
    public void testChangePassword_NoEmailPattern() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.signUp("abc", "abc");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.email.invalid", exception.getMessage());
    }

}
