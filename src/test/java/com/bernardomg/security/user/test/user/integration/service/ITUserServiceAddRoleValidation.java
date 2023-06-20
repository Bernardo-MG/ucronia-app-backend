
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
@DisplayName("User service - add role - validation")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/role_permission.sql" })
public class ITUserServiceAddRoleValidation {

    @Autowired
    private UserService service;

    public ITUserServiceAddRoleValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the role doesn't exist")
    public void testAddRoles_NotExistingRole() {
        final ThrowingCallable executable;
        final FieldFailure     failure;

        executable = () -> service.addRole(1l, -1l);

        failure = FieldFailure.of("role.notExisting", "role", "notExisting", -1L);

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the user doesn't exist")
    public void testAddRoles_NotExistingUser() {
        final ThrowingCallable executable;
        final FieldFailure     failure;

        executable = () -> service.addRole(-1l, 1l);

        failure = FieldFailure.of("id.notExisting", "id", "notExisting", -1L);

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

}
