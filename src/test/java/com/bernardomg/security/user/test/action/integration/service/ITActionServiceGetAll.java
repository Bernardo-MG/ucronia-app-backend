
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
import com.bernardomg.security.user.model.request.ActionQuery;
import com.bernardomg.security.user.model.request.ValidatedActionQuery;
import com.bernardomg.security.user.service.ActionService;

@IntegrationTest
@DisplayName("Action service - get all")
@Sql({ "/db/queries/security/action/crud.sql" })
class ITActionServiceGetAll {

    @Autowired
    private ActionService service;

    public ITActionServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    void testGetAll_Count() {
        final Iterable<Action> result;
        final ActionQuery      sample;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        sample = ValidatedActionQuery.builder()
            .build();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(4);
    }

    @Test
    @DisplayName("Returns all data")
    void testGetAll_Data() {
        final Iterable<Action>   data;
        final ActionQuery        sample;
        final Pageable           pageable;
        final Collection<String> names;

        pageable = Pageable.unpaged();

        sample = ValidatedActionQuery.builder()
            .build();

        data = service.getAll(sample, pageable);

        names = StreamSupport.stream(data.spliterator(), false)
            .map(Action::getName)
            .toList();

        Assertions.assertThat(names)
            .contains("CREATE")
            .contains("READ")
            .contains("UPDATE")
            .contains("DELETE");
    }

}
