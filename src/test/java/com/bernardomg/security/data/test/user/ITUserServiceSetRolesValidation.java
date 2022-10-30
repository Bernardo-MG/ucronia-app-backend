
package com.bernardomg.security.data.test.user;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.service.UserService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("User service - set roles validation")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql" })
public class ITUserServiceSetRolesValidation {

    @Autowired
    private UserService service;

    public ITUserServiceSetRolesValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the role doesn't exist")
    public void testAddRoles_NotExistingRole() {
        final Collection<Long> roles;
        final Executable       executable;
        final Exception        exception;

        roles = new ArrayList<>();
        roles.add(-1L);

        executable = () -> service.setRoles(1l, roles);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.role.notExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the user doesn't exist")
    public void testAddRoles_NotExistingUser() {
        final Collection<Long> roles;
        final Executable       executable;
        final Exception        exception;

        roles = new ArrayList<>();
        roles.add(1L);

        executable = () -> service.setRoles(-1l, roles);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.id.notExisting", exception.getMessage());
    }

}
