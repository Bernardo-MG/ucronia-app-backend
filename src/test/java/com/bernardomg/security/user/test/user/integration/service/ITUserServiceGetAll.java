
package com.bernardomg.security.user.test.user.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.ImmutableUser;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.request.UserQuery;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.security.user.test.util.assertion.UserAssertions;
import com.bernardomg.security.user.test.util.model.UsersQuery;

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
        final Iterable<User> result;
        final UserQuery      sample;
        final Pageable       pageable;

        pageable = Pageable.unpaged();

        sample = UsersQuery.empty();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterable<User> data;
        final UserQuery      sample;
        final Pageable       pageable;
        final User           user;

        pageable = Pageable.unpaged();

        sample = UsersQuery.empty();

        data = service.getAll(sample, pageable);

        user = data.iterator()
            .next();

        UserAssertions.isEqualTo(user, ImmutableUser.builder()
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build());
    }

}
