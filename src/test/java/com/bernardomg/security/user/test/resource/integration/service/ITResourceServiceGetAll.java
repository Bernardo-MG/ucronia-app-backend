
package com.bernardomg.security.user.test.resource.integration.service;

import java.util.Collection;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.Resource;
import com.bernardomg.security.user.model.request.ResourceQuery;
import com.bernardomg.security.user.model.request.ValidatedResourceQuery;
import com.bernardomg.security.user.service.ResourceService;

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
        final Iterable<Resource> result;
        final ResourceQuery      sample;
        final Pageable           pageable;

        pageable = Pageable.unpaged();

        sample = ValidatedResourceQuery.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(4);
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterable<Resource> data;
        final ResourceQuery      sample;
        final Pageable           pageable;
        final Collection<String> names;

        pageable = Pageable.unpaged();

        sample = ValidatedResourceQuery.builder()
            .build();

        data = service.getAll(sample, pageable);

        names = StreamSupport.stream(data.spliterator(), false)
            .map(Resource::getName)
            .toList();

        Assertions.assertThat(names)
            .contains("DATA1")
            .contains("DATA2")
            .contains("DATA3")
            .contains("DATA4");
    }

}
