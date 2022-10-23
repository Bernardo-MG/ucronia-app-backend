
package com.bernardomg.security.test.role;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.DtoPrivilege;
import com.bernardomg.security.model.DtoRole;
import com.bernardomg.security.model.Privilege;
import com.bernardomg.security.model.Role;
import com.bernardomg.security.service.RoleService;

@IntegrationTest
@DisplayName("Role service - create with privileges")
@Sql({ "/db/queries/security/privilege/multiple.sql" })
@Sql({ "/db/queries/security/privilege/multiple.sql" })
public class ITRoleServiceCreateWithPrivileges {

    @Autowired
    private RoleService service;

    public ITRoleServiceCreateWithPrivileges() {
        super();
    }

    @Test
    @DisplayName("Reading the created data returns the privileges")
    public void testCreate_ReadBack() {
        final Role      role;
        final Role      result;
        final Role      read;
        final Privilege privilegeResult;

        role = getRole();

        result = service.create(role);
        read = service.getOne(result.getId())
            .get();

        Assertions.assertNotNull(read.getId());
        Assertions.assertEquals("Role", read.getName());
        Assertions.assertEquals(1, IterableUtils.size(read.getPrivileges()));

        privilegeResult = read.getPrivileges()
            .iterator()
            .next();

        Assertions.assertNotNull(privilegeResult.getId());
        Assertions.assertEquals("CREATE_DATA", privilegeResult.getName());
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final Role      role;
        final Role      result;
        final Privilege privilegeResult;

        role = getRole();

        result = service.create(role);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Role", result.getName());
        Assertions.assertEquals(1, IterableUtils.size(result.getPrivileges()));

        privilegeResult = result.getPrivileges()
            .iterator()
            .next();

        Assertions.assertNotNull(privilegeResult.getId());
        Assertions.assertEquals("CREATE_DATA", privilegeResult.getName());
    }

    private final Role getRole() {
        final DtoRole               role;
        final DtoPrivilege          privilege;
        final Collection<Privilege> privileges;

        role = new DtoRole();
        role.setName("Role");

        privileges = new ArrayList<>();

        privilege = new DtoPrivilege();
        privilege.setId(1L);
        privileges.add(privilege);

        role.setPrivileges(privileges);

        return role;
    }

}
