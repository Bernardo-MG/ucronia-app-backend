
package com.bernardomg.association.calendar.game.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - update")
class TestScheduledGameServiceUpdate {

    @Mock
    private EventEmitter                eventEmitter;

    @Mock
    private ScheduledGameRepository     scheduledGameRepository;

    @InjectMocks
    private DefaultScheduledGameService service;

    @Test
    @DisplayName("When negative max players is provided, then an exception is thrown")
    void testUpdate_NegativeMaxPlayers() {
        final ThrowingCallable action;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.negativeMaxPlayers();

        given(scheduledGameRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        action = () -> service.update(scheduledGame);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("negative", "maxPlayers", scheduledGame.maxPlayers()));
    }

    @Test
    @DisplayName("When negative recurrence is provided, then an exception is thrown")
    void testUpdate_NegativeRecurrence() {
        final ThrowingCallable action;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.negativeRecurrence();

        given(scheduledGameRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        action = () -> service.update(scheduledGame);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("negative", "recurrence.interval", scheduledGame.recurrence()
                .get()
                .interval()));
    }

    @Test
    @DisplayName("When there is a not existing entity, then an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final ScheduledGame    scheduledGame;
        final ThrowingCallable action;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();

        given(scheduledGameRepository.exists(ActivityConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(scheduledGame);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing entity, then an exception is thrown")
            .isInstanceOf(MissingScheduledGameException.class);
    }

    @Test
    @DisplayName("When there is a valid scheduled game, then it is persisted")
    void testUpdate_PersistedData() {
        final ScheduledGame scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();

        given(scheduledGameRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(scheduledGame);

        // THEN
        verify(scheduledGameRepository).save(ScheduledGames.weeklyOneshot());
    }

    @Test
    @DisplayName("When there is a valid scheduled game, then it is returned")
    void testUpdate_ReturnedData() {
        final ScheduledGame scheduledGame;
        final ScheduledGame updated;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();

        given(scheduledGameRepository.save(scheduledGame)).willReturn(scheduledGame);
        given(scheduledGameRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        updated = service.update(scheduledGame);

        // THEN
        Assertions.assertThat(updated)
            .as("scheduled game")
            .isEqualTo(ScheduledGames.weeklyOneshot());
    }

    @Test
    @DisplayName("When zero max players is provided, then an exception is thrown")
    void testUpdate_ZeroMaxPlayers() {
        final ThrowingCallable action;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.zeroMaxPlayers();

        given(scheduledGameRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        action = () -> service.update(scheduledGame);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("negative", "maxPlayers", scheduledGame.maxPlayers()));
    }

    @Test
    @DisplayName("When zero recurrence is provided, then it is persisted")
    void testUpdate_ZeroRecurrenceData() {
        final ScheduledGame scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.zeroRecurrence();

        given(scheduledGameRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(scheduledGame);

        // THEN
        verify(scheduledGameRepository).save(ScheduledGames.zeroRecurrence());
    }

}
