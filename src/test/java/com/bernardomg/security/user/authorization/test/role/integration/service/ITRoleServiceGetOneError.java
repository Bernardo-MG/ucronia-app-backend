
package com.bernardomg.security.user.authorization.test.role.integration.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.exception.InvalidIdException;
import com.bernardomg.security.user.authorization.service.RoleService;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Role service - get one - errors")
class ITRoleServiceGetOneError {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetOneError() {
        super();
    }

    @Test
    @DisplayName("With a not existing entity, an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable execution;

        execution = () -> service.getOne(1L);

        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(InvalidIdException.class);
    }

}
