
package com.bernardomg.security.data.test.role;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.DtoRole;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.service.RoleService;

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
        final Iterable<? extends Role> result;
        final DtoRole                  sample;
        final Pageable                 pageable;

        pageable = Pageable.unpaged();

        sample = new DtoRole();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterable<? extends Role> data;
        final DtoRole                  sample;
        final Pageable                 pageable;
        final Role                     role;

        pageable = Pageable.unpaged();

        sample = new DtoRole();

        data = service.getAll(sample, pageable);

        role = data.iterator()
            .next();

        Assertions.assertNotNull(role.getId());
        Assertions.assertEquals("ADMIN", role.getName());
    }

}
