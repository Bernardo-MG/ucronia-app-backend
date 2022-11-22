
package com.bernardomg.security.data.test.role;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.service.RoleService;
import com.bernardomg.validation.failure.exception.FailureException;

@IntegrationTest
@DisplayName("Role service - set privileges validation")
public class ITRoleServiceAddPrivilegeValidation {

    @Autowired
    private RoleService service;

    public ITRoleServiceAddPrivilegeValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the privilege doesn't exist")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql" })
    public void testAddPrivilege_NotExistingPrivilege() {
        final Collection<Long> privileges;
        final Executable       executable;
        final Exception        exception;

        privileges = new ArrayList<>();
        privileges.add(-1L);

        executable = () -> service.addPrivilege(1l, -1l);

        exception = Assertions.assertThrows(FailureException.class, executable);

        Assertions.assertEquals("error.privilege.id.notExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the role doesn't exist")
    @Sql({ "/db/queries/security/privilege/multiple.sql" })
    public void testAddPrivilege_NotExistingRole() {
        final Collection<Long> privileges;
        final Executable       executable;
        final Exception        exception;

        privileges = new ArrayList<>();
        privileges.add(1L);

        executable = () -> service.addPrivilege(1l, 1l);

        exception = Assertions.assertThrows(FailureException.class, executable);

        Assertions.assertEquals("error.id.notExisting", exception.getMessage());
    }

}
