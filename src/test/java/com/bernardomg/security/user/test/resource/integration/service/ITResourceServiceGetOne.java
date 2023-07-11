
package com.bernardomg.security.user.test.resource.integration.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.Resource;
import com.bernardomg.security.user.service.ResourceService;

@IntegrationTest
@DisplayName("Resource service - get one")
@Sql({ "/db/queries/security/resource/multiple.sql" })
class ITResourceServiceGetOne {

    @Autowired
    private ResourceService service;

    public ITResourceServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("Returns a single entity by id")
    void testGetOne_Existing() {
        final Optional<Resource> result;

        result = service.getOne(1l);

        Assertions.assertThat(result)
            .isPresent();
    }

    @Test
    @DisplayName("Returns the correct data when reading a single entity")
    void testGetOne_Existing_Data() {
        final Resource result;

        result = service.getOne(1l)
            .get();

        Assertions.assertThat(result.getName())
            .isEqualTo("DATA1");
    }

    @Test
    @DisplayName("When reading a single entity with an invalid id, no entity is returned")
    void testGetOne_NotExisting() {
        final Optional<Resource> result;

        result = service.getOne(-1L);

        Assertions.assertThat(result)
            .isNotPresent();
    }

}
