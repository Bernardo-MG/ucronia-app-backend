
package com.bernardomg.security.data.test.role;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.DtoRole;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.model.PersistentRole;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.security.data.service.RoleService;

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
        final DtoRole data;

        data = new DtoRole();
        data.setName("Role");

        service.create(data);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final DtoRole        data;
        final PersistentRole entity;

        data = new DtoRole();
        data.setName("Role");

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
        final DtoRole data;
        final Role    result;

        data = new DtoRole();
        data.setName("Role");

        result = service.create(data);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Role", result.getName());
    }

}
