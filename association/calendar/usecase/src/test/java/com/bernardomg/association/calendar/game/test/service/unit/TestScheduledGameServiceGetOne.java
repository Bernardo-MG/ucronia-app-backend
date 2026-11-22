
package com.bernardomg.association.calendar.game.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.game.domain.exception.MissingScheduledGameException;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.association.calendar.game.usecase.service.DefaultScheduledGameService;
import com.bernardomg.event.emitter.EventEmitter;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - getOne")
class TestScheduledGameServiceGetOne {

    @Mock
    private EventEmitter                eventEmitter;

    @Mock
    private ScheduledGameRepository     scheduledGameRepository;

    @InjectMocks
    private DefaultScheduledGameService service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testFindOne() {
        final ScheduledGame           scheduledGame;
        final Optional<ScheduledGame> read;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();
        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));

        // WHEN
        read = service.getOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(read)
            .as("scheduled game")
            .contains(scheduledGame);
    }

    @Test
    @DisplayName("When there is no data, then an exception is thrown")
    void testFindOne_NoData() {
        final ThrowingCallable action;

        // GIVEN
        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is no data, then an exception is thrown")
            .isInstanceOf(MissingScheduledGameException.class);
    }

}
