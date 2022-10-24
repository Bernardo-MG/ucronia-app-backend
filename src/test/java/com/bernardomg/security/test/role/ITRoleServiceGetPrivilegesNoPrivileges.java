
package com.bernardomg.security.test.role;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.Privilege;
import com.bernardomg.security.service.RoleService;

@IntegrationTest
@DisplayName("Role service - get privileges - no privileges")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql" })
public class ITRoleServiceGetPrivilegesNoPrivileges {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetPrivilegesNoPrivileges() {
        super();
    }

    @Test
    @DisplayName("Returns no privileges for a role")
    public void testGetPrivileges() {
        final Iterable<? extends Privilege> result;

        result = service.getPrivileges(1l);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

}
