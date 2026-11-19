
package com.bernardomg.association.library.gamesystem.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.association.library.gamesystem.usecase.service.DefaultGameSystemService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameSystemService - getAll")
class TestGameSystemServiceGetAll {

    @Mock
    private GameSystemRepository     gameSystemRepository;

    @InjectMocks
    private DefaultGameSystemService service;

    @Test
    @DisplayName("When there are game systems, then they are returned")
    void testGetAll() {
        final Pagination       pagination;
        final Sorting          sorting;
        final Page<GameSystem> systems;
        final Page<GameSystem> existing;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(GameSystems.valid()), 0, 0, 0, 0, 0, false, false, sorting);
        given(gameSystemRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        systems = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(systems)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("game systems")
            .containsExactly(GameSystems.valid());
    }

    @Test
    @DisplayName("When there are no game systems, then nothing is returned")
    void testGetAll_NoData() {
        final Pagination       pagination;
        final Sorting          sorting;
        final Page<GameSystem> systems;
        final Page<GameSystem> existing;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(gameSystemRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        systems = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(systems)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("game systems")
            .isEmpty();
    }

}
