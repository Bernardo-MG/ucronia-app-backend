
package com.bernardomg.security.test.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

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
        final User user;

        user = getUser();

        service.create(user);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Doesn't create over existing ids")
    @Sql({ "/db/queries/security/role/single.sql" })
    public void testCreate_existing() {
        final User    result;
        final DtoUser user;

        user = getUser();
        user.setId(1L);

        result = service.create(user);

        Assertions.assertNotEquals(1L, result.getId());
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
        final User user;
        final User result;

        user = getUser();

        result = service.create(user);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("User", result.getUsername());
        Assertions.assertEquals("email", result.getEmail());
        Assertions.assertEquals(false, result.getCredentialsExpired());
        Assertions.assertEquals(true, result.getEnabled());
        Assertions.assertEquals(false, result.getExpired());
        Assertions.assertEquals(false, result.getLocked());
    }

    private final DtoUser getUser() {
        final DtoUser user;

        user = new DtoUser();
        user.setUsername("User");
        user.setEmail("email");
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        return user;
    }

}
