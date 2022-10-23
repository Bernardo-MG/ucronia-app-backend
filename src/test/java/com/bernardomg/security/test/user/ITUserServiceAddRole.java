
package com.bernardomg.security.test.user;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.Role;
import com.bernardomg.security.service.UserService;

@IntegrationTest
@DisplayName("User service - add roles")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql" })
public class ITUserServiceAddRole {

    @Autowired
    private UserService service;

    public ITUserServiceAddRole() {
        super();
    }

    @Test
    @DisplayName("Adds and returns roles")
    public void testAddRoles_CallBack() {
        final Iterable<? extends Role> result;
        final Collection<Long>         roles;
        final Role                     role;

        roles = new ArrayList<>();

        roles.add(1L);

        service.addRoles(1L, roles);
        result = service.getRoles(1L);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        role = result.iterator()
            .next();

        Assertions.assertEquals("ADMIN", role.getName());
    }

    @Test
    @DisplayName("Reading the role roles after adding a not existing role return none")
    public void testAddRoles_NotExistingRole_CallBack() {
        final Iterable<? extends Role> result;
        final Collection<Long>         roles;

        roles = new ArrayList<>();

        roles.add(-1L);

        service.addRoles(1L, roles);
        result = service.getRoles(1L);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns no roles when adding a not existing role")
    public void testAddRoles_NotExistingRole_ReturnedData() {
        final Iterable<? extends Role> result;
        final Collection<Long>         roles;

        roles = new ArrayList<>();

        roles.add(-1L);

        result = service.addRoles(1L, roles);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns no roles when adding to a not existing user")
    public void testAddRoles_NotExistingUser() {
        final Iterable<? extends Role> result;
        final Collection<Long>         roles;

        roles = new ArrayList<>();

        roles.add(1L);

        result = service.addRoles(-1L, roles);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Adds and returns roles")
    public void testAddRoles_ReturnedData() {
        final Iterable<? extends Role> result;
        final Collection<Long>         roles;
        final Role                     role;

        roles = new ArrayList<>();

        roles.add(1L);

        result = service.addRoles(1L, roles);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        role = result.iterator()
            .next();

        Assertions.assertEquals("ADMIN", role.getName());
    }

}
