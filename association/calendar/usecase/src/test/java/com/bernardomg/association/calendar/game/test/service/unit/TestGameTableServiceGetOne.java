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

import com.bernardomg.association.calendar.domain.exception.MissingGameTableException;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTableConstants;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTables;
import com.bernardomg.association.calendar.game.usecase.service.DefaultGameTableService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameTableService - get one")
class TestGameTableServiceGetOne {

    @Mock
    private GameTableRepository     gameTableRepository;

    @InjectMocks
    private DefaultGameTableService service;

    public TestGameTableServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("When there is data it is returned")
    void testGetOne() {
        final Optional<GameTable> gameTable;

        // GIVEN
        given(gameTableRepository.findOne(GameTableConstants.NUMBER)).willReturn(Optional.of(GameTables.valid()));

        // WHEN
        gameTable = service.getOne(GameTableConstants.NUMBER);

        // THEN
        Assertions.assertThat(gameTable)
            .as("game table")
            .contains(GameTables.valid());
    }

    @Test
    @DisplayName("When there is no data an exception is thrown")
    void testGetOne_NoData() {
        final ThrowingCallable execution;

        // GIVEN
        given(gameTableRepository.findOne(GameTableConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(GameTableConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingGameTableException.class);
    }

}
