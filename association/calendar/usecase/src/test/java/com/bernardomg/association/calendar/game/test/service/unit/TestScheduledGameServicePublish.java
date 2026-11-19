
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
import com.bernardomg.association.calendar.domain.event.CalendarEventFactory;
import com.bernardomg.association.calendar.domain.event.ScheduledGamePublishedEvent;
import com.bernardomg.association.calendar.game.domain.exception.ScheduledGameAlreadyPublishedException;
import com.bernardomg.association.calendar.game.domain.exception.ScheduledGameNotPublishableException;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.association.calendar.game.usecase.service.DefaultScheduledGameService;
import com.bernardomg.event.emitter.EventEmitter;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - publish")
class TestScheduledGameServicePublish {

    @Mock
    private EventEmitter                eventEmitter;

    @Mock
    private ScheduledGameRepository     scheduledGameRepository;

    @InjectMocks
    private DefaultScheduledGameService service;

    @Test
    @DisplayName("When the scheduled game is cancelled, then an exception is thrown")
    void testPublish_Cancelled() {
        final ThrowingCallable action;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.cancelled();

        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));

        // WHEN
        action = () -> service.publish(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the scheduled game is cancelled, then an exception is thrown")
            .isInstanceOf(ScheduledGameNotPublishableException.class);
    }

    @Test
    @DisplayName("When publishing a scheduled game, then an event is emitted")
    void testPublish_EmitsEvent() {
        final ScheduledGame               scheduledGame;
        final ScheduledGame               scheduledGamePublished;
        final ScheduledGamePublishedEvent event;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();
        scheduledGamePublished = ScheduledGames.weeklyOneshotPublished();

        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));
        given(scheduledGameRepository.save(scheduledGamePublished)).willReturn(scheduledGamePublished);

        event = CalendarEventFactory.scheduledGamePublished(scheduledGame.number());

        // WHEN
        service.publish(ActivityConstants.NUMBER);

        // THEN
        verify(eventEmitter).emit(
            org.mockito.ArgumentMatchers.argThat(actual -> actual instanceof final ScheduledGamePublishedEvent emitted
                    && emitted.getScheduledGameNumber()
                        .equals(event.getScheduledGameNumber())
                    && emitted.getSource()
                        .equals(event.getSource())));
    }

    @Test
    @DisplayName("When publishing a scheduled game, then it is persisted")
    void testPublish_PersistedData() {
        final ScheduledGame scheduledGame;
        final ScheduledGame scheduledGamePublished;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();
        scheduledGamePublished = ScheduledGames.weeklyOneshotPublished();

        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));
        given(scheduledGameRepository.save(scheduledGamePublished)).willReturn(scheduledGamePublished);

        // WHEN
        service.publish(ActivityConstants.NUMBER);

        // THEN
        verify(scheduledGameRepository).save(ScheduledGames.weeklyOneshotPublished());
    }

    @Test
    @DisplayName("When the scheduled game is already published, then an exception is thrown")
    void testPublish_Published() {
        final ThrowingCallable action;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.published();

        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));

        // WHEN
        action = () -> service.publish(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the scheduled game is already published, then an exception is thrown")
            .isInstanceOf(ScheduledGameAlreadyPublishedException.class);
    }

    @Test
    @DisplayName("When the scheduled game is rejected, then an exception is thrown")
    void testPublish_Rejected() {
        final ThrowingCallable action;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.rejected();

        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));

        // WHEN
        action = () -> service.publish(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the scheduled game is rejected, then an exception is thrown")
            .isInstanceOf(ScheduledGameNotPublishableException.class);
    }

    @Test
    @DisplayName("When publishing a scheduled game, then it is returned")
    void testPublish_ReturnedData() {
        final ScheduledGame scheduledGame;
        final ScheduledGame scheduledGamePublished;
        final ScheduledGame published;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();
        scheduledGamePublished = ScheduledGames.weeklyOneshotPublished();

        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));
        given(scheduledGameRepository.save(scheduledGamePublished)).willReturn(scheduledGamePublished);

        // WHEN
        published = service.publish(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(published)
            .as("activity")
            .isEqualTo(ScheduledGames.weeklyOneshotPublished());
    }

}
