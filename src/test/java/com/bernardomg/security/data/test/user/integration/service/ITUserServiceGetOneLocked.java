
package com.bernardomg.security.data.test.user.integration.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.service.UserService;

@IntegrationTest
@DisplayName("User service - get one - locked")
@Sql({ "/db/queries/security/user/locked.sql" })
public class ITUserServiceGetOneLocked {

    @Autowired
    private UserService service;

    public ITUserServiceGetOneLocked() {
        super();
    }

    @Test
    @DisplayName("Returns a single entity by id")
    public void testGetOne_Existing() {
        final Optional<User> result;

        result = service.getOne(1l);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Returns the correct data when reading a single entity")
    public void testGetOne_Existing_Data() {
        final User result;

        result = service.getOne(1l)
            .get();

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("admin", result.getUsername());
        Assertions.assertEquals("email@somewhere.com", result.getEmail());
        Assertions.assertFalse(result.getCredentialsExpired());
        Assertions.assertTrue(result.getEnabled());
        Assertions.assertFalse(result.getExpired());
        Assertions.assertTrue(result.getLocked());
    }

}
