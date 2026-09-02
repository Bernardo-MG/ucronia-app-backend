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

package com.bernardomg.association.calendar.game.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.GameTableSpringRepository;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.ValidTable;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTableConstants;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTables;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@ValidTable
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameTableRepository - save")
class ITGameTableRepositorySave {

    @Autowired
    private GameTableRepository       repository;

    @Autowired
    private GameTableSpringRepository springRepository;

    public ITGameTableRepositorySave() {
        super();
    }

    @Test
    @DisplayName("Saving an existing game table updates it")
    void testSave_Existing() {
        final GameTable gameTable;
        final GameTable toSave;
        final long      previousCount;

        // GIVEN
        previousCount = springRepository.count();
        toSave = GameTables.nameChange();

        // WHEN
        gameTable = repository.save(toSave);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("game table count")
            .isEqualTo(previousCount);
        Assertions.assertThat(gameTable.number())
            .as("number")
            .isEqualTo(GameTableConstants.NUMBER);
        Assertions.assertThat(gameTable.name())
            .as("name")
            .isEqualTo(GameTableConstants.ALTERNATIVE_NAME);
        Assertions.assertThat(gameTable.description())
            .as("description")
            .isEqualTo(GameTableConstants.DESCRIPTION);
    }

    @Test
    @DisplayName("Saving a new game table persists it")
    void testSave_New() {
        final GameTable gameTable;
        final GameTable toSave;
        final long      previousCount;

        // GIVEN
        previousCount = springRepository.count();
        toSave = new GameTable(0, GameTableConstants.ALTERNATIVE_NAME, GameTableConstants.ALTERNATIVE_DESCRIPTION);

        // WHEN
        gameTable = repository.save(toSave);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("game table count")
            .isGreaterThan(previousCount);
        Assertions.assertThat(gameTable.name())
            .as("name")
            .isEqualTo(GameTableConstants.ALTERNATIVE_NAME);
        Assertions.assertThat(gameTable.description())
            .as("description")
            .isEqualTo(GameTableConstants.ALTERNATIVE_DESCRIPTION);
    }

}
