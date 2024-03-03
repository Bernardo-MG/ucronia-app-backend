/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.library.test.usecase.service.unit;

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

import com.bernardomg.association.library.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.test.config.factory.GameSystemConstants;
import com.bernardomg.association.library.usecase.service.DefaultGameSystemService;

@ExtendWith(MockitoExtension.class)
@DisplayName("GameSystemService - delete")
class TestGameSystemServiceDelete {

    @Mock
    private GameSystemRepository     gameSystemRepository;

    @InjectMocks
    private DefaultGameSystemService service;

    public TestGameSystemServiceDelete() {
        super();
    }

    @Test
    @DisplayName("When deleting a game system, the repository is called")
    void testDeleteGameSystem_CallsRepository() {
        // GIVEN
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(true);

        // WHEN
        service.delete(GameSystemConstants.NAME);

        // THEN
        verify(gameSystemRepository).delete(GameSystemConstants.NAME);
    }

    @Test
    @DisplayName("When the game system doesn't exist, an exception is thrown")
    void testDeleteGameSystem_NotExisting_NotRemovesEntity() {
        final ThrowingCallable execution;

        // GIVEN
        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(false);

        // WHEN
        execution = () -> service.delete(GameSystemConstants.NAME);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingGameSystemException.class);
    }

}
