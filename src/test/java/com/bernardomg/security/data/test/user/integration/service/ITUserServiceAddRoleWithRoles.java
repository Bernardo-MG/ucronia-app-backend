
package com.bernardomg.security.data.test.user.integration.service;

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
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.service.UserService;

@IntegrationTest
@DisplayName("User service - add role - with role")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/role/alternative.sql",
        "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_permission.sql",
        "/db/queries/security/relationship/user_role.sql" })
public class ITUserServiceAddRoleWithRoles {

    @Autowired
    private UserService service;

    public ITUserServiceAddRoleWithRoles() {
        super();
    }

    @Test
    @DisplayName("Adding a role which the user already has adds nothing")
    public void testAddRoles_AddExisting_CallBack() {
        final Iterable<Role>     result;
        final Collection<String> roleNames;
        final Pageable           pageable;

        pageable = Pageable.unpaged();

        service.addRole(1l, 2l);
        result = service.getRoles(1l, pageable);

        Assertions.assertEquals(2L, IterableUtils.size(result));

        roleNames = StreamSupport.stream(result.spliterator(), false)
            .map(Role::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(roleNames.contains("ADMIN"));
    }

    @Test
    @DisplayName("Reading the roles after adding a new role returns them")
    public void testAddRoles_AddNew_CallBack() {
        final Iterable<Role>     result;
        final Collection<String> roleNames;
        final Pageable           pageable;

        pageable = Pageable.unpaged();

        service.addRole(1l, 2l);
        result = service.getRoles(1l, pageable);

        Assertions.assertEquals(2L, IterableUtils.size(result));

        roleNames = StreamSupport.stream(result.spliterator(), false)
            .map(Role::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(roleNames.contains("ADMIN"));
        Assertions.assertTrue(roleNames.contains("ALT"));
    }

}
