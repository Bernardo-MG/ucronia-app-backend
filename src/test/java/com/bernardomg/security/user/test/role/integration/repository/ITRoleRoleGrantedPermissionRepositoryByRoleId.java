
package com.bernardomg.security.user.test.role.integration.repository;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
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
import com.bernardomg.security.user.test.util.assertion.RoleGrantedPermissionAssertions;

@IntegrationTest
@DisplayName("Role repository - find all permissions")
class ITRoleRoleGrantedPermissionRepositoryByRoleId {

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
    void testAllByRoleId() {
        final Iterable<PersistentRoleGrantedPermission> read;
        final Iterator<PersistentRoleGrantedPermission> itr;
        final Pageable                                  pageable;
        PersistentRoleGrantedPermission                 found;

        pageable = Pageable.unpaged();

        read = repository.findAllByRoleId(1L, pageable);

        Assertions.assertThat(IterableUtils.size(read))
            .isEqualTo(4);

        itr = read.iterator();

        found = itr.next();

        RoleGrantedPermissionAssertions.isEqualTo(found, PersistentRoleGrantedPermission.builder()
            .actionId(1L)
            .action("CREATE")
            .resourceId(1L)
            .resource("DATA")
            .roleId(1L)
            .role("ADMIN")
            .build());

        found = itr.next();

        RoleGrantedPermissionAssertions.isEqualTo(found, PersistentRoleGrantedPermission.builder()
            .actionId(2L)
            .action("READ")
            .resourceId(1L)
            .resource("DATA")
            .roleId(1L)
            .role("ADMIN")
            .build());

        found = itr.next();

        RoleGrantedPermissionAssertions.isEqualTo(found, PersistentRoleGrantedPermission.builder()
            .actionId(3L)
            .action("UPDATE")
            .resourceId(1L)
            .resource("DATA")
            .roleId(1L)
            .role("ADMIN")
            .build());

        found = itr.next();

        RoleGrantedPermissionAssertions.isEqualTo(found, PersistentRoleGrantedPermission.builder()
            .actionId(4L)
            .action("DELETE")
            .resourceId(1L)
            .resource("DATA")
            .roleId(1L)
            .role("ADMIN")
            .build());
    }

    @Test
    @DisplayName("Applies pagination")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testAllByRoleId_FirstPage_Count() {
        final Page<PersistentRoleGrantedPermission> read;
        final Pageable                              pageable;

        pageable = PageRequest.of(0, 1);

        read = repository.findAllByRoleId(1L, pageable);

        Assertions.assertThat(IterableUtils.size(read))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("The returned permission contains the ids")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testAllByRoleId_Ids() {
        final Iterable<PersistentRoleGrantedPermission> read;
        final Pageable                                  pageable;
        PersistentRoleGrantedPermission                 found;

        pageable = Pageable.unpaged();

        read = repository.findAllByRoleId(1L, pageable);

        found = read.iterator()
            .next();

        RoleGrantedPermissionAssertions.isEqualTo(found, PersistentRoleGrantedPermission.builder()
            .actionId(1L)
            .action("CREATE")
            .resourceId(1L)
            .resource("DATA")
            .roleId(1L)
            .role("ADMIN")
            .build());
    }

    @Test
    @DisplayName("Finds no permissions when the role has none")
    @Sql({ "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testAllByRoleId_NoPermissions_Count() {
        final Page<PersistentRoleGrantedPermission> read;
        final Pageable                              pageable;

        pageable = Pageable.unpaged();

        read = repository.findAllByRoleId(1L, pageable);

        Assertions.assertThat(IterableUtils.size(read))
            .isZero();
    }

    @Test
    @DisplayName("Finds no permissions for a not existing role")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testAllByRoleId_NotExisting_Count() {
        final Page<PersistentRoleGrantedPermission> read;
        final Pageable                              pageable;

        pageable = Pageable.unpaged();

        read = repository.findAllByRoleId(-1L, pageable);

        Assertions.assertThat(IterableUtils.size(read))
            .isZero();
    }

    @Test
    @DisplayName("When there are no granted permissions nothing is returned")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission_not_granted.sql",
            "/db/queries/security/relationship/user_role.sql" })
    void testAllByRoleId_NotGranted() {
        final Iterable<PersistentRoleGrantedPermission> read;
        final Pageable                                  pageable;

        pageable = Pageable.unpaged();

        read = repository.findAllByRoleId(1L, pageable);

        Assertions.assertThat(IterableUtils.size(read))
            .isZero();
    }

}
