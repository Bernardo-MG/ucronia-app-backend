
package com.bernardomg.security.test.register;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.service.RegisterUserService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("User service - create validation")
@Sql({ "/db/queries/security/user/single.sql" })
public class ITRegisterUserServiceValidation {

    @Autowired
    private RegisterUserService service;

    public ITRegisterUserServiceValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the username already exists")
    public void testChangePassword_NotExistingUsername() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.registerUser("admin", "email", "1234");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

}
