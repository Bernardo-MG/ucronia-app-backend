
package com.bernardomg.security.user.test.role.integration.repository;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.persistence.model.PersistentRoleGrantedPermission;
import com.bernardomg.security.user.persistence.repository.RoleGrantedPermissionRepository;

@IntegrationTest
@DisplayName("Role repository - find all permissions")
public class ITRoleRoleGrantedPermissionRepositoryByRoleId {

    @Autowired
    private RoleGrantedPermissionRepository repository;

    public ITRoleRoleGrantedPermissionRepositoryByRoleId() {
        super();
    }

    @Test
    @DisplayName("Finds all the granted permissions for the role")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testAllByRoleId() {
        final Iterable<PersistentRoleGrantedPermission> read;
        final Iterator<PersistentRoleGrantedPermission> itr;
        final Pageable                                  pageable;
        PersistentRoleGrantedPermission                 found;

        pageable = Pageable.unpaged();

        read = repository.findAllByRoleId(1L, pageable);

        Assertions.assertEquals(4L, IterableUtils.size(read));

        itr = read.iterator();

        found = itr.next();

        Assertions.assertEquals(1L, found.getActionId());
        Assertions.assertEquals("CREATE", found.getAction());
        Assertions.assertEquals(1L, found.getResourceId());
        Assertions.assertEquals("DATA", found.getResource());
        Assertions.assertEquals(1L, found.getRoleId());
        Assertions.assertEquals("ADMIN", found.getRole());

        found = itr.next();

        Assertions.assertEquals(2L, found.getActionId());
        Assertions.assertEquals("READ", found.getAction());
        Assertions.assertEquals(1L, found.getResourceId());
        Assertions.assertEquals("DATA", found.getResource());
        Assertions.assertEquals(1L, found.getRoleId());
        Assertions.assertEquals("ADMIN", found.getRole());

        found = itr.next();

        Assertions.assertEquals(3L, found.getActionId());
        Assertions.assertEquals("UPDATE", found.getAction());
        Assertions.assertEquals(1L, found.getResourceId());
        Assertions.assertEquals("DATA", found.getResource());
        Assertions.assertEquals(1L, found.getRoleId());
        Assertions.assertEquals("ADMIN", found.getRole());

        found = itr.next();

        Assertions.assertEquals(4L, found.getActionId());
        Assertions.assertEquals("DELETE", found.getAction());
        Assertions.assertEquals(1L, found.getResourceId());
        Assertions.assertEquals("DATA", found.getResource());
        Assertions.assertEquals(1L, found.getRoleId());
        Assertions.assertEquals("ADMIN", found.getRole());
    }

    @Test
    @DisplayName("Applies pagination")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testAllByRoleId_FirstPage_Count() {
        final Page<PersistentRoleGrantedPermission> read;
        final Pageable                              pageable;

        pageable = PageRequest.of(0, 1);

        read = repository.findAllByRoleId(1L, pageable);

        Assertions.assertEquals(1, IterableUtils.size(read));
    }

    @Test
    @DisplayName("The returned permission contains the ids")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testAllByRoleId_Ids() {
        final Iterable<PersistentRoleGrantedPermission> read;
        final Pageable                                  pageable;
        PersistentRoleGrantedPermission                 found;

        pageable = Pageable.unpaged();

        read = repository.findAllByRoleId(1L, pageable);

        found = read.iterator()
            .next();

        Assertions.assertEquals(1L, found.getActionId());
        Assertions.assertEquals("CREATE", found.getAction());
        Assertions.assertEquals(1L, found.getResourceId());
        Assertions.assertEquals("DATA", found.getResource());
        Assertions.assertEquals(1L, found.getRoleId());
        Assertions.assertEquals("ADMIN", found.getRole());
    }

    @Test
    @DisplayName("Finds no permissions when the role has none")
    @Sql({ "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testAllByRoleId_NoPermissions_Count() {
        final Page<PersistentRoleGrantedPermission> read;
        final Pageable                              pageable;

        pageable = Pageable.unpaged();

        read = repository.findAllByRoleId(1L, pageable);

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

    @Test
    @DisplayName("Finds no permissions for a not existing role")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testAllByRoleId_NotExisting_Count() {
        final Page<PersistentRoleGrantedPermission> read;
        final Pageable                              pageable;

        pageable = Pageable.unpaged();

        read = repository.findAllByRoleId(-1L, pageable);

        Assertions.assertEquals(0, IterableUtils.size(read));
    }

    @Test
    @DisplayName("When there are no granted permissions nothing is returned")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission_not_granted.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public void testAllByRoleId_NotGranted() {
        final Iterable<PersistentRoleGrantedPermission> read;
        final Pageable                                  pageable;

        pageable = Pageable.unpaged();

        read = repository.findAllByRoleId(1L, pageable);

        Assertions.assertEquals(0L, IterableUtils.size(read));
    }

}
