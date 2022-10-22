
package com.bernardomg.security.test.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.DtoUser;
import com.bernardomg.security.model.User;
import com.bernardomg.security.persistence.model.PersistentUser;
import com.bernardomg.security.persistence.repository.UserRepository;
import com.bernardomg.security.service.UserService;

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
        final DtoUser data;

        data = new DtoUser();
        data.setUsername("User");

        service.create(data);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Persists the data with a day which is not the first of the month")
    public void testCreate_AnotherDay_PersistedData() {
        final DtoUser        data;
        final PersistentUser entity;

        data = new DtoUser();
        data.setUsername("User");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        service.create(data);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("User", entity.getUsername());
        Assertions.assertEquals("email", entity.getEmail());
        Assertions.assertEquals(false, entity.getCredentialsExpired());
        Assertions.assertEquals(true, entity.getEnabled());
        Assertions.assertEquals(false, entity.getExpired());
        Assertions.assertEquals(false, entity.getLocked());
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final DtoUser        data;
        final PersistentUser entity;

        data = new DtoUser();
        data.setUsername("User");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        service.create(data);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("User", entity.getUsername());
        Assertions.assertEquals("email", entity.getEmail());
        Assertions.assertEquals(false, entity.getCredentialsExpired());
        Assertions.assertEquals(true, entity.getEnabled());
        Assertions.assertEquals(false, entity.getExpired());
        Assertions.assertEquals(false, entity.getLocked());
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final User    result;
        final DtoUser data;

        data = new DtoUser();
        data.setUsername("User");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        result = service.create(data);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("User", result.getUsername());
        Assertions.assertEquals("email", result.getEmail());
        Assertions.assertEquals(false, result.getCredentialsExpired());
        Assertions.assertEquals(true, result.getEnabled());
        Assertions.assertEquals(false, result.getExpired());
        Assertions.assertEquals(false, result.getLocked());
    }

}
