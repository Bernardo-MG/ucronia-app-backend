
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
import com.bernardomg.security.data.model.Privilege;
import com.bernardomg.security.data.service.RoleService;

@IntegrationTest
@DisplayName("Role service - get privileges")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/relationship/role_privilege.sql" })
public class ITRoleServiceGetPrivileges {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetPrivileges() {
        super();
    }

    @Test
    @DisplayName("Returns the privileges for a role")
    public void testGetPrivileges() {
        final Iterable<? extends Privilege> result;
        final Collection<String>            privileges;
        final Pageable                      pageable;

        pageable = Pageable.unpaged();

        result = service.getPrivileges(1l, pageable);

        Assertions.assertEquals(4L, IterableUtils.size(result));

        privileges = StreamSupport.stream(result.spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(privileges.contains("CREATE_DATA"));
        Assertions.assertTrue(privileges.contains("READ_DATA"));
        Assertions.assertTrue(privileges.contains("UPDATE_DATA"));
        Assertions.assertTrue(privileges.contains("DELETE_DATA"));
    }

    @Test
    @DisplayName("Returns no privileges for a not existing role")
    public void testGetPrivileges_NotExisting() {
        final Iterable<? extends Privilege> result;
        final Pageable                      pageable;

        pageable = Pageable.unpaged();

        result = service.getPrivileges(-1l, pageable);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

}
