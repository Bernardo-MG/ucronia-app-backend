
package com.bernardomg.security.user.test.role.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.request.DtoRoleCreateRequest;
import com.bernardomg.security.user.model.request.RoleCreateRequest;
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
        final RoleCreateRequest data;

        data = DtoRoleCreateRequest.builder()
            .name("Role")
            .build();

        service.create(data);

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final RoleCreateRequest data;
        final PersistentRole    entity;

        data = DtoRoleCreateRequest.builder()
            .name("Role")
            .build();

        service.create(data);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getId())
            .isNotNull();
        Assertions.assertThat(entity.getName())
            .isEqualTo("Role");
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final RoleCreateRequest data;
        final Role              result;

        data = DtoRoleCreateRequest.builder()
            .name("Role")
            .build();

        result = service.create(data);

        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getName())
            .isEqualTo("Role");
    }

}
