
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
@DisplayName("User service - set roles")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql" })
public class ITUserServiceSetRoles {

    @Autowired
    private UserService service;

    public ITUserServiceSetRoles() {
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

        service.setRoles(1L, roles);
        result = service.getRoles(1L);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        role = result.iterator()
            .next();

        Assertions.assertEquals("ADMIN", role.getName());
    }

    @Test
    @DisplayName("Adds and returns roles")
    public void testAddRoles_ReturnedData() {
        final Iterable<? extends Role> result;
        final Collection<Long>         roles;
        final Role                     role;

        roles = new ArrayList<>();

        roles.add(1L);

        result = service.setRoles(1L, roles);

        Assertions.assertEquals(1L, IterableUtils.size(result));

        role = result.iterator()
            .next();

        Assertions.assertEquals("ADMIN", role.getName());
    }

}
