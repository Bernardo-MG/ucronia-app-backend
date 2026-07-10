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

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultScheduledGameService - get one")
class TestScheduledGameServiceGetOne {

    @Mock
    private ScheduledGameRepository     scheduledGameRepository;

    @InjectMocks
    private DefaultScheduledGameService service;

    public TestScheduledGameServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("When there is data it is returned")
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
    @DisplayName("When there is no data an exception is thrown")
    void testFindOne_NoData() {
        final ThrowingCallable execution;

        // GIVEN
        given(scheduledGameRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingScheduledGameException.class);
    }

}
