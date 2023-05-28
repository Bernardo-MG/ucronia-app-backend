
package com.bernardomg.security.signup.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.signup.model.DtoSignUp;
import com.bernardomg.security.signup.model.SignUpStatus;
import com.bernardomg.security.signup.service.SignUpService;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

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
    public void testSignUp_AddsEntity() {
        final DtoSignUp signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("email@somewhere.com");

        service.signUp(signUp);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("The new user is disabled")
    public void testSignUp_Disabled() {
        final PersistentUser entity;
        final DtoSignUp      signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("email@somewhere.com");

        service.signUp(signUp);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertFalse(entity.getEnabled());

        Assertions.assertFalse(entity.getCredentialsExpired());
        Assertions.assertFalse(entity.getExpired());
        Assertions.assertFalse(entity.getLocked());
    }

    @Test
    @DisplayName("The new user has no password")
    public void testSignUp_NoPassword() {
        final PersistentUser entity;
        final DtoSignUp      signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("email@somewhere.com");

        service.signUp(signUp);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertEquals("", entity.getPassword());
    }

    @Test
    @DisplayName("Persists the data after a signup")
    public void testSignUp_PersistedData() {
        final PersistentUser entity;
        final DtoSignUp      signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("email@somewhere.com");

        service.signUp(signUp);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("user", entity.getUsername());
        Assertions.assertEquals("email@somewhere.com", entity.getEmail());
    }

    @Test
    @DisplayName("Persists the data, ignoring case")
    public void testSignUp_PersistedData_Case() {
        final PersistentUser entity;
        final DtoSignUp      signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("USER");
        signUp.setEmail("EMAIL@SOMEWHERE.COM");

        service.signUp(signUp);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("user", entity.getUsername());
        Assertions.assertEquals("email@somewhere.com", entity.getEmail());
    }

    @Test
    @DisplayName("Returns the status after a signup")
    public void testSignUp_Status() {
        final SignUpStatus status;
        final DtoSignUp    signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("email@somewhere.com");

        status = service.signUp(signUp);

        Assertions.assertEquals("user", status.getUsername());
        Assertions.assertEquals("email@somewhere.com", status.getEmail());
        Assertions.assertTrue(status.getSuccessful());
    }

    @Test
    @DisplayName("Returns the status after a signup, ignoring case")
    public void testSignUp_Status_Case() {
        final SignUpStatus status;
        final DtoSignUp    signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("USER");
        signUp.setEmail("EMAIL@SOMEWHERE.COM");

        status = service.signUp(signUp);

        Assertions.assertEquals("user", status.getUsername());
        Assertions.assertEquals("email@somewhere.com", status.getEmail());
        Assertions.assertTrue(status.getSuccessful());
    }

}
