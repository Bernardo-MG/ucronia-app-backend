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
import com.bernardomg.association.calendar.game.domain.exception.MissingGameSessionInfoException;
import com.bernardomg.association.calendar.game.domain.model.GameSessionInfo;
import com.bernardomg.association.calendar.game.domain.repository.GameSessionInfoRepository;
import com.bernardomg.association.calendar.game.usecase.service.DefaultGameSessionInfoService;
import com.bernardomg.association.calendar.session.test.configuration.factory.GameSessionInfos;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameSessionInfoService - update")
class TestGameSessionInfoServiceUpdate {

    @Mock
    private GameSessionInfoRepository     gameSessionInfoRepository;

    @InjectMocks
    private DefaultGameSessionInfoService service;

    @Test
    @DisplayName("With a not existing entity, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final GameSessionInfo  gameSessionInfo;
        final ThrowingCallable execution;

        // GIVEN
        gameSessionInfo = GameSessionInfos.weekly();

        given(gameSessionInfoRepository.exists(ActivityConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(gameSessionInfo);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingGameSessionInfoException.class);
    }

    @Test
    @DisplayName("With a member with padded name, the member is persisted")
    void testUpdate_Padded_PersistedData() {
        final GameSessionInfo gameSessionInfo;

        // GIVEN
        gameSessionInfo = GameSessionInfos.weekly();

        given(gameSessionInfoRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(gameSessionInfo);

        // THEN
        verify(gameSessionInfoRepository).save(GameSessionInfos.weekly());
    }

    @Test
    @DisplayName("With a valid game session info, it is persisted")
    void testUpdate_PersistedData() {
        final GameSessionInfo gameSessionInfo;

        // GIVEN
        gameSessionInfo = GameSessionInfos.weekly();

        given(gameSessionInfoRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(gameSessionInfo);

        // THEN
        verify(gameSessionInfoRepository).save(GameSessionInfos.weekly());
    }

    @Test
    @DisplayName("With a valid game session info, it is returned")
    void testUpdate_ReturnedData() {
        final GameSessionInfo gameSessionInfo;
        final GameSessionInfo updated;

        // GIVEN
        gameSessionInfo = GameSessionInfos.weekly();

        given(gameSessionInfoRepository.save(gameSessionInfo)).willReturn(gameSessionInfo);
        given(gameSessionInfoRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        updated = service.update(gameSessionInfo);

        // THEN
        Assertions.assertThat(updated)
            .as("game session info")
            .isEqualTo(GameSessionInfos.weekly());
    }

}
