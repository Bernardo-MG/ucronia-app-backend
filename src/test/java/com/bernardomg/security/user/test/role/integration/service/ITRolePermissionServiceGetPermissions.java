
package com.bernardomg.security.user.test.role.integration.service;

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
import com.bernardomg.security.permission.model.Permission;
import com.bernardomg.security.user.service.RolePermissionService;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Role service - get permissions")
class ITRolePermissionServiceGetPermissions {

    @Autowired
    private RolePermissionService service;

    public ITRolePermissionServiceGetPermissions() {
        super();
    }

    @Test
    @DisplayName("Returns no permission for a not existing role")
    void testGetActions_NotExisting() {
        final Iterable<Permission> result;
        final Pageable             pageable;

        pageable = Pageable.unpaged();

        result = service.getPermissions(-1l, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isZero();
    }

    @Test
    @DisplayName("Returns the permissions for a role")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/relationship/role_permission.sql" })
    void testGetPermissions() {
        final Iterable<Permission> result;
        final Pageable             pageable;
        Boolean                    found;

        pageable = Pageable.unpaged();

        result = service.getPermissions(1l, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(4);

        // DATA:CREATE
        found = StreamSupport.stream(result.spliterator(), false)
            .filter(p -> "DATA".equals(p.getResource()) && "CREATE".equals(p.getAction()))
            .findAny()
            .isPresent();
        Assertions.assertThat(found)
            .isTrue();
        // DATA:READ
        found = StreamSupport.stream(result.spliterator(), false)
            .filter(p -> "DATA".equals(p.getResource()) && "READ".equals(p.getAction()))
            .findAny()
            .isPresent();
        Assertions.assertThat(found)
            .isTrue();

        // DATA:UPDATE
        found = StreamSupport.stream(result.spliterator(), false)
            .filter(p -> "DATA".equals(p.getResource()) && "UPDATE".equals(p.getAction()))
            .findAny()
            .isPresent();
        Assertions.assertThat(found)
            .isTrue();

        // DATA:DELETE
        found = StreamSupport.stream(result.spliterator(), false)
            .filter(p -> "DATA".equals(p.getResource()) && "DELETE".equals(p.getAction()))
            .findAny()
            .isPresent();
        Assertions.assertThat(found)
            .isTrue();
    }

    @Test
    @DisplayName("When the permission is not granted nothing is returned")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql" })
    void testGetPermissions_NoPermissions() {
        final Iterable<Permission> result;
        final Pageable             pageable;

        pageable = Pageable.unpaged();

        result = service.getPermissions(1l, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isZero();
    }

    @Test
    @DisplayName("When there no permissions are granted nothing is returned")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql",
            "/db/queries/security/relationship/role_permission_not_granted.sql" })
    void testGetPermissions_NotGranted() {
        final Iterable<Permission> result;
        final Pageable             pageable;

        pageable = Pageable.unpaged();

        result = service.getPermissions(1l, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isZero();
    }

}
