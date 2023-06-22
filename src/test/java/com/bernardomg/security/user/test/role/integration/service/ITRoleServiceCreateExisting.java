
package com.bernardomg.security.user.test.role.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.request.DtoRoleCreateRequest;
import com.bernardomg.security.user.model.request.RoleCreateRequest;
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
        final RoleCreateRequest data;
        final Role              result;

        data = DtoRoleCreateRequest.builder()
            .name("Role")
            .build();

        result = service.create(data);

        Assertions.assertThat(result.getId())
            .isNotEqualTo(1);
    }

    @Test
    @DisplayName("Doesn't create over existing ids")
    public void testCreate_AddsEntity() {
        final RoleCreateRequest data;

        data = DtoRoleCreateRequest.builder()
            .name("Role")
            .build();

        service.create(data);

        Assertions.assertThat(repository.count())
            .isEqualTo(2);
    }

}
