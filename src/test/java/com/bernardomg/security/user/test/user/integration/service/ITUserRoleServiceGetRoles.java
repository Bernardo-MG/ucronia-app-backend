
package com.bernardomg.security.user.test.user.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.service.UserRoleService;

@IntegrationTest
@DisplayName("User service - get roles")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/role_permission.sql", "/db/queries/security/relationship/user_role.sql" })
class ITUserRoleServiceGetRoles {

    @Autowired
    private UserRoleService service;

    public ITUserRoleServiceGetRoles() {
        super();
    }

    @Test
    @DisplayName("Returns the roles for a user")
    void testGetRoles() {
        final Iterable<Role> result;
        final Role           role;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        result = service.getRoles(1L, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);

        role = result.iterator()
            .next();

        Assertions.assertThat(role.getName())
            .isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Returns no roles for a not existing user")
    void testGetRoles_NotExisting() {
        final Iterable<Role> result;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        result = service.getRoles(-1L, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isZero();
    }

}
