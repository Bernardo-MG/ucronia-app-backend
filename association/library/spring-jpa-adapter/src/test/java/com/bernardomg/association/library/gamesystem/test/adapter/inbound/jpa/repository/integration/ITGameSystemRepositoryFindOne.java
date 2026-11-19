
package com.bernardomg.association.library.gamesystem.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.test.configuration.data.annotation.ValidGameSystem;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemConstants;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameSystemRepository - findOne")
class ITGameSystemRepositoryFindOne {

    @Autowired
    private GameSystemRepository repository;

    @Test
    @DisplayName("When there is a game system, then it is returned")
    @ValidGameSystem
    void testFindOne() {
        final Optional<GameSystem> gameSystem;

        // WHEN
        gameSystem = repository.findOne(GameSystemConstants.NUMBER);

        // THEN
        Assertions.assertThat(gameSystem)
            .as("game system")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a game system, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(GameSystems.valid()));
    }

    @Test
    @DisplayName("When there is no game system, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<GameSystem> gameSystem;

        // WHEN
        gameSystem = repository.findOne(GameSystemConstants.NUMBER);

        // THEN
        Assertions.assertThat(gameSystem)
            .as("game system")
            .isEmpty();
    }

}
