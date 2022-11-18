
package com.bernardomg.security.data.test.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.data.service.UserService;

@IntegrationTest
@DisplayName("User service - create")
public class ITUserServiceCreate {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService    service;

    public ITUserServiceCreate() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when creating")
    public void testCreate_AddsEntity() {
        final User user;

        user = getUser();

        service.create(user);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final User           user;
        final PersistentUser entity;

        user = getUser();

        service.create(user);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("admin", entity.getUsername());
        Assertions.assertEquals("Admin", entity.getName());
        Assertions.assertEquals("email@somewhere.com", entity.getEmail());
        Assertions.assertEquals("", entity.getPassword());
        Assertions.assertEquals(false, entity.getCredentialsExpired());
        Assertions.assertEquals(true, entity.getEnabled());
        Assertions.assertEquals(false, entity.getExpired());
        Assertions.assertEquals(false, entity.getLocked());
    }

    @Test
    @DisplayName("Persists the data, ignoring case")
    public void testCreate_PersistedData_Case() {
        final DtoUser        user;
        final PersistentUser entity;

        user = getUser();
        user.setUsername("ADMIN");
        user.setEmail("EMAIL@SOMEWHERE.COM");

        service.create(user);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertEquals("admin", entity.getUsername());
        Assertions.assertEquals("email@somewhere.com", entity.getEmail());
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final User user;
        final User result;

        user = getUser();

        result = service.create(user);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("admin", result.getUsername());
        Assertions.assertEquals("Admin", result.getName());
        Assertions.assertEquals("email@somewhere.com", result.getEmail());
        Assertions.assertEquals(false, result.getCredentialsExpired());
        Assertions.assertEquals(true, result.getEnabled());
        Assertions.assertEquals(false, result.getExpired());
        Assertions.assertEquals(false, result.getLocked());
    }

    @Test
    @DisplayName("Returns the created data, ignoring case")
    public void testCreate_ReturnedData_Case() {
        final DtoUser user;
        final User    result;

        user = getUser();
        user.setUsername("ADMIN");
        user.setEmail("EMAIL@SOMEWHERE.COM");

        result = service.create(user);

        Assertions.assertEquals("admin", result.getUsername());
        Assertions.assertEquals("email@somewhere.com", result.getEmail());
    }

    private final DtoUser getUser() {
        final DtoUser user;

        user = new DtoUser();
        user.setUsername("admin");
        user.setName("Admin");
        user.setEmail("email@somewhere.com");
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        return user;
    }

}
