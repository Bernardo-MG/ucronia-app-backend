
package com.bernardomg.security.user.test.role.integration.service;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.persistence.model.PersistentRolePermission;
import com.bernardomg.security.user.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.service.RoleService;

@IntegrationTest
@DisplayName("Role service - remove permission")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/relationship/role_permission.sql" })
public class ITRoleServiceRemovePermission {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RoleService              service;

    public ITRoleServiceRemovePermission() {
        super();
    }

    @Test
    @DisplayName("Can remove a permission")
    public void testRemovePermission() {
        final Iterable<PersistentRolePermission> result;
        final Iterator<PersistentRolePermission> itr;
        PersistentRolePermission                 found;

        service.removePermission(1l, 1l, 1l);
        result = rolePermissionRepository.findAll();

        Assertions.assertEquals(4L, IterableUtils.size(result));

        itr = result.iterator();

        found = itr.next();

        Assertions.assertEquals(1L, found.getActionId());
        Assertions.assertEquals(1L, found.getResourceId());
        Assertions.assertEquals(1L, found.getRoleId());
        Assertions.assertFalse(found.getGranted());

        found = itr.next();

        Assertions.assertEquals(2L, found.getActionId());
        Assertions.assertEquals(1L, found.getResourceId());
        Assertions.assertEquals(1L, found.getRoleId());
        Assertions.assertTrue(found.getGranted());

        found = itr.next();

        Assertions.assertEquals(3L, found.getActionId());
        Assertions.assertEquals(1L, found.getResourceId());
        Assertions.assertEquals(1L, found.getRoleId());
        Assertions.assertTrue(found.getGranted());

        found = itr.next();

        Assertions.assertEquals(4L, found.getActionId());
        Assertions.assertEquals(1L, found.getResourceId());
        Assertions.assertEquals(1L, found.getRoleId());
        Assertions.assertTrue(found.getGranted());
    }

}
