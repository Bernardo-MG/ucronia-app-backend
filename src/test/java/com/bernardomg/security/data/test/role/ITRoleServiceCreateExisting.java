
package com.bernardomg.security.data.test.role;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.DtoRole;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.security.data.service.RoleService;

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
        final DtoRole data;
        final Role    result;

        data = new DtoRole();
        data.setId(1L);
        data.setName("Role");

        result = service.create(data);

        Assertions.assertNotEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Doesn't create over existing ids")
    public void testCreate_AddsEntity() {
        final DtoRole data;

        data = new DtoRole();
        data.setId(1L);
        data.setName("Role");

        service.create(data);

        Assertions.assertEquals(2L, repository.count());
    }

}
