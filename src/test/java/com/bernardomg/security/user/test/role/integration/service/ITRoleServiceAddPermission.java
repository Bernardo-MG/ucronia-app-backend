
package com.bernardomg.security.user.test.role.integration.service;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.persistence.model.PersistentRolePermission;
import com.bernardomg.security.user.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.service.RoleService;
import com.bernardomg.security.user.test.util.assertion.RolePermissionAssertions;

@IntegrationTest
@DisplayName("Role service - add permission")
public class ITRoleServiceAddPermission {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RoleService              service;

    public ITRoleServiceAddPermission() {
        super();
    }

    @Test
    @DisplayName("Adds a permission")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql" })
    public void testAddPermission() {
        final Iterable<PersistentRolePermission> result;
        final PersistentRolePermission           found;

        service.addPermission(1l, 1l, 1l);
        result = rolePermissionRepository.findAll();

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);

        found = result.iterator()
            .next();

        RolePermissionAssertions.isEqualTo(found, PersistentRolePermission.builder()
            .actionId(1L)
            .resourceId(1L)
            .roleId(1L)
            .granted(true)
            .build());
    }

    @Test
    @DisplayName("When adding an existing permission no permission is added")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/relationship/role_permission.sql" })
    public void testAddPermission_Existing() {
        final Iterable<PersistentRolePermission> result;
        final Iterator<PersistentRolePermission> itr;
        PersistentRolePermission                 found;

        service.addPermission(1l, 1l, 1l);
        result = rolePermissionRepository.findAll();

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(4);

        itr = result.iterator();

        found = itr.next();

        RolePermissionAssertions.isEqualTo(found, PersistentRolePermission.builder()
            .actionId(1L)
            .resourceId(1L)
            .roleId(1L)
            .granted(true)
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
