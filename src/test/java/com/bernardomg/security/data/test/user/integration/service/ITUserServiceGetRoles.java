
package com.bernardomg.security.data.test.user.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.service.UserService;

@IntegrationTest
@DisplayName("User service - get roles")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/role_permission.sql", "/db/queries/security/relationship/user_role.sql" })
public class ITUserServiceGetRoles {

    @Autowired
    private UserService service;

    public ITUserServiceGetRoles() {
        super();
    }

    @Test
    @DisplayName("Returns the roles for a user")
    public void testGetRoles() {
        final Iterable<Role> result;
        final Role           role;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        result = service.getRoles(1L, pageable);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        role = result.iterator()
            .next();

        Assertions.assertEquals("ADMIN", role.getName());
    }

    @Test
    @DisplayName("Returns no roles for a not existing user")
    public void testGetRoles_NotExisting() {
        final Iterable<Role> result;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        result = service.getRoles(-1L, pageable);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

}
