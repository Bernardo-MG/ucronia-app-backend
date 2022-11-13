
package com.bernardomg.security.data.test.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.DtoUser;
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
        final User    result;
        final DtoUser user;

        user = getUser();
        user.setId(1L);

        result = service.create(user);

        Assertions.assertNotEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Adds an entity when creating with an existing id")
    public void testCreate_AddsEntity() {
        final DtoUser user;

        user = getUser();
        user.setId(1L);

        service.create(user);

        Assertions.assertEquals(2L, repository.count());
    }

    private final DtoUser getUser() {
        final DtoUser user;

        user = new DtoUser();
        user.setUsername("User");
        user.setEmail("email2@somewhere.com");
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        return user;
    }

}
