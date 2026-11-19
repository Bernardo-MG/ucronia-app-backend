
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

import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.association.calendar.game.usecase.service.DefaultScheduledGameService;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - create")
class TestScheduledGameServiceCreate {

    @Mock
    private EventEmitter                eventEmitter;

    @Mock
    private ScheduledGameRepository     scheduledGameRepository;

    @InjectMocks
    private DefaultScheduledGameService service;

    @Test
    @DisplayName("When negative max players is provided, then an exception is thrown")
    void testCreate_NegativeMaxPlayers() {
        final ThrowingCallable action;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.negativeMaxPlayers();

        // WHEN
        action = () -> service.create(scheduledGame);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("negative", "maxPlayers", scheduledGame.maxPlayers()));
    }

    @Test
    @DisplayName("When negative recurrence is provided, then an exception is thrown")
    void testCreate_NegativeRecurrence() {
        final ThrowingCallable action;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.negativeRecurrence();

        // WHEN
        action = () -> service.create(scheduledGame);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("negative", "recurrence.interval", scheduledGame.recurrence()
                .get()
                .interval()));
    }

    @Test
    @DisplayName("When there is a valid scheduled game, then it is persisted")
    void testCreate_PersistedData() {
        final ScheduledGame scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();

        // WHEN
        service.create(scheduledGame);

        // THEN
        verify(scheduledGameRepository).save(scheduledGame);
    }

    @Test
    @DisplayName("When there is a published scheduled game, then it is persisted as draft")
    void testCreate_Published() {
        final ScheduledGame scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.published();

        // WHEN
        service.create(scheduledGame);

        // THEN
        verify(scheduledGameRepository).save(ScheduledGames.draft());
    }

    @Test
    @DisplayName("When there is a valid scheduled game, then it is returned")
    void testCreate_ReturnedData() {
        final ScheduledGame scheduledGame;
        final ScheduledGame created;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();

        given(scheduledGameRepository.save(scheduledGame)).willReturn(scheduledGame);

        // WHEN
        created = service.create(scheduledGame);

        // THEN
        Assertions.assertThat(created)
            .as("activity")
            .isEqualTo(scheduledGame);
    }

    @Test
    @DisplayName("When zero max players is provided, then an exception is thrown")
    void testCreate_ZeroMaxPlayers() {
        final ThrowingCallable action;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.zeroMaxPlayers();

        // WHEN
        action = () -> service.create(scheduledGame);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("negative", "maxPlayers", scheduledGame.maxPlayers()));
    }

    @Test
    @DisplayName("When zero recurrence is provided, then it is persisted")
    void testCreate_ZeroRecurrence() {
        final ScheduledGame scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.zeroRecurrence();

        // WHEN
        service.create(scheduledGame);

        // THEN
        verify(scheduledGameRepository).save(scheduledGame);
    }

}
