
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
@DisplayName("Role service - add privileges validation")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql" })
public class ITRoleServiceAddPrivilegesValidation {

    @Autowired
    private RoleService service;

    public ITRoleServiceAddPrivilegesValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the privilege doesn't exist")
    public void testDelete_NotExistingPrivilege() {
        final Collection<Long> privileges;
        final Executable       executable;
        final Exception        exception;

        privileges = new ArrayList<>();
        privileges.add(-1L);

        executable = () -> service.addPrivileges(1l, privileges);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.privilege.notExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the role doesn't exist")
    public void testDelete_NotExistingRole() {
        final Collection<Long> privileges;
        final Executable       executable;
        final Exception        exception;

        privileges = new ArrayList<>();
        privileges.add(1L);

        executable = () -> service.addPrivileges(-1l, privileges);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.notExisting", exception.getMessage());
    }

}
