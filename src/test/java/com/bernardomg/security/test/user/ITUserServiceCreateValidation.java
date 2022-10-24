
package com.bernardomg.security.test.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.DtoUser;
import com.bernardomg.security.service.UserService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("User service - create validation")
@Sql({ "/db/queries/security/user/single.sql" })
public class ITUserServiceCreateValidation {

    @Autowired
    private UserService service;

    public ITUserServiceCreateValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the username already exists")
    public void testCreate_ExistingUsername() {
        final DtoUser    data;
        final Executable executable;
        final Exception  exception;

        data = new DtoUser();
        data.setUsername("admin");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

}
