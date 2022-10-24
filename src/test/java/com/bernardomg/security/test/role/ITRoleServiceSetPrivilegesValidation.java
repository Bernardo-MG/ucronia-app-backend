
package com.bernardomg.security.test.role;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.service.RoleService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("Role service - set privileges validation")
public class ITRoleServiceSetPrivilegesValidation {

    @Autowired
    private RoleService service;

    public ITRoleServiceSetPrivilegesValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the privilege doesn't exist")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql" })
    public void testSetPrivileges_NotExistingPrivilege() {
        final Collection<Long> privileges;
        final Executable       executable;
        final Exception        exception;

        privileges = new ArrayList<>();
        privileges.add(-1L);

        executable = () -> service.setPrivileges(1l, privileges);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.privilege.id.notExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the role doesn't exist")
    @Sql({ "/db/queries/security/privilege/multiple.sql" })
    public void testSetPrivileges_NotExistingRole() {
        final Collection<Long> privileges;
        final Executable       executable;
        final Exception        exception;

        privileges = new ArrayList<>();
        privileges.add(1L);

        executable = () -> service.setPrivileges(1l, privileges);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.id.notExisting", exception.getMessage());
    }

}
