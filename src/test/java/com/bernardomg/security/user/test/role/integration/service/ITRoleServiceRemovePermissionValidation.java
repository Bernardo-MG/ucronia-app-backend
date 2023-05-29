
package com.bernardomg.security.user.test.role.integration.service;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.service.RoleService;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

@IntegrationTest
@DisplayName("Role service - set action validation")
public class ITRoleServiceRemovePermissionValidation {

    @Autowired
    private RoleService service;

    public ITRoleServiceRemovePermissionValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the action doesn't exist")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/role/single.sql" })
    public void testAddPermission_NotExistingAction() {
        final Collection<Long>      action;
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        action = new ArrayList<>();
        action.add(-1L);

        executable = () -> service.removePermission(1l, 1l, 1l);

        exception = Assertions.assertThrows(FieldFailureException.class, executable);

        Assertions.assertEquals(1, exception.getFailures()
            .size());

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertEquals("notExisting", failure.getCode());
        Assertions.assertEquals("action", failure.getField());
        Assertions.assertEquals("action.notExisting", failure.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the resource doesn't exist")
    @Sql({ "/db/queries/security/action/crud.sql", "/db/queries/security/role/single.sql" })
    public void testAddPermission_NotExistingResource() {
        final Collection<Long>      action;
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        action = new ArrayList<>();
        action.add(-1L);

        executable = () -> service.removePermission(1l, 1l, 1l);

        exception = Assertions.assertThrows(FieldFailureException.class, executable);

        Assertions.assertEquals(1, exception.getFailures()
            .size());

        failure = exception.getFailures()
            .iterator()
            .next();

        Assertions.assertEquals("notExisting", failure.getCode());
        Assertions.assertEquals("resource", failure.getField());
        Assertions.assertEquals("resource.notExisting", failure.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the role doesn't exist")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql" })
    public void testAddPermission_NotExistingRole() {
        final Collection<Long>      action;
        final Executable            executable;
        final FieldFailureException exception;
        final FieldFailure          failure;

        action = new ArrayList<>();
        action.add(1L);

        executable = () -> service.removePermission(1l, 1l, 1l);

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
