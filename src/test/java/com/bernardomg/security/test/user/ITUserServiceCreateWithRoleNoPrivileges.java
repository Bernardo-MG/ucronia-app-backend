
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
import com.bernardomg.security.model.DtoRole;
import com.bernardomg.security.model.DtoUser;
import com.bernardomg.security.model.Role;
import com.bernardomg.security.model.User;
import com.bernardomg.security.service.UserService;

@IntegrationTest
@DisplayName("User service - create with role and no privileges")
@Sql({ "/db/queries/security/role/single.sql" })
public class ITUserServiceCreateWithRoleNoPrivileges {

    @Autowired
    private UserService service;

    public ITUserServiceCreateWithRoleNoPrivileges() {
        super();
    }

    @Test
    @DisplayName("Reading the created data returns the role and privileges")
    public void testCreate_ReadBack() {
        final User user;
        final User result;
        final User read;
        final Role roleResult;

        user = getUser();

        result = service.create(user);
        read = service.getOne(result.getId())
            .get();

        Assertions.assertNotNull(read.getId());
        Assertions.assertEquals("User", read.getUsername());
        Assertions.assertEquals("email", read.getEmail());
        Assertions.assertEquals(false, read.getCredentialsExpired());
        Assertions.assertEquals(true, read.getEnabled());
        Assertions.assertEquals(false, read.getExpired());
        Assertions.assertEquals(false, read.getLocked());

        Assertions.assertEquals(1, IterableUtils.size(read.getRoles()));

        roleResult = read.getRoles()
            .iterator()
            .next();

        Assertions.assertNotNull(roleResult.getId());
        Assertions.assertEquals("ADMIN", roleResult.getName());

        Assertions.assertEquals(0, IterableUtils.size(roleResult.getPrivileges()));
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final User user;
        final User result;
        final Role roleResult;

        user = getUser();

        result = service.create(user);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("User", result.getUsername());
        Assertions.assertEquals("email", result.getEmail());
        Assertions.assertEquals(false, result.getCredentialsExpired());
        Assertions.assertEquals(true, result.getEnabled());
        Assertions.assertEquals(false, result.getExpired());
        Assertions.assertEquals(false, result.getLocked());

        Assertions.assertEquals(1, IterableUtils.size(result.getRoles()));

        roleResult = result.getRoles()
            .iterator()
            .next();

        Assertions.assertNotNull(roleResult.getId());
        Assertions.assertEquals("ADMIN", roleResult.getName());

        Assertions.assertEquals(0, IterableUtils.size(roleResult.getPrivileges()));
    }

    private final User getUser() {
        final DtoUser          user;
        final DtoRole          role;
        final Collection<Role> roles;

        role = new DtoRole();
        role.setId(1L);

        roles = new ArrayList<>();
        roles.add(role);

        user = new DtoUser();
        user.setUsername("User");
        user.setEmail("email");
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        user.setRoles(roles);

        return user;
    }

}
