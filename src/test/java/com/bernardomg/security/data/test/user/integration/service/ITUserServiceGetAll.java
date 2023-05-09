
package com.bernardomg.security.data.test.user.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.service.UserService;

@IntegrationTest
@DisplayName("User service - get all")
@Sql({ "/db/queries/security/user/single.sql" })
public class ITUserServiceGetAll {

    @Autowired
    private UserService service;

    public ITUserServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<? extends User> result;
        final DtoUser                  sample;
        final Pageable                 pageable;

        pageable = Pageable.unpaged();

        sample = new DtoUser();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterable<? extends User> data;
        final DtoUser                  sample;
        final Pageable                 pageable;
        final User                     user;

        pageable = Pageable.unpaged();

        sample = new DtoUser();

        data = service.getAll(sample, pageable);

        user = data.iterator()
            .next();

        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("admin", user.getUsername());
        Assertions.assertEquals("email@somewhere.com", user.getEmail());
        Assertions.assertFalse(user.getCredentialsExpired());
        Assertions.assertTrue(user.getEnabled());
        Assertions.assertFalse(user.getExpired());
        Assertions.assertFalse(user.getLocked());
    }

}
