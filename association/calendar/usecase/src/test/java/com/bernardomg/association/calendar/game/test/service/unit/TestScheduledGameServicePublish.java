/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
import com.bernardomg.association.calendar.domain.event.CalendarInfoPublishedEvent;
import com.bernardomg.association.calendar.game.domain.exception.ScheduledGameAlreadyPublishedException;
import com.bernardomg.association.calendar.game.domain.exception.ScheduledGameNotPublishableException;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.association.calendar.game.usecase.service.DefaultScheduledGameService;
import com.bernardomg.event.emitter.EventEmitter;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - create")
class TestScheduledGameServicePublish {

    @Mock
    private EventEmitter                eventEmitter;

    @Mock
    private ScheduledGameRepository     scheduledGameRepository;

    @InjectMocks
    private DefaultScheduledGameService service;

    @Test
    @DisplayName("When the scheduled game is cancelled, an exception is thrown")
    void testPublish_Cancelled() {
        final ThrowingCallable execution;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.cancelled();

        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));

        // WHEN
        execution = () -> service.publish(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ScheduledGameNotPublishableException.class);
    }

    @Test
    @DisplayName("When publishing a scheduled game, an event is emitted")
    void testPublish_EmitsEvent() {
        final ScheduledGame              scheduledGame;
        final ScheduledGame              scheduledGamePublished;
        final CalendarInfoPublishedEvent event;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();
        scheduledGamePublished = ScheduledGames.weeklyOneshotPublished();

        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));
        given(scheduledGameRepository.save(scheduledGamePublished)).willReturn(scheduledGamePublished);

        event = new CalendarInfoPublishedEvent(null, scheduledGame.number());

        // WHEN
        service.publish(ActivityConstants.NUMBER);

        // THEN
        verify(eventEmitter).emit(event);
    }

    @Test
    @DisplayName("When publishing a scheduled game, it is persisted")
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
    @DisplayName("When the scheduled game is already published, an exception is thrown")
    void testPublish_Published() {
        final ThrowingCallable execution;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.published();

        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));

        // WHEN
        execution = () -> service.publish(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ScheduledGameAlreadyPublishedException.class);
    }

    @Test
    @DisplayName("When the scheduled game is rejected, an exception is thrown")
    void testPublish_Rejected() {
        final ThrowingCallable execution;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.rejected();

        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));

        // WHEN
        execution = () -> service.publish(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(ScheduledGameNotPublishableException.class);
    }

    @Test
    @DisplayName("When publishing a scheduled game, it is returned")
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
