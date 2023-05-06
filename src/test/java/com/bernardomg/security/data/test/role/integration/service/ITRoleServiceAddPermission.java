
package com.bernardomg.security.data.test.role.integration.service;

import java.util.Collection;
import java.util.stream.Collectors;
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
@DisplayName("Role service - add permission")
public class ITRoleServiceAddPermission {

    @Autowired
    private RoleService service;

    public ITRoleServiceAddPermission() {
        super();
    }

    @Test
    @DisplayName("Adds a permission")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql" })
    public void testAddPermission() {
        final Iterable<? extends Permission> result;
        final Collection<String>             actionNames;
        final Pageable                       pageable;

        pageable = Pageable.unpaged();

        service.addPermission(1l, 1l, 1l);
        result = service.getPermission(1l, pageable);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        actionNames = StreamSupport.stream(result.spliterator(), false)
            .map(p -> p.getResource() + ":" + p.getAction())
            .collect(Collectors.toList());

        Assertions.assertTrue(actionNames.contains("DATA:CREATE"));
    }

    @Test
    @DisplayName("When adding an existing permission no permission is added")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/relationship/role_permission.sql" })
    public void testAddPermission_Existing() {
        final Iterable<? extends Permission> result;
        final Pageable                       pageable;
        Boolean                              found;

        pageable = Pageable.unpaged();

        service.addPermission(1l, 1l, 1l);
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

}
