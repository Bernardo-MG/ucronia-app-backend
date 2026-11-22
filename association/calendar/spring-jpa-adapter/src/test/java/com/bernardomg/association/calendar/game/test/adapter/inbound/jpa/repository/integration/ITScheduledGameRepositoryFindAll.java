
package com.bernardomg.association.calendar.game.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.ScheduledGameWithTable;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.WeeklyScheduledGame;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ActivityRepository - findAll")
class ITActivityRepositoryFindAllWithFilter {

    @Autowired
    private ScheduledGameRepository repository;

    @Test
    @DisplayName("When there is a single scheduled game, then it returns all the scheduled games")
    @WeeklyScheduledGame
    void testFindAll() {
        final Page<ScheduledGame> scheduledGames;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        scheduledGames = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(scheduledGames)
            .as("When there is a single scheduled game, then it returns all the scheduled games")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(ScheduledGames.weeklyOneshot());
    }

    @Test
    @DisplayName("When there is no data, then it returns nothing")
    void testFindAll_NoData() {
        final Page<ScheduledGame> scheduledGames;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        scheduledGames = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(scheduledGames)
            .as("When there is no data, then it returns nothing")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a single scheduled game with a table, then it returns all the scheduled games")
    @ScheduledGameWithTable
    void testFindAll_WithTable() {
        final Page<ScheduledGame> scheduledGames;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        scheduledGames = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(scheduledGames)
            .as("When there is a single scheduled game with a table, then it returns all the scheduled games")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(ScheduledGames.withTable());
    }

}
