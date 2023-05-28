
package com.bernardomg.security.data.test.user.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.ImmutableUser;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.data.service.UserService;

@IntegrationTest
@DisplayName("User service - create - existing")
@Sql({ "/db/queries/security/user/single.sql" })
public class ITUserServiceCreateExisting {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService    service;

    public ITUserServiceCreateExisting() {
        super();
    }

    @Test
    @DisplayName("Doesn't create over existing ids")
    public void testCreate() {
        final User          result;
        final ImmutableUser user;

        user = getUser();

        result = service.create(user);

        Assertions.assertNotEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Adds an entity when creating with an existing id")
    public void testCreate_AddsEntity() {
        final ImmutableUser user;

        user = getUser();

        service.create(user);

        Assertions.assertEquals(2L, repository.count());
    }

    private final ImmutableUser getUser() {
        return ImmutableUser.builder()
            .id(1L)
            .username("user")
            .name("User")
            .email("email2@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

}
