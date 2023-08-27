
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.user.model.DtoUser;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.security.user.test.util.assertion.UserAssertions;
import com.bernardomg.security.user.test.util.model.UsersCreate;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("User service - register new user")
class ITUserServiceRegisterNewUser {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService    service;

    public ITUserServiceRegisterNewUser() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when creating")
    void testRegisterNewUser_AddsEntity() {
        final UserCreate user;

        user = UsersCreate.valid();

        service.registerNewUser(user);

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Persists the data, ignoring case")
    void testRegisterNewUser_Case_PersistedData() {
        final UserCreate     user;
        final PersistentUser entity;

        user = UsersCreate.valid("ADMIN", "EMAIL@SOMEWHERE.COM");

        service.registerNewUser(user);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getUsername())
            .isEqualTo("admin");
        Assertions.assertThat(entity.getEmail())
            .isEqualTo("email@somewhere.com");
    }

    @Test
    @DisplayName("Returns the created data, ignoring case")
    void testRegisterNewUser_Case_ReturnedData() {
        final UserCreate user;
        final User       result;

        user = UsersCreate.valid("ADMIN", "EMAIL@SOMEWHERE.COM");

        result = service.registerNewUser(user);

        Assertions.assertThat(result.getUsername())
            .isEqualTo("admin");
        Assertions.assertThat(result.getEmail())
            .isEqualTo("email@somewhere.com");
    }

    @Test
    @DisplayName("With a user having padding whitespaces in username, name and email, these whitespaces are removed")
    void testRegisterNewUser_Padded_PersistedData() {
        final UserCreate     user;
        final PersistentUser entity;

        user = UsersCreate.paddedWithWhitespaces();

        service.registerNewUser(user);
        entity = repository.findAll()
            .iterator()
            .next();

        UserAssertions.isEqualTo(entity, PersistentUser.builder()
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .password("")
            .credentialsExpired(false)
            .enabled(false)
            .expired(false)
            .locked(false)
            .build());
    }

    @Test
    @DisplayName("Persists the data")
    void testRegisterNewUser_PersistedData() {
        final UserCreate     user;
        final PersistentUser entity;

        user = UsersCreate.valid();

        service.registerNewUser(user);
        entity = repository.findAll()
            .iterator()
            .next();

        UserAssertions.isEqualTo(entity, PersistentUser.builder()
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .password("")
            .credentialsExpired(false)
            .enabled(false)
            .expired(false)
            .locked(false)
            .build());
    }

    @Test
    @DisplayName("Returns the created data")
    void testRegisterNewUser_ReturnedData() {
        final UserCreate user;
        final User       result;

        user = UsersCreate.valid();

        result = service.registerNewUser(user);

        UserAssertions.isEqualTo(result, DtoUser.builder()
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .credentialsExpired(false)
            .enabled(false)
            .expired(false)
            .locked(false)
            .build());
    }

}
