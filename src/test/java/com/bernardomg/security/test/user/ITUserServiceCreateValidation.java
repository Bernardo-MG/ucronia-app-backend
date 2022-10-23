
package com.bernardomg.security.test.user;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.DtoRole;
import com.bernardomg.security.model.DtoUser;
import com.bernardomg.security.model.Role;
import com.bernardomg.security.service.UserService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("User service - create validation")
@Sql({ "/db/queries/security/role/single.sql" })
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
        data.setUsername("User");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the credentials expired flag is not received")
    public void testCreate_MissingCredentialsExpired() {
        final DtoUser    data;
        final Executable executable;
        final Exception  exception;

        data = new DtoUser();
        data.setUsername("User2");
        data.setEmail("email");
        data.setCredentialsExpired(null);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the enabled flag is not received")
    public void testCreate_MissingEnabled() {
        final DtoUser    data;
        final Executable executable;
        final Exception  exception;

        data = new DtoUser();
        data.setUsername("User2");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(null);
        data.setExpired(false);
        data.setLocked(false);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the expired flag is not received")
    public void testCreate_MissingExpired() {
        final DtoUser    data;
        final Executable executable;
        final Exception  exception;

        data = new DtoUser();
        data.setUsername("User2");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(null);
        data.setLocked(false);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the locked flag is not received")
    public void testCreate_MissingLocked() {
        final DtoUser    data;
        final Executable executable;
        final Exception  exception;

        data = new DtoUser();
        data.setUsername("User2");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(null);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the username is not received")
    public void testCreate_MissingUsername() {
        final DtoUser    data;
        final Executable executable;
        final Exception  exception;

        data = new DtoUser();
        data.setUsername(null);
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.missing", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the role doesn't exist")
    public void testCreate_NotExistingRole() {
        final DtoUser          data;
        final DtoRole          role;
        final Collection<Role> roles;
        final Executable       executable;
        final Exception        exception;

        data = new DtoUser();
        data.setUsername("User");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        role = new DtoRole();
        role.setId(111L);

        roles = new ArrayList<>();
        roles.add(role);

        data.setRoles(roles);

        executable = () -> service.create(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.existing", exception.getMessage());
    }

}
