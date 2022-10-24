
package com.bernardomg.security.test.user;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.User;
import com.bernardomg.security.service.UserService;

@IntegrationTest
@DisplayName("User service - get one - expired")
@Sql({ "/db/queries/security/user/expired.sql" })
public class ITUserServiceGetOneExpired {

    @Autowired
    private UserService service;

    public ITUserServiceGetOneExpired() {
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
        final User result;

        result = service.getOne(1l)
            .get();

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("admin", result.getUsername());
        Assertions.assertEquals("email", result.getEmail());
        Assertions.assertFalse(result.getCredentialsExpired());
        Assertions.assertTrue(result.getEnabled());
        Assertions.assertTrue(result.getExpired());
        Assertions.assertFalse(result.getLocked());
    }

}
