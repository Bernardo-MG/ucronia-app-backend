
package com.bernardomg.security.user.test.action.integration.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.user.model.Action;
import com.bernardomg.security.user.service.ActionService;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Action service - get one")
@Sql({ "/db/queries/security/action/crud.sql" })
class ITActionServiceGetOne {

    @Autowired
    private ActionService service;

    public ITActionServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("Returns a single entity by id")
    void testGetOne_Existing() {
        final Optional<Action> result;

        result = service.getOne(1l);

        Assertions.assertThat(result)
            .isPresent();
    }

    @Test
    @DisplayName("Returns the correct data when reading a single entity")
    void testGetOne_Existing_Data() {
        final Action result;

        result = service.getOne(1l)
            .get();

        Assertions.assertThat(result.getName())
            .isEqualTo("CREATE");
    }

    @Test
    @DisplayName("When reading a single entity with an invalid id, no entity is returned")
    void testGetOne_NotExisting() {
        final Optional<Action> result;

        result = service.getOne(-1L);

        Assertions.assertThat(result)
            .isNotPresent();
    }

}
