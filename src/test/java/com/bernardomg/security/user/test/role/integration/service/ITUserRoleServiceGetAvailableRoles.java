
package com.bernardomg.security.user.test.role.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.service.UserRoleService;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("User service - get available roles")
class ITUserRoleServiceGetAvailableRoles {

    @Autowired
    private UserRoleService service;

    public ITUserRoleServiceGetAvailableRoles() {
        super();
    }

    @Test
    @DisplayName("Returns no available roles when a user has all the roles")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/permission/crud.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/role/alternative.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testGetRoles() {
        final Iterable<Role> result;
        final Role           role;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        result = service.getAvailableRoles(1L, pageable);

        Assertions.assertThat(result)
            .hasSize(1);

        role = result.iterator()
            .next();

        Assertions.assertThat(role.getName())
            .isEqualTo("ALT");
    }

    @Test
    @DisplayName("Returns no available roles when a user has all the roles")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/permission/crud.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testGetRoles_AllAssigned() {
        final Iterable<Role> result;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        result = service.getAvailableRoles(1L, pageable);

        Assertions.assertThat(result)
            .isEmpty();
    }

    @Test
    @DisplayName("Returns no available roles for a not existing user")
    void testGetRoles_NotExisting() {
        final Iterable<Role> result;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        result = service.getAvailableRoles(-1L, pageable);

        Assertions.assertThat(result)
            .isEmpty();
    }

}
