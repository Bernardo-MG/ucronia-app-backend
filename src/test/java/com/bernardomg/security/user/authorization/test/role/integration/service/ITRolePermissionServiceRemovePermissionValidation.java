
package com.bernardomg.security.user.authorization.test.role.integration.service;

import java.util.ArrayList;
import java.util.Collection;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.permission.service.RolePermissionService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;
import com.bernardomg.validation.failure.FieldFailure;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Role service - set action validation")
class ITRolePermissionServiceRemovePermissionValidation {

    @Autowired
    private RolePermissionService service;

    public ITRolePermissionServiceRemovePermissionValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the action doesn't exist")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/role/single.sql" })
    void testAddPermission_NotExistingAction() {
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
    void testAddPermission_NotExistingResource() {
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
    void testAddPermission_NotExistingRole() {
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
