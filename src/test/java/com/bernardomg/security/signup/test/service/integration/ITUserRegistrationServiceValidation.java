
package com.bernardomg.security.signup.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.signup.model.DtoSignUp;
import com.bernardomg.security.signup.service.SignUpService;
import com.bernardomg.validation.failure.exception.FailureException;

@IntegrationTest
@DisplayName("UserRegistrationService - validation")
public class ITUserRegistrationServiceValidation {

    @Autowired
    private SignUpService service;

    public ITUserRegistrationServiceValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the email is empty")
    public void testSignUp_EmptyEmail() {
        final Executable executable;
        final Exception  exception;
        final DtoSignUp  signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("");

        executable = () -> service.signUp(signUp);

        exception = Assertions.assertThrows(FailureException.class, executable);

        Assertions.assertEquals("error.email.invalid", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the email already exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testSignUp_ExistingEmail() {
        final Executable executable;
        final Exception  exception;
        final DtoSignUp  signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("abc");
        signUp.setEmail("email@somewhere.com");

        executable = () -> service.signUp(signUp);

        exception = Assertions.assertThrows(FailureException.class, executable);

        Assertions.assertEquals("error.email.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the email already exists, ignoring case")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testSignUp_ExistingEmail_Case() {
        final Executable executable;
        final Exception  exception;
        final DtoSignUp  signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("abc");
        signUp.setEmail("EMAIL@somewhere.com");

        executable = () -> service.signUp(signUp);

        exception = Assertions.assertThrows(FailureException.class, executable);

        Assertions.assertEquals("error.email.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the username already exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testSignUp_ExistingUsername() {
        final Executable executable;
        final Exception  exception;
        final DtoSignUp  signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("admin");
        signUp.setEmail("email2@somewhere.com");

        executable = () -> service.signUp(signUp);

        exception = Assertions.assertThrows(FailureException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the username already exists, ignoring case")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testSignUp_ExistingUsername_Case() {
        final Executable executable;
        final Exception  exception;
        final DtoSignUp  signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("ADMIN");
        signUp.setEmail("email2@somewhere.com");

        executable = () -> service.signUp(signUp);

        exception = Assertions.assertThrows(FailureException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the email doesn't match the email pattern")
    public void testSignUp_NoEmailPattern() {
        final Executable executable;
        final Exception  exception;
        final DtoSignUp  signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("abc");

        executable = () -> service.signUp(signUp);

        exception = Assertions.assertThrows(FailureException.class, executable);

        Assertions.assertEquals("error.email.invalid", exception.getMessage());
    }

}
