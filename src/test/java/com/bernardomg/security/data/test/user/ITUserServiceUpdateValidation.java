
package com.bernardomg.security.data.test.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.service.UserService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("User service - add roles validation")
public class ITUserServiceUpdateValidation {

    @Autowired
    private UserService service;

    public ITUserServiceUpdateValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when changing the username")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql" })
    public void testUpdate_ChangeUsername() {
        final Executable executable;
        final Exception  exception;
        final DtoUser    data;

        data = getUser();
        data.setUsername("abc");

        executable = () -> service.update(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.immutable", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the email already exists")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/user/alternative.sql",
            "/db/queries/security/relationship/role_privilege.sql" })
    public void testUpdate_ExistingMail() {
        final Executable executable;
        final Exception  exception;
        final DtoUser    data;

        data = getUser();
        data.setEmail("email2@somewhere.com");

        executable = () -> service.update(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.email.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the email doesn't match the valid pattern")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql" })
    public void testUpdate_InvalidMail() {
        final Executable executable;
        final Exception  exception;
        final DtoUser    data;

        data = getUser();
        data.setEmail("abc");

        executable = () -> service.update(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.email.invalid", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the user doesn't exist")
    public void testUpdate_NotExistingUser() {
        final Executable executable;
        final Exception  exception;
        final User       data;

        data = getUser();

        executable = () -> service.update(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.id.notExisting", exception.getMessage());
    }

    private final DtoUser getUser() {
        final DtoUser user;

        user = new DtoUser();
        user.setId(1L);
        user.setUsername("admin");
        user.setName("Admin");
        user.setEmail("email@somewhere.com");
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        return user;
    }

}
