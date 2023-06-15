
package com.bernardomg.security.user.test.role.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.request.DtoRoleQueryRequest;
import com.bernardomg.security.user.model.request.RoleQueryRequest;
import com.bernardomg.security.user.service.RoleService;

@IntegrationTest
@DisplayName("Role service - get all")
@Sql({ "/db/queries/security/role/single.sql" })
public class ITRoleServiceGetAll {

    @Autowired
    private RoleService service;

    public ITRoleServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<Role>   result;
        final RoleQueryRequest sample;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        sample = DtoRoleQueryRequest.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterable<Role>   data;
        final RoleQueryRequest sample;
        final Pageable         pageable;
        final Role             role;

        pageable = Pageable.unpaged();

        sample = DtoRoleQueryRequest.builder()
            .build();

        data = service.getAll(sample, pageable);

        role = data.iterator()
            .next();

        Assertions.assertThat(role.getId())
            .isNotNull();
        Assertions.assertThat(role.getName())
            .isEqualTo("ADMIN");
    }

}
