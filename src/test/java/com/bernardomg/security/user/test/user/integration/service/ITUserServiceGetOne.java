
package com.bernardomg.security.user.test.user.integration.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.DtoUser;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.security.user.test.util.assertion.UserAssertions;

@IntegrationTest
@DisplayName("User service - get one - with role and no action")
@Sql({ "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/user_role.sql" })
public class ITUserServiceGetOne {

    @Autowired
    private UserService service;

    public ITUserServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("Returns a single entity by id")
    public void testGetOne_Existing() {
        final Optional<User> result;

        result = service.getOne(1l);

        Assertions.assertThat(result)
            .isPresent();
    }

    @Test
    @DisplayName("Returns the correct data when reading a single entity")
    public void testGetOne_Existing_Data() {
        final User result;

        result = service.getOne(1l)
            .get();

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
    @DisplayName("When reading a single entity with an invalid id, no entity is returned")
    public void testGetOne_NotExisting() {
        final Optional<User> result;

        result = service.getOne(-1L);

        Assertions.assertThat(result)
            .isNotPresent();
    }

}
