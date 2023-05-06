
package com.bernardomg.security.data.test.privilege.integration.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.Privilege;
import com.bernardomg.security.data.service.PrivilegeService;

@IntegrationTest
@DisplayName("Privilege service - get one")
@Sql({ "/db/queries/security/privilege/crud.sql" })
public class ITPrivilegeServiceGetOne {

    @Autowired
    private PrivilegeService service;

    public ITPrivilegeServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("Returns a single entity by id")
    public void testGetOne_Existing() {
        final Optional<? extends Privilege> result;

        result = service.getOne(1l);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Returns the correct data when reading a single entity")
    public void testGetOne_Existing_Data() {
        final Privilege result;

        result = service.getOne(1l)
            .get();

        Assertions.assertEquals("CREATE_DATA", result.getName());
    }

    @Test
    @DisplayName("When reading a single entity with an invalid id, no entity is returned")
    public void testGetOne_NotExisting() {
        final Optional<? extends Privilege> result;

        result = service.getOne(-1L);

        Assertions.assertFalse(result.isPresent());
    }

}
