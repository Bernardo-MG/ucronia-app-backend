
package com.bernardomg.security.test.user;

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
import com.bernardomg.security.model.Role;
import com.bernardomg.security.service.UserService;

@IntegrationTest
@DisplayName("User service - set roles - with role")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql" })
public class ITUserServiceSetRolesWithRoles {

    @Autowired
    private UserService service;

    public ITUserServiceSetRolesWithRoles() {
        super();
    }

    @Test
    @DisplayName("Reading the role privileges after changing privileges returns them")
    public void testAddRoles_Change_CallBack() {
        final Collection<Long>   roles;
        final Iterable<Role>     result;
        final Collection<String> roleNames;

        roles = new ArrayList<>();
        roles.add(1L);

        service.setRoles(1l, roles);
        result = service.getRoles(1l);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        roleNames = StreamSupport.stream(result.spliterator(), false)
            .map(Role::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(roleNames.contains("ADMIN"));
    }

    @Test
    @DisplayName("Adding a different privilege and returns the changed privileges")
    public void testAddRoles_Change_ReturnedData() {
        final Collection<Long>         roles;
        final Iterable<? extends Role> result;
        final Collection<String>       roleNames;

        roles = new ArrayList<>();
        roles.add(1L);

        result = service.setRoles(1l, roles);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        roleNames = StreamSupport.stream(result.spliterator(), false)
            .map(Role::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(roleNames.contains("ADMIN"));
    }

    @Test
    @DisplayName("Reading the user roles after adding empty privileges returns none")
    public void testAddRoles_Empty_CallBack() {
        final Collection<Long> roles;
        final Iterable<Role>   result;

        roles = new ArrayList<>();

        service.setRoles(1l, roles);
        result = service.getRoles(1l);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Adding empty roles returns not roles")
    public void testAddRoles_Empty_ReturnedData() {
        final Collection<Long>         roles;
        final Iterable<? extends Role> result;

        roles = new ArrayList<>();

        result = service.setRoles(1l, roles);

        Assertions.assertEquals(0L, IterableUtils.size(result));
    }

}
