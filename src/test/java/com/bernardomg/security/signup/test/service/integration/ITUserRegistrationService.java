
package com.bernardomg.security.signup.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.signup.service.SignUpService;

@IntegrationTest
@DisplayName("UserRegistrationService")
public class ITUserRegistrationService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private SignUpService  service;

    public ITUserRegistrationService() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when registering")
    public void testRegisterUser_AddsEntity() {
        service.signUp("user", "email@somewhere.com");

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("The new user is disabled")
    public void testRegisterUser_Disabled() {
        final PersistentUser entity;

        service.signUp("user", "email@somewhere.com");
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertFalse(entity.getCredentialsExpired());
        Assertions.assertFalse(entity.getEnabled());
        Assertions.assertFalse(entity.getExpired());
        Assertions.assertFalse(entity.getLocked());
    }

    @Test
    @DisplayName("The new user has no password")
    public void testRegisterUser_NoPassword() {
        final PersistentUser entity;

        service.signUp("user", "email@somewhere.com");
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertEquals("", entity.getPassword());
    }

    @Test
    @DisplayName("Persists the data")
    public void testRegisterUser_PersistedData() {
        final PersistentUser entity;

        service.signUp("user", "email@somewhere.com");
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("user", entity.getUsername());
        Assertions.assertEquals("email@somewhere.com", entity.getEmail());
    }

    @Test
    @DisplayName("Returns the registered user")
    public void testRegisterUser_ReturnedData() {
        final User user;

        user = service.signUp("user", "email@somewhere.com");

        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("user", user.getUsername());
        Assertions.assertEquals("email@somewhere.com", user.getEmail());
        Assertions.assertFalse(user.getCredentialsExpired());
        Assertions.assertFalse(user.getEnabled());
        Assertions.assertFalse(user.getExpired());
        Assertions.assertFalse(user.getLocked());
    }

}
