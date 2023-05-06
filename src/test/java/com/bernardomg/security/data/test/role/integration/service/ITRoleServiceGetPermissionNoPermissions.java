
package com.bernardomg.security.data.test.role.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.Action;
import com.bernardomg.security.data.service.RoleService;

@IntegrationTest
@DisplayName("Role service - get action - no action")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql" })
public class ITRoleServiceGetPermissionNoPermissions {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetPermissionNoPermissions() {
        super();
    }

    @Test
    @DisplayName("Returns no action for a role")
    public void testGetActions() {
        final Iterable<? extends Action> result;
        final Pageable                   pageable;

        pageable = Pageable.unpaged();

        result = service.getPermission(1l, pageable);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

}
