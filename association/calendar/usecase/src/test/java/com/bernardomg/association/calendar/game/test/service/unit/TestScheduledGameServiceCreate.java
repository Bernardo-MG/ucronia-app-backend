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
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - create")
class TestScheduledGameServiceCreate {

    @Mock
    private ScheduledGameRepository     scheduledGameRepository;

    @InjectMocks
    private DefaultScheduledGameService service;

    @Test
    @DisplayName("With negative max players, an exception is thrown")
    void testCreate_NegativeMaxPlayers() {
        final ThrowingCallable execution;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.negativeMaxPlayers();

        // WHEN
        execution = () -> service.create(scheduledGame);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("negative", "maxPlayers", scheduledGame.maxPlayers()));
    }

    @Test
    @DisplayName("With negative recurrence, an exception is thrown")
    void testCreate_NegativeRecurrence() {
        final ThrowingCallable execution;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.negativeRecurrence();

        // WHEN
        execution = () -> service.create(scheduledGame);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("negative", "recurrence.interval", scheduledGame.recurrence()
                .interval()));
    }

    @Test
    @DisplayName("With a valid scheduled game, it is persisted")
    void testCreate_PersistedData() {
        final ScheduledGame scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.weekly();

        // WHEN
        service.create(scheduledGame);

        // THEN
        verify(scheduledGameRepository).save(scheduledGame);
    }

    @Test
    @DisplayName("With a valid scheduled game, it is returned")
    void testCreate_ReturnedData() {
        final ScheduledGame scheduledGame;
        final ScheduledGame created;

        // GIVEN
        scheduledGame = ScheduledGames.weekly();

        given(scheduledGameRepository.save(scheduledGame)).willReturn(scheduledGame);

        // WHEN
        created = service.create(scheduledGame);

        // THEN
        Assertions.assertThat(created)
            .as("activity")
            .isEqualTo(scheduledGame);
    }

    @Test
    @DisplayName("With zero max players, an exception is thrown")
    void testCreate_ZeroMaxPlayers() {
        final ThrowingCallable execution;
        final ScheduledGame    scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.zeroMaxPlayers();

        // WHEN
        execution = () -> service.create(scheduledGame);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("negative", "maxPlayers", scheduledGame.maxPlayers()));
    }

    @Test
    @DisplayName("With zero recurrence, it is persisted")
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
