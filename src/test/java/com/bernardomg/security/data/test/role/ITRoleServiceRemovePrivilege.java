
package com.bernardomg.security.data.test.role;

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
@DisplayName("Role service - set privileges - with privileges")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/relationship/role_privilege.sql" })
public class ITRoleServiceRemovePrivilege {

    @Autowired
    private RoleService service;

    public ITRoleServiceRemovePrivilege() {
        super();
    }

    @Test
    @DisplayName("Reading the role privileges after changing privileges returns them")
    public void testAddPrivilege_Change_CallBack() {
        final Iterable<? extends Privilege> result;
        final Collection<String>            privilegeNames;
        final Pageable                      pageable;

        pageable = Pageable.unpaged();

        service.removePrivilege(1l, 1l);
        result = service.getPrivileges(1l, pageable);

        Assertions.assertEquals(3L, IterableUtils.size(result));

        privilegeNames = StreamSupport.stream(result.spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(privilegeNames.contains("READ_DATA"));
        Assertions.assertTrue(privilegeNames.contains("UPDATE_DATA"));
        Assertions.assertTrue(privilegeNames.contains("DELETE_DATA"));
    }

}
