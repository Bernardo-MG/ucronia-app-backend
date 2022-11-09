
package com.bernardomg.security.registration.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.registration.service.UserRegistrationService;

@IntegrationTest
@DisplayName("Register user service")
public class ITUserRegistrationService {

    @Autowired
    private UserRepository          repository;

    @Autowired
    private UserRegistrationService service;

    public ITUserRegistrationService() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when registering")
    public void testRegisterUser_AddsEntity() {
        service.registerUser("user", "email@somewhere.com");

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Persists the data")
    public void testRegisterUser_PersistedData() {
        final PersistentUser entity;

        service.registerUser("user", "email@somewhere.com");
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("user", entity.getUsername());
        Assertions.assertEquals("", entity.getPassword());
        Assertions.assertEquals("email@somewhere.com", entity.getEmail());
        Assertions.assertFalse(entity.getCredentialsExpired());
        Assertions.assertFalse(entity.getEnabled());
        Assertions.assertFalse(entity.getExpired());
        Assertions.assertFalse(entity.getLocked());
    }

    @Test
    @DisplayName("Returns the registered user")
    public void testRegisterUser_ReturnedData() {
        final User user;

        user = service.registerUser("user", "email@somewhere.com");

        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("user", user.getUsername());
        Assertions.assertEquals("email@somewhere.com", user.getEmail());
        Assertions.assertFalse(user.getCredentialsExpired());
        Assertions.assertFalse(user.getEnabled());
        Assertions.assertFalse(user.getExpired());
        Assertions.assertFalse(user.getLocked());
    }

}
