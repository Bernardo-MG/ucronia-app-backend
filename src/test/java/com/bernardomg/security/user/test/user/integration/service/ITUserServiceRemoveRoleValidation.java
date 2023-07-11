
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@IntegrationTest
@DisplayName("User service - remove role - validation")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/role_permission.sql", "/db/queries/security/relationship/user_role.sql" })
class ITUserServiceRemoveRoleValidation {

    @Autowired
    private UserService service;

    public ITUserServiceRemoveRoleValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the role doesn't exist")
    void testAddRoles_NotExistingRole() {
        final ThrowingCallable executable;
        final FieldFailure     failure;

        executable = () -> service.removeRole(1l, -1l);

        failure = FieldFailure.of("role.notExisting", "role", "notExisting", -1l);

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the user doesn't exist")
    void testAddRoles_NotExistingUser() {
        final ThrowingCallable executable;
        final FieldFailure     failure;

        executable = () -> service.removeRole(-1l, 1l);

        failure = FieldFailure.of("id.notExisting", "id", "notExisting", -1l);

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

}
