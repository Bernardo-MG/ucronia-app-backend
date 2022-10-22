
package com.bernardomg.security.test.permission;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.DtoPermission;
import com.bernardomg.security.model.Permission;
import com.bernardomg.security.service.PermissionService;

@IntegrationTest
@DisplayName("Permission service - get all")
@Sql({ "/db/queries/security/permission/multiple.sql" })
public class ITPermissionServiceGetAll {

    @Autowired
    private PermissionService service;

    public ITPermissionServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<? extends Permission> result;
        final DtoPermission                  sample;
        final Pageable                       pageable;

        pageable = Pageable.unpaged();

        sample = new DtoPermission();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(4, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterable<? extends Permission> data;
        final DtoPermission                  sample;
        final Pageable                       pageable;
        final Collection<String>             names;

        pageable = Pageable.unpaged();

        sample = new DtoPermission();

        data = service.getAll(sample, pageable);

        names = StreamSupport.stream(data.spliterator(), false)
            .map(Permission::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(names.contains("CREATE_DATA"));
        Assertions.assertTrue(names.contains("READ_DATA"));
        Assertions.assertTrue(names.contains("UPDATE_DATA"));
        Assertions.assertTrue(names.contains("DELETE_DATA"));
    }

}
