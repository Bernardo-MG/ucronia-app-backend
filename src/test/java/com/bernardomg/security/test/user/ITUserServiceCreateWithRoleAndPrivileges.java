
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
import com.bernardomg.security.model.DtoRole;
import com.bernardomg.security.model.DtoUser;
import com.bernardomg.security.model.Privilege;
import com.bernardomg.security.model.Role;
import com.bernardomg.security.model.User;
import com.bernardomg.security.service.UserService;

@IntegrationTest
@DisplayName("User service - create with role and privileges")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/relationship/role_privilege.sql" })
public class ITUserServiceCreateWithRoleAndPrivileges {

    @Autowired
    private UserService service;

    public ITUserServiceCreateWithRoleAndPrivileges() {
        super();
    }

    @Test
    @DisplayName("Reading the created data returns the role and privileges")
    public void testCreate_ReadBack() {
        final User               user;
        final User               result;
        final User               read;
        final Role               roleResult;
        final Collection<String> privileges;

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

        Assertions.assertEquals(4, IterableUtils.size(roleResult.getPrivileges()));

        privileges = StreamSupport.stream(roleResult.getPrivileges()
            .spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(privileges.contains("CREATE_DATA"));
        Assertions.assertTrue(privileges.contains("READ_DATA"));
        Assertions.assertTrue(privileges.contains("UPDATE_DATA"));
        Assertions.assertTrue(privileges.contains("DELETE_DATA"));
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final User               user;
        final User               result;
        final Role               roleResult;
        final Collection<String> privileges;

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

        Assertions.assertEquals(4, IterableUtils.size(roleResult.getPrivileges()));

        privileges = StreamSupport.stream(roleResult.getPrivileges()
            .spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(privileges.contains("CREATE_DATA"));
        Assertions.assertTrue(privileges.contains("READ_DATA"));
        Assertions.assertTrue(privileges.contains("UPDATE_DATA"));
        Assertions.assertTrue(privileges.contains("DELETE_DATA"));
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
