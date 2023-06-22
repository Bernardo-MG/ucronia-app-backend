
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.ImmutableUser;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.ValidatedUserCreate;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.security.user.test.assertion.UserAssertions;

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
        final UserCreate user;

        user = getUser();

        service.create(user);

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final UserCreate     user;
        final PersistentUser entity;

        user = getUser();

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
    public void testCreate_PersistedData_Case() {
        final UserCreate     user;
        final PersistentUser entity;

        user = getUser("ADMIN", "EMAIL@SOMEWHERE.COM");

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
    public void testCreate_ReturnedData() {
        final UserCreate user;
        final User       result;

        user = getUser();

        result = service.create(user);

        UserAssertions.isEqualTo(result, ImmutableUser.builder()
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
    public void testCreate_ReturnedData_Case() {
        final UserCreate user;
        final User       result;

        user = getUser("ADMIN", "EMAIL@SOMEWHERE.COM");

        result = service.create(user);

        Assertions.assertThat(result.getUsername())
            .isEqualTo("admin");
        Assertions.assertThat(result.getEmail())
            .isEqualTo("email@somewhere.com");
    }

    private final UserCreate getUser() {
        return ValidatedUserCreate.builder()
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

    private final UserCreate getUser(final String username, final String email) {
        return ValidatedUserCreate.builder()
            .username(username)
            .name("Admin")
            .email(email)
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

}
