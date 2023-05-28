
package com.bernardomg.security.data.test.action.integration.service;

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
import com.bernardomg.security.data.model.Action;
import com.bernardomg.security.data.model.request.ActionQueryRequest;
import com.bernardomg.security.data.model.request.DtoActionQueryRequest;
import com.bernardomg.security.data.service.ActionService;

@IntegrationTest
@DisplayName("Action service - get all")
@Sql({ "/db/queries/security/action/crud.sql" })
public class ITActionServiceGetAll {

    @Autowired
    private ActionService service;

    public ITActionServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<Action>   result;
        final ActionQueryRequest sample;
        final Pageable           pageable;

        pageable = Pageable.unpaged();

        sample = DtoActionQueryRequest.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(4, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterable<Action>   data;
        final ActionQueryRequest sample;
        final Pageable           pageable;
        final Collection<String> names;

        pageable = Pageable.unpaged();

        sample = DtoActionQueryRequest.builder()
            .build();

        data = service.getAll(sample, pageable);

        names = StreamSupport.stream(data.spliterator(), false)
            .map(Action::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(names.contains("CREATE"));
        Assertions.assertTrue(names.contains("READ"));
        Assertions.assertTrue(names.contains("UPDATE"));
        Assertions.assertTrue(names.contains("DELETE"));
    }

}
