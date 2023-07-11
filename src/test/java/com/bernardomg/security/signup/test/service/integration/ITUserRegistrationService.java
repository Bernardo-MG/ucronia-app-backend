
package com.bernardomg.security.signup.test.service.integration;

import org.assertj.core.api.Assertions;
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
class ITUserRegistrationService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private SignUpService  service;

    public ITUserRegistrationService() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when registering")
    void testSignUp_AddsEntity() {
        final DtoSignUp signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("email@somewhere.com");

        service.signUp(signUp);

        Assertions.assertThat(repository.count())
            .isEqualTo(1L);
    }

    @Test
    @DisplayName("The new user is disabled")
    void testSignUp_Disabled() {
        final PersistentUser entity;
        final DtoSignUp      signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("email@somewhere.com");

        service.signUp(signUp);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getEnabled())
            .isFalse();

        Assertions.assertThat(entity.getCredentialsExpired())
            .isFalse();
        Assertions.assertThat(entity.getExpired())
            .isFalse();
        Assertions.assertThat(entity.getLocked())
            .isFalse();
    }

    @Test
    @DisplayName("The new user has no password")
    void testSignUp_NoPassword() {
        final PersistentUser entity;
        final DtoSignUp      signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("email@somewhere.com");

        service.signUp(signUp);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getPassword())
            .isEmpty();
    }

    @Test
    @DisplayName("Persists the data after a signup")
    void testSignUp_PersistedData() {
        final PersistentUser entity;
        final DtoSignUp      signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("email@somewhere.com");

        service.signUp(signUp);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getId())
            .isNotNull();
        Assertions.assertThat(entity.getUsername())
            .isEqualTo("user");
        Assertions.assertThat(entity.getEmail())
            .isEqualTo("email@somewhere.com");
    }

    @Test
    @DisplayName("Persists the data, ignoring case")
    void testSignUp_PersistedData_Case() {
        final PersistentUser entity;
        final DtoSignUp      signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("USER");
        signUp.setEmail("EMAIL@SOMEWHERE.COM");

        service.signUp(signUp);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getId())
            .isNotNull();
        Assertions.assertThat(entity.getUsername())
            .isEqualTo("user");
        Assertions.assertThat(entity.getEmail())
            .isEqualTo("email@somewhere.com");
    }

    @Test
    @DisplayName("Returns the status after a signup")
    void testSignUp_Status() {
        final SignUpStatus status;
        final DtoSignUp    signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("user");
        signUp.setEmail("email@somewhere.com");

        status = service.signUp(signUp);

        Assertions.assertThat(status.getUsername())
            .isEqualTo("user");
        Assertions.assertThat(status.getEmail())
            .isEqualTo("email@somewhere.com");
        Assertions.assertThat(status.getSuccessful())
            .isTrue();
    }

    @Test
    @DisplayName("Returns the status after a signup, ignoring case")
    void testSignUp_Status_Case() {
        final SignUpStatus status;
        final DtoSignUp    signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("USER");
        signUp.setEmail("EMAIL@SOMEWHERE.COM");

        status = service.signUp(signUp);

        Assertions.assertThat(status.getUsername())
            .isEqualTo("user");
        Assertions.assertThat(status.getEmail())
            .isEqualTo("email@somewhere.com");
        Assertions.assertThat(status.getSuccessful())
            .isTrue();
    }

}
