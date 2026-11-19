
package com.bernardomg.association.calendar.game.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.association.calendar.game.usecase.service.DefaultScheduledGameService;
import com.bernardomg.event.emitter.EventEmitter;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - delete")
class TestScheduledGameServiceDelete {

    @Mock
    private EventEmitter                eventEmitter;

    @Mock
    private ScheduledGameRepository     scheduledGameRepository;

    @InjectMocks
    private DefaultScheduledGameService service;

    @Test
    @DisplayName("When the scheduled game doesn't exist, then an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the scheduled game doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingScheduledGameException.class);
    }

    @Test
    @DisplayName("When deleting, then the repository is called")
    void testDelete_RemovesEntity() {
        // GIVEN
        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER))
            .willReturn(Optional.of(ScheduledGames.weeklyOneshot()));

        // WHEN
        service.delete(ActivityConstants.NUMBER);

        // THEN
        verify(scheduledGameRepository).delete(ActivityConstants.NUMBER);
    }

}
