
package com.bernardomg.security.user.test.action.integration.service;

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
import com.bernardomg.security.user.model.Action;
import com.bernardomg.security.user.model.request.ActionQueryRequest;
import com.bernardomg.security.user.model.request.DtoActionQueryRequest;
import com.bernardomg.security.user.service.ActionService;

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

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(4);
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
            .toList();

        Assertions.assertThat(names)
            .contains("CREATE");
        Assertions.assertThat(names)
            .contains("READ");
        Assertions.assertThat(names)
            .contains("UPDATE");
        Assertions.assertThat(names)
            .contains("DELETE");
    }

}
