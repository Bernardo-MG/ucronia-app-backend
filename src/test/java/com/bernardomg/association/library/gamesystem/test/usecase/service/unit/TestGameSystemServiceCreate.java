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

package com.bernardomg.association.library.gamesystem.test.usecase.service.unit;

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

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.test.config.factory.GameSystemConstants;
import com.bernardomg.association.library.gamesystem.test.config.factory.GameSystems;
import com.bernardomg.association.library.gamesystem.usecase.service.DefaultGameSystemService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("GameSystemService - create")
class TestGameSystemServiceCreate {

    @Mock
    private GameSystemRepository     gameSystemRepository;

    @InjectMocks
    private DefaultGameSystemService service;

    public TestGameSystemServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a game system with an empty name, an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable execution;
        final GameSystem       gameSystem;

        // GIVEN
        gameSystem = GameSystems.emptyName();

        // WHEN
        execution = () -> service.create(gameSystem);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("name", "empty", " "));
    }

    @Test
    @DisplayName("With a game system with an existing name, an exception is thrown")
    void testCreate_ExistingName() {
        final ThrowingCallable execution;
        final GameSystem       gameSystem;

        // GIVEN
        gameSystem = GameSystems.valid();

        given(gameSystemRepository.exists(GameSystemConstants.NAME)).willReturn(true);

        // WHEN
        execution = () -> service.create(gameSystem);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            FieldFailure.of("name", "existing", GameSystemConstants.NAME));
    }

    @Test
    @DisplayName("With a valid game system, the game system is persisted")
    void testCreate_PersistedData() {
        final GameSystem gameSystem;

        // GIVEN
        gameSystem = GameSystems.valid();

        // WHEN
        service.create(gameSystem);

        // THEN
        verify(gameSystemRepository).save(GameSystems.valid());
    }

    @Test
    @DisplayName("With a valid game system, the created game system is returned")
    void testCreate_ReturnedData() {
        final GameSystem gameSystem;
        final GameSystem created;

        // GIVEN
        gameSystem = GameSystems.valid();

        given(gameSystemRepository.save(GameSystems.valid())).willReturn(GameSystems.valid());

        // WHEN
        created = service.create(gameSystem);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(GameSystems.valid());
    }

}
