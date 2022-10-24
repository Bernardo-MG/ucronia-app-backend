
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
@DisplayName("Role service - add privileges - with privileges")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/relationship/role_privilege.sql" })
public class ITRoleServiceAddPrivilegesWithPrivileges {

    @Autowired
    private RoleService service;

    public ITRoleServiceAddPrivilegesWithPrivileges() {
        super();
    }

    @Test
    @DisplayName("Reading the role privileges after changing privileges returns them")
    public void testAddPrivileges_Change_CallBack() {
        final Collection<Long>              privileges;
        final Iterable<? extends Privilege> result;
        final Collection<String>            privilegeNames;

        privileges = new ArrayList<>();
        privileges.add(1L);

        service.addPrivileges(1l, privileges);
        result = service.getPrivileges(1l);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        privilegeNames = StreamSupport.stream(result.spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(privilegeNames.contains("CREATE_DATA"));
    }

    @Test
    @DisplayName("Adding a different privilege and returns the changed privileges")
    public void testAddPrivileges_Change_ReturnedData() {
        final Collection<Long>              privileges;
        final Iterable<? extends Privilege> result;
        final Collection<String>            privilegeNames;

        privileges = new ArrayList<>();
        privileges.add(1L);

        result = service.addPrivileges(1l, privileges);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        privilegeNames = StreamSupport.stream(result.spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(privilegeNames.contains("CREATE_DATA"));
    }

    @Test
    @DisplayName("Reading the role privileges after adding empty privileges returns none")
    public void testAddPrivileges_Empty_CallBack() {
        final Collection<Long>              privileges;
        final Iterable<? extends Privilege> result;

        privileges = new ArrayList<>();

        service.addPrivileges(1l, privileges);
        result = service.getPrivileges(1l);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Adding empty privileges returns not privileges")
    public void testAddPrivileges_Empty_ReturnedData() {
        final Collection<Long>              privileges;
        final Iterable<? extends Privilege> result;

        privileges = new ArrayList<>();

        result = service.addPrivileges(1l, privileges);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

}
