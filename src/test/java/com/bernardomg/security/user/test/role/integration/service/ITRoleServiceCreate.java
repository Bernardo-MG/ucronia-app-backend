
package com.bernardomg.security.user.test.role.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.ImmutableRole;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.persistence.model.PersistentRole;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.security.user.service.RoleService;

@IntegrationTest
@DisplayName("Role service - create")
public class ITRoleServiceCreate {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleService    service;

    public ITRoleServiceCreate() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when creating")
    public void testCreate_AddsEntity() {
        final ImmutableRole data;

        data = ImmutableRole.builder()
            .name("Role")
            .build();

        service.create(data);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final ImmutableRole  data;
        final PersistentRole entity;

        data = ImmutableRole.builder()
            .name("Role")
            .build();

        service.create(data);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("Role", entity.getName());
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final ImmutableRole data;
        final Role          result;

        data = ImmutableRole.builder()
            .name("Role")
            .build();

        result = service.create(data);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Role", result.getName());
    }

}
