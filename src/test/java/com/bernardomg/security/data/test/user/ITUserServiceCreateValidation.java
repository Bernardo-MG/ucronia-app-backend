
package com.bernardomg.security.data.test.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.service.UserService;
import com.bernardomg.validation.exception.ValidationException;

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
        final DtoUser    data;
        final Executable executable;
        final Exception  exception;

        data = new DtoUser();
        data.setUsername("abc");
        data.setEmail("email@somewhere.com");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.email.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the username already exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testCreate_ExistingUsername() {
        final DtoUser    data;
        final Executable executable;
        final Exception  exception;

        data = new DtoUser();
        data.setUsername("admin");
        data.setEmail("email2@somewhere.com");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the email doesn't match the valid pattern")
    public void testCreate_invalidEmail() {
        final DtoUser    data;
        final Executable executable;
        final Exception  exception;

        data = new DtoUser();
        data.setUsername("admin");
        data.setName("admin");
        data.setEmail("abc");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.email.invalid", exception.getMessage());
    }

}
