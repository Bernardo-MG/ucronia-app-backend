
package com.bernardomg.security.register.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.register.service.RegisterUserService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("Register user - validation")
@Sql({ "/db/queries/security/user/single.sql" })
public class ITRegisterUserServiceValidation {

    @Autowired
    private RegisterUserService service;

    public ITRegisterUserServiceValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the username already exists")
    public void testChangePassword_ExistingUsername() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.registerUser("admin", "email", "1234");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

}
