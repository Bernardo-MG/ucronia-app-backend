
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
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

@IntegrationTest
@DisplayName("Role service - set privileges validation")
public class ITRoleServiceRemovePrivilegeValidation {

    @Autowired
    private RoleService service;

    public ITRoleServiceRemovePrivilegeValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the privilege doesn't exist")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql" })
    public void testAddPrivilege_NotExistingPrivilege() {
        final Collection<Long>      privileges;
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        privileges = new ArrayList<>();
        privileges.add(-1L);

        executable = () -> service.removePrivilege(1l, -1l);

        exception = Assertions.assertThrows(FieldFailureException.class, executable);

        Assertions.assertEquals(1, exception.getFailures()
            .size());

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertEquals("notExisting", failure.getCode());
        Assertions.assertEquals("privilege", failure.getField());
        Assertions.assertEquals("privilege.notExisting", failure.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the role doesn't exist")
    @Sql({ "/db/queries/security/privilege/multiple.sql" })
    public void testAddPrivilege_NotExistingRole() {
        final Collection<Long>      privileges;
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        privileges = new ArrayList<>();
        privileges.add(1L);

        executable = () -> service.removePrivilege(1l, 1l);

        exception = Assertions.assertThrows(FieldFailureException.class, executable);

        Assertions.assertEquals(1, exception.getFailures()
            .size());

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertEquals("notExisting", failure.getCode());
        Assertions.assertEquals("id", failure.getField());
        Assertions.assertEquals("id.notExisting", failure.getMessage());
    }

}
