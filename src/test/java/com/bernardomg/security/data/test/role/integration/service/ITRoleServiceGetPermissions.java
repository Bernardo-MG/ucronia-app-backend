
package com.bernardomg.security.data.test.role.integration.service;

import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.Permission;
import com.bernardomg.security.data.service.RoleService;

@IntegrationTest
@DisplayName("Role service - get permissions")
public class ITRoleServiceGetPermissions {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetPermissions() {
        super();
    }

    @Test
    @DisplayName("Returns no action for a not existing role")
    public void testGetActions_NotExisting() {
        final Iterable<? extends Permission> result;
        final Pageable                       pageable;

        pageable = Pageable.unpaged();

        result = service.getPermission(-1l, pageable);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns the permissions for a role")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/relationship/role_permission.sql" })
    public void testGetPermissions() {
        final Iterable<? extends Permission> result;
        final Pageable                       pageable;
        Boolean                              found;

        pageable = Pageable.unpaged();

        result = service.getPermission(1l, pageable);

        Assertions.assertEquals(4L, IterableUtils.size(result));

        // DATA:CREATE
        found = StreamSupport.stream(result.spliterator(), false)
            .filter(p -> "DATA".equals(p.getResource()) && "CREATE".equals(p.getAction()))
            .findAny()
            .isPresent();
        Assertions.assertTrue(found);

        // DATA:READ
        found = StreamSupport.stream(result.spliterator(), false)
            .filter(p -> "DATA".equals(p.getResource()) && "READ".equals(p.getAction()))
            .findAny()
            .isPresent();
        Assertions.assertTrue(found);

        // DATA:UPDATE
        found = StreamSupport.stream(result.spliterator(), false)
            .filter(p -> "DATA".equals(p.getResource()) && "UPDATE".equals(p.getAction()))
            .findAny()
            .isPresent();
        Assertions.assertTrue(found);

        // DATA:DELETE
        found = StreamSupport.stream(result.spliterator(), false)
            .filter(p -> "DATA".equals(p.getResource()) && "DELETE".equals(p.getAction()))
            .findAny()
            .isPresent();
        Assertions.assertTrue(found);
    }

    @Test
    @DisplayName("When there are no permissions nothing is returned")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql" })
    public void testGetPermissions_NoPermissions() {
        final Iterable<? extends Permission> result;
        final Pageable                       pageable;

        pageable = Pageable.unpaged();

        result = service.getPermission(1l, pageable);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

}
