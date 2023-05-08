
package com.bernardomg.security.data.test.resource.integration.service;

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
import com.bernardomg.security.data.model.DtoResource;
import com.bernardomg.security.data.model.Resource;
import com.bernardomg.security.data.service.ResourceService;

@IntegrationTest
@DisplayName("Resource service - get all")
@Sql({ "/db/queries/security/resource/multiple.sql" })
public class ITResourceServiceGetAll {

    @Autowired
    private ResourceService service;

    public ITResourceServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<? extends Resource> result;
        final DtoResource                  sample;
        final Pageable                     pageable;

        pageable = Pageable.unpaged();

        sample = new DtoResource();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(4, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterable<? extends Resource> data;
        final DtoResource                  sample;
        final Pageable                     pageable;
        final Collection<String>           names;

        pageable = Pageable.unpaged();

        sample = new DtoResource();

        data = service.getAll(sample, pageable);

        names = StreamSupport.stream(data.spliterator(), false)
            .map(Resource::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(names.contains("DATA1"));
        Assertions.assertTrue(names.contains("DATA2"));
        Assertions.assertTrue(names.contains("DATA3"));
        Assertions.assertTrue(names.contains("DATA4"));
    }

}
