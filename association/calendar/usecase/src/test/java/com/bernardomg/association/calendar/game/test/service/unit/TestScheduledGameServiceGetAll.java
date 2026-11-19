
package com.bernardomg.association.calendar.game.test.service.unit;

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

import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.association.calendar.game.usecase.service.DefaultScheduledGameService;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - getAll")
class TestScheduledGameServiceGetAll {

    @Mock
    private EventEmitter                eventEmitter;

    @Mock
    private ScheduledGameRepository     scheduledGameRepository;

    @InjectMocks
    private DefaultScheduledGameService service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetAll() {
        final Page<ScheduledGame> scheduledGames;
        final Page<ScheduledGame> existing;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(ScheduledGames.weeklyOneshot()), 0, 0, 0, 0, 0, false, false, sorting);
        given(scheduledGameRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        scheduledGames = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(scheduledGames)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("Activities")
            .containsExactly(ScheduledGames.weeklyOneshot());
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testGetAll_NoData() {
        final Page<ScheduledGame> scheduledGames;
        final Page<ScheduledGame> existing;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(scheduledGameRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        scheduledGames = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(scheduledGames)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("scheduled game")
            .isEmpty();
    }

}
