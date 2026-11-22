
package com.bernardomg.association.library.gamesystem.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.test.configuration.data.annotation.ValidGameSystem;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameSystemRepository - findAll")
class ITGameSystemRepositoryFindAll {

    @Autowired
    private GameSystemRepository repository;

    @Test
    @DisplayName("When there are game systems, then they are returned")
    @ValidGameSystem
    void testFindAll() {
        final Page<GameSystem> gameSystems;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        gameSystems = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(gameSystems)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("game systems")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(GameSystems.valid());
    }

    @Test
    @DisplayName("When there are no game systems, then nothing is returned")
    void testFindAll_NoData() {
        final Page<GameSystem> gameSystems;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        gameSystems = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(gameSystems)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("game systems")
            .isEmpty();
    }

}
