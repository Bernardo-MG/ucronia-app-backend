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

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.GameTableSpringRepository;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.GameTableEntity;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.ValidTable;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTables;
import com.bernardomg.association.calendar.game.test.factory.GameTableEntities;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameTableRepository - save")
class ITGameTableRepositorySave {

    @Autowired
    private GameTableRepository       repository;

    @Autowired
    private GameTableSpringRepository springRepository;

    @Test
    @DisplayName("When changing the title, the data is persisted")
    @ValidTable
    void testSave_Existing_PersistedData() {
        final GameTable             toSave;
        final List<GameTableEntity> gameTables;

        // GIVEN
        toSave = GameTables.nameChange();

        // WHEN
        repository.save(toSave);

        // THEN
        gameTables = springRepository.findAll();

        Assertions.assertThat(gameTables)
            .as("game tables")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(GameTableEntities.nameChange());
    }

    @Test
    @DisplayName("When changing the title, the data is returned")
    @ValidTable
    void testSave_Existing_ReturnedData() {
        final GameTable created;
        final GameTable toSave;

        // GIVEN
        toSave = GameTables.nameChange();

        // WHEN
        created = repository.save(toSave);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(GameTables.nameChange());
    }

    @Test
    @DisplayName("When creating a table, it is persisted")
    void testSave_PersistedData() {
        final GameTable             toSave;
        final List<GameTableEntity> gameTables;

        // GIVEN
        toSave = GameTables.valid();

        // WHEN
        repository.save(toSave);

        // THEN
        gameTables = springRepository.findAll();

        Assertions.assertThat(gameTables)
            .as("game tables")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(GameTableEntities.valid());
    }

    @Test
    @DisplayName("When creating a table, it is returned")
    void testSave_ReturnedData() {
        final GameTable created;
        final GameTable toSave;

        // GIVEN
        toSave = GameTables.valid();

        // WHEN
        created = repository.save(toSave);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(GameTables.valid());
    }

}
