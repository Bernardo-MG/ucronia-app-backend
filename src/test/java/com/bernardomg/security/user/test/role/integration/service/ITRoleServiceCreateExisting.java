
package com.bernardomg.security.user.test.role.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.ImmutableRole;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.security.user.service.RoleService;

@IntegrationTest
@DisplayName("Role service - create - existing")
@Sql({ "/db/queries/security/role/single.sql" })
public class ITRoleServiceCreateExisting {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleService    service;

    public ITRoleServiceCreateExisting() {
        super();
    }

    @Test
    @DisplayName("Doesn't create over existing ids")
    public void testCreate() {
        final ImmutableRole data;
        final Role          result;

        data = ImmutableRole.builder()
            .id(1L)
            .name("Role")
            .build();

        result = service.create(data);

        Assertions.assertNotEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Doesn't create over existing ids")
    public void testCreate_AddsEntity() {
        final ImmutableRole data;

        data = ImmutableRole.builder()
            .id(1L)
            .name("Role")
            .build();

        service.create(data);

        Assertions.assertEquals(2L, repository.count());
    }

}
