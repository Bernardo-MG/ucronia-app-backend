
package com.bernardomg.association.calendar.game.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.ValidTable;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTableConstants;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTables;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameTableRepository - findOne")
class ITGameTableRepositoryFindOne {

    @Autowired
    private GameTableRepository repository;

    @Test
    @DisplayName("When there is an existing game table, then it is returned")
    @ValidTable
    void testFindOne() {
        final Optional<GameTable> gameTable;

        // WHEN
        gameTable = repository.findOne(GameTableConstants.NUMBER);

        // THEN
        Assertions.assertThat(gameTable)
            .as("game table")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is an existing game table, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(GameTables.valid()));
    }

    @Test
    @DisplayName("When there is a not existing game table number, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<GameTable> gameTable;

        // WHEN
        gameTable = repository.findOne(GameTableConstants.NUMBER);

        // THEN
        Assertions.assertThat(gameTable)
            .as("game table")
            .isEmpty();
    }

}
