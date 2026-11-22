
package com.bernardomg.association.calendar.game.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.ValidTable;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTables;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameTableRepository - findAll")
class ITGameTableRepositoryFindAll {

    @Autowired
    private GameTableRepository repository;

    @Test
    @DisplayName("When there are tables, then they are returned")
    @ValidTable
    void testFindAll() {
        final Page<GameTable> gameTables;
        final Pagination      pagination;
        final Sorting         sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        gameTables = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(gameTables)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("game tables")
            .contains(GameTables.valid());
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindAll_DefaultCount() {
        final Page<GameTable> gameTables;
        final Pagination      pagination;
        final Sorting         sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        gameTables = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(gameTables.content())
            .as("When there is no data, then nothing is returned")
            .isEmpty();
    }

}
