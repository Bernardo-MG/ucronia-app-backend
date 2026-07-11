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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.association.calendar.game.usecase.service.DefaultScheduledGameService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - create")
class TestScheduledGameServicePublish {

    @Mock
    private ScheduledGameRepository     scheduledGameRepository;

    @InjectMocks
    private DefaultScheduledGameService service;

    @Test
    @DisplayName("When publishing a scheduled game, it is persisted")
    void testPublish_PersistedData() {
        final ScheduledGame scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();
        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));

        // WHEN
        service.publish(ActivityConstants.NUMBER);

        // THEN
        verify(scheduledGameRepository).save(ScheduledGames.weeklyOneshotPublished());
    }

    @Test
    @DisplayName("When publishing a scheduled game, it is returned")
    void testPublish_ReturnedData() {
        final ScheduledGame scheduledGame;
        final ScheduledGame created;

        // GIVEN
        scheduledGame = ScheduledGames.weeklyOneshot();
        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(scheduledGame));

        given(scheduledGameRepository.save(scheduledGame)).willReturn(scheduledGame);

        // WHEN
        created = service.publish(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(created)
            .as("activity")
            .isEqualTo(ScheduledGames.weeklyOneshotPublished());
    }

}
