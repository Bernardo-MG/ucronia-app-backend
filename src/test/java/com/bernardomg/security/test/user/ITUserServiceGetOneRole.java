
package com.bernardomg.security.test.user;

import java.util.Collection;
import java.util.Optional;
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
import com.bernardomg.security.model.Role;
import com.bernardomg.security.model.User;
import com.bernardomg.security.service.UserService;

@IntegrationTest
@DisplayName("User service - get one - with role and privileges")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
        "/db/queries/security/relationship/user_role.sql" })
public class ITUserServiceGetOneRole {

    @Autowired
    private UserService service;

    public ITUserServiceGetOneRole() {
        super();
    }

    @Test
    @DisplayName("Returns a single entity by id")
    public void testGetOne_Existing() {
        final Optional<? extends User> result;

        result = service.getOne(1l);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Returns the correct data when reading a single entity")
    public void testGetOne_Existing_Data() {
        final User               result;
        final Role               role;
        final Collection<String> privileges;

        result = service.getOne(1l)
            .get();

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("ADMIN", result.getUsername());
        Assertions.assertEquals("ADMIN", result.getEmail());
        Assertions.assertFalse(result.getCredentialsExpired());
        Assertions.assertTrue(result.getEnabled());
        Assertions.assertFalse(result.getExpired());
        Assertions.assertFalse(result.getLocked());
        Assertions.assertEquals(1, IterableUtils.size(result.getRoles()));

        role = result.getRoles()
            .iterator()
            .next();

        Assertions.assertNotNull(role.getId());
        Assertions.assertEquals("ADMIN", role.getName());
        Assertions.assertEquals(4, IterableUtils.size(role.getPrivileges()));

        privileges = StreamSupport.stream(role.getPrivileges()
            .spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(privileges.contains("CREATE_DATA"));
        Assertions.assertTrue(privileges.contains("READ_DATA"));
        Assertions.assertTrue(privileges.contains("UPDATE_DATA"));
        Assertions.assertTrue(privileges.contains("DELETE_DATA"));
    }

    @Test
    @DisplayName("When reading a single entity with an invalid id, no entity is returned")
    public void testGetOne_NotExisting() {
        final Optional<? extends User> result;

        result = service.getOne(-1L);

        Assertions.assertFalse(result.isPresent());
    }

}
