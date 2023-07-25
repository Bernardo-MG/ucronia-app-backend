
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.DtoUser;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.security.user.test.util.assertion.UserAssertions;
import com.bernardomg.security.user.test.util.model.UsersCreate;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("User service - create")
class ITUserServiceCreate {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService    service;

    public ITUserServiceCreate() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when creating")
    void testCreate_AddsEntity() {
        final UserCreate user;

        user = UsersCreate.enabled();

        service.create(user);

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Persists the data")
    void testCreate_PersistedData() {
        final UserCreate     user;
        final PersistentUser entity;

        user = UsersCreate.enabled();

        service.create(user);
        entity = repository.findAll()
            .iterator()
            .next();

        UserAssertions.isEqualTo(entity, PersistentUser.builder()
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .password("")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build());
    }

    @Test
    @DisplayName("Persists the data, ignoring case")
    void testCreate_PersistedData_Case() {
        final UserCreate     user;
        final PersistentUser entity;

        user = UsersCreate.enabled("ADMIN", "EMAIL@SOMEWHERE.COM");

        service.create(user);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getUsername())
            .isEqualTo("admin");
        Assertions.assertThat(entity.getEmail())
            .isEqualTo("email@somewhere.com");
    }

    @Test
    @DisplayName("Returns the created data")
    void testCreate_ReturnedData() {
        final UserCreate user;
        final User       result;

        user = UsersCreate.enabled();

        result = service.create(user);

        UserAssertions.isEqualTo(result, DtoUser.builder()
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build());
    }

    @Test
    @DisplayName("Returns the created data, ignoring case")
    void testCreate_ReturnedData_Case() {
        final UserCreate user;
        final User       result;

        user = UsersCreate.enabled("ADMIN", "EMAIL@SOMEWHERE.COM");

        result = service.create(user);

        Assertions.assertThat(result.getUsername())
            .isEqualTo("admin");
        Assertions.assertThat(result.getEmail())
            .isEqualTo("email@somewhere.com");
    }

}
