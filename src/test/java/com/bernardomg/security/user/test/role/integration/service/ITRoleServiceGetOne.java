
package com.bernardomg.security.user.test.role.integration.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.service.RoleService;

@IntegrationTest
@DisplayName("Role service - get one - no action")
@Sql({ "/db/queries/security/role/single.sql" })
public class ITRoleServiceGetOne {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("Returns a single entity by id")
    public void testGetOne_Existing() {
        final Optional<Role> result;

        result = service.getOne(1l);

        Assertions.assertThat(result)
            .isPresent();
    }

    @Test
    @DisplayName("Returns the correct data when reading a single entity")
    public void testGetOne_Existing_Data() {
        final Role result;

        result = service.getOne(1l)
            .get();

        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getName())
            .isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("When reading a single entity with an invalid id, no entity is returned")
    public void testGetOne_NotExisting() {
        final Optional<Role> result;

        result = service.getOne(-1L);

        Assertions.assertThat(result)
            .isNotPresent();
    }

}
