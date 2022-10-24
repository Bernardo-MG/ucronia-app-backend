
package com.bernardomg.security.test.role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
@DisplayName("Role service - set privileges")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql" })
public class ITRoleServiceSetPrivileges {

    @Autowired
    private RoleService service;

    public ITRoleServiceSetPrivileges() {
        super();
    }

    @Test
    @DisplayName("Reading the role privileges after adding privileges returns them")
    public void testSetPrivileges_CallBack() {
        final Collection<Long>              privileges;
        final Iterable<? extends Privilege> result;
        final Collection<String>            privilegeNames;

        privileges = new ArrayList<>();
        privileges.add(1L);

        service.setPrivileges(1l, privileges);
        result = service.getPrivileges(1l);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        privilegeNames = StreamSupport.stream(result.spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(privilegeNames.contains("CREATE_DATA"));
    }

    @Test
    @DisplayName("Adds and returns privileges")
    public void testSetPrivileges_ReturnedData() {
        final Collection<Long>              privileges;
        final Iterable<? extends Privilege> result;
        final Collection<String>            privilegeNames;

        privileges = new ArrayList<>();
        privileges.add(1L);

        result = service.setPrivileges(1l, privileges);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        privilegeNames = StreamSupport.stream(result.spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(privilegeNames.contains("CREATE_DATA"));
    }

}
