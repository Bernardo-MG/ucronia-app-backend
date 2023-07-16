
package com.bernardomg.security.user.test.role.integration.service;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.RolePermission;
import com.bernardomg.security.user.persistence.model.PersistentRolePermission;
import com.bernardomg.security.user.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.service.RoleService;
import com.bernardomg.security.user.test.util.assertion.RolePermissionAssertions;

@IntegrationTest
@DisplayName("Role service - remove permission")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/relationship/role_permission.sql" })
class ITRoleServiceRemovePermission {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RoleService              service;

    public ITRoleServiceRemovePermission() {
        super();
    }

    @Test
    @DisplayName("Returns the removed data")
    void testAddRole_ReturnedData() {
        final RolePermission result;

        result = service.removePermission(1l, 1l, 1l);

        Assertions.assertThat(result.getRole())
            .isEqualTo(1);
        Assertions.assertThat(result.getResource())
            .isEqualTo(1);
        Assertions.assertThat(result.getAction())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Can remove a permission")
    void testRemovePermission() {
        final Iterable<PersistentRolePermission> result;
        final Iterator<PersistentRolePermission> itr;
        PersistentRolePermission                 found;

        service.removePermission(1l, 1l, 1l);
        result = rolePermissionRepository.findAll();

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(4);

        itr = result.iterator();

        found = itr.next();

        RolePermissionAssertions.isEqualTo(found, PersistentRolePermission.builder()
            .actionId(1L)
            .resourceId(1L)
            .roleId(1L)
            .granted(false)
            .build());

        found = itr.next();

        RolePermissionAssertions.isEqualTo(found, PersistentRolePermission.builder()
            .actionId(2L)
            .resourceId(1L)
            .roleId(1L)
            .granted(true)
            .build());

        found = itr.next();

        RolePermissionAssertions.isEqualTo(found, PersistentRolePermission.builder()
            .actionId(3L)
            .resourceId(1L)
            .roleId(1L)
            .granted(true)
            .build());

        found = itr.next();

        RolePermissionAssertions.isEqualTo(found, PersistentRolePermission.builder()
            .actionId(4L)
            .resourceId(1L)
            .roleId(1L)
            .granted(true)
            .build());
    }

}
