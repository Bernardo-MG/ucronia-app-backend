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

package com.bernardomg.association.calendar.session.test.service.unit;

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
import com.bernardomg.association.calendar.game.usecase.service.DefaultScheduledGameService;
import com.bernardomg.association.calendar.session.test.configuration.factory.ScheduledGames;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - update")
class TestScheduledGameServiceUpdate {

    @Mock
    private ScheduledGameRepository     scheduledGameRepository;

    @InjectMocks
    private DefaultScheduledGameService service;

    @Test
    @DisplayName("With a not existing entity, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final ScheduledGame    scheduledGame;
        final ThrowingCallable execution;

        // GIVEN
        scheduledGame = ScheduledGames.weekly();

        given(scheduledGameRepository.exists(ActivityConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(scheduledGame);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingScheduledGameException.class);
    }

    @Test
    @DisplayName("With a member with padded name, the member is persisted")
    void testUpdate_Padded_PersistedData() {
        final ScheduledGame scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.weekly();

        given(scheduledGameRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(scheduledGame);

        // THEN
        verify(scheduledGameRepository).save(ScheduledGames.weekly());
    }

    @Test
    @DisplayName("With a valid scheduled game, it is persisted")
    void testUpdate_PersistedData() {
        final ScheduledGame scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.weekly();

        given(scheduledGameRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(scheduledGame);

        // THEN
        verify(scheduledGameRepository).save(ScheduledGames.weekly());
    }

    @Test
    @DisplayName("With a valid scheduled game, it is returned")
    void testUpdate_ReturnedData() {
        final ScheduledGame scheduledGame;
        final ScheduledGame updated;

        // GIVEN
        scheduledGame = ScheduledGames.weekly();

        given(scheduledGameRepository.save(scheduledGame)).willReturn(scheduledGame);
        given(scheduledGameRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        updated = service.update(scheduledGame);

        // THEN
        Assertions.assertThat(updated)
            .as("scheduled game")
            .isEqualTo(ScheduledGames.weekly());
    }

}
