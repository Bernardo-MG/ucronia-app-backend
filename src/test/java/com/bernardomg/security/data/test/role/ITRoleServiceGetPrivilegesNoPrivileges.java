
package com.bernardomg.security.data.test.role;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.Privilege;
import com.bernardomg.security.data.service.RoleService;

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
        final Pageable                      pageable;

        pageable = Pageable.unpaged();

        result = service.getPrivileges(1l, pageable);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

}
