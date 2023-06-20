
package com.bernardomg.security.user.test.role.integration.service;

import java.util.ArrayList;
import java.util.Collection;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.service.RoleService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

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
        final Collection<Long> action;
        final ThrowingCallable executable;
        final FieldFailure     failure;

        action = new ArrayList<>();
        action.add(-1L);

        executable = () -> service.removePermission(1l, 1l, 1l);

        failure = FieldFailure.of("action.notExisting", "action", "notExisting", 1L);

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the resource doesn't exist")
    @Sql({ "/db/queries/security/action/crud.sql", "/db/queries/security/role/single.sql" })
    public void testAddPermission_NotExistingResource() {
        final Collection<Long> action;
        final ThrowingCallable executable;
        final FieldFailure     failure;

        action = new ArrayList<>();
        action.add(-1L);

        executable = () -> service.removePermission(1l, 1l, 1l);

        failure = FieldFailure.of("resource.notExisting", "resource", "notExisting", 1L);

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the role doesn't exist")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql" })
    public void testAddPermission_NotExistingRole() {
        final Collection<Long> action;
        final ThrowingCallable executable;
        final FieldFailure     failure;

        action = new ArrayList<>();
        action.add(1L);

        executable = () -> service.removePermission(1l, 1l, 1l);

        failure = FieldFailure.of("id.notExisting", "id", "notExisting", 1L);

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

}
