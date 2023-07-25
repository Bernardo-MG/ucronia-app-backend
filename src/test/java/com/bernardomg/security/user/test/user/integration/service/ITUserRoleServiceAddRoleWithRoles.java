
package com.bernardomg.security.user.test.user.integration.service;

import java.util.Collection;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.service.UserRoleService;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("User service - add role - with role")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/role/alternative.sql",
        "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission.sql",
        "/db/queries/security/relationship/user_role.sql" })
class ITUserRoleServiceAddRoleWithRoles {

    @Autowired
    private UserRoleService service;

    public ITUserRoleServiceAddRoleWithRoles() {
        super();
    }

    @Test
    @DisplayName("Adding a role which the user already has adds nothing")
    void testAddRoles_AddExisting_CallBack() {
        final Iterable<Role>     result;
        final Collection<String> roleNames;
        final Pageable           pageable;

        pageable = Pageable.unpaged();

        service.addRole(1l, 2l);
        result = service.getRoles(1l, pageable);

        // FIXME: Should be a single role
        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(2);

        roleNames = StreamSupport.stream(result.spliterator(), false)
            .map(Role::getName)
            .toList();

        Assertions.assertThat(roleNames)
            .contains("ADMIN");
    }

    @Test
    @DisplayName("Reading the roles after adding a new role returns them")
    void testAddRoles_AddNew_CallBack() {
        final Iterable<Role>     result;
        final Collection<String> roleNames;
        final Pageable           pageable;

        pageable = Pageable.unpaged();

        service.addRole(1l, 2l);
        result = service.getRoles(1l, pageable);

        // FIXME: Should be a single role
        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(2);

        roleNames = StreamSupport.stream(result.spliterator(), false)
            .map(Role::getName)
            .toList();

        Assertions.assertThat(roleNames)
            .contains("ADMIN")
            .contains("ALT");
    }

}
