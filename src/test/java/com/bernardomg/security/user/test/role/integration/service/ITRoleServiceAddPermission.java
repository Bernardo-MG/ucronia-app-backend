
package com.bernardomg.security.user.test.role.integration.service;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.permission.persistence.repository.RoleGrantedPermissionRepository;
import com.bernardomg.security.user.model.DtoPermission;
import com.bernardomg.security.user.model.Permission;
import com.bernardomg.security.user.model.RolePermission;
import com.bernardomg.security.user.persistence.model.PersistentRolePermission;
import com.bernardomg.security.user.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.service.RoleService;
import com.bernardomg.security.user.test.util.assertion.RolePermissionAssertions;

@IntegrationTest
@DisplayName("Role service - add permission")
class ITRoleServiceAddPermission {

    @Autowired
    private RoleGrantedPermissionRepository roleGrantedPermissionRepository;

    @Autowired
    private RolePermissionRepository        rolePermissionRepository;

    @Autowired
    private RoleService                     service;

    public ITRoleServiceAddPermission() {
        super();
    }

    @Test
    @DisplayName("Adds a permission")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql" })
    void testAddPermission_AddsEntity() {
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
    @DisplayName("Reading the permissions after adding a permission returns the new permission")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql" })
    void testAddPermission_CallBack() {
        final Iterable<Permission> result;
        final Permission           found;
        final Pageable             pageable;

        pageable = Pageable.unpaged();

        service.addPermission(1l, 1l, 1l);
        roleGrantedPermissionRepository.flush();
        result = service.getPermissions(1l, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);

        found = result.iterator()
            .next();

        RolePermissionAssertions.isEqualTo(found, DtoPermission.builder()
            .actionId(1L)
            .action("CREATE")
            .resourceId(1L)
            .resource("DATA")
            .build());
    }

    @Test
    @DisplayName("When adding an existing permission no permission is added")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/relationship/role_permission.sql" })
    void testAddPermission_Existing() {
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

    @Test
    @DisplayName("Returns the created data")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql" })
    void testAddRole_ReturnedData() {
        final RolePermission result;

        result = service.addPermission(1l, 1l, 1l);

        Assertions.assertThat(result.getRoleId())
            .isEqualTo(1);
        Assertions.assertThat(result.getResourceId())
            .isEqualTo(1);
        Assertions.assertThat(result.getActionId())
            .isEqualTo(1);
    }

}
