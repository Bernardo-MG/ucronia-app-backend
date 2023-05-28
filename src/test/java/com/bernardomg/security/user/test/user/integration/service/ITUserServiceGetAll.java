
package com.bernardomg.security.user.test.user.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.request.DtoUserQueryRequest;
import com.bernardomg.security.user.model.request.UserQueryRequest;
import com.bernardomg.security.user.service.UserService;

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
        final Iterable<User>   result;
        final UserQueryRequest sample;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        sample = DtoUserQueryRequest.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterable<User>   data;
        final UserQueryRequest sample;
        final Pageable         pageable;
        final User             user;

        pageable = Pageable.unpaged();

        sample = DtoUserQueryRequest.builder()
            .build();

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