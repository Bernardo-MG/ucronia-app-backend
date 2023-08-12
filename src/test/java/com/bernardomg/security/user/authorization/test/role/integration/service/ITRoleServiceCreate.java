
package com.bernardomg.security.user.authorization.test.role.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.security.user.authorization.persistence.model.PersistentRole;
import com.bernardomg.security.user.authorization.persistence.repository.RoleRepository;
import com.bernardomg.security.user.authorization.service.RoleService;
import com.bernardomg.security.user.authorization.test.util.model.RolesCreate;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.request.RoleCreate;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Role service - create")
class ITRoleServiceCreate {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleService    service;

    public ITRoleServiceCreate() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when creating")
    void testCreate_AddsEntity() {
        final RoleCreate data;

        data = RolesCreate.valid();

        service.create(data);

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Persists the data")
    void testCreate_PersistedData() {
        final RoleCreate     data;
        final PersistentRole entity;

        data = RolesCreate.valid();

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
    void testCreate_ReturnedData() {
        final RoleCreate data;
        final Role       result;

        data = RolesCreate.valid();

        result = service.create(data);

        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getName())
            .isEqualTo("Role");
    }

}
