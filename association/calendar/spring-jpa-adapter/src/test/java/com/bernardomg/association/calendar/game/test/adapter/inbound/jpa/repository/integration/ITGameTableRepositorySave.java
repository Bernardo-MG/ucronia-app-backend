
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
    @DisplayName("When changing the title, then the data is persisted")
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
    @DisplayName("When changing the title, then the data is returned")
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
    @DisplayName("When creating a table, then it is persisted")
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
    @DisplayName("When creating a table, then it is returned")
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
