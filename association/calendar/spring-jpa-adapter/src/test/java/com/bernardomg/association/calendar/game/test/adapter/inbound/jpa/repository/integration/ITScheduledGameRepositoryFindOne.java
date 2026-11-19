
package com.bernardomg.association.calendar.game.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.ScheduledGameWithTable;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.WeeklyScheduledGame;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ScheduledGameRepository - findOne")
class ITScheduledGameRepositoryFindOne {

    @Autowired
    private ScheduledGameRepository repository;

    @Test
    @DisplayName("When there is an existing scheduled game, then it is returned")
    @WeeklyScheduledGame
    void testFindOne() {
        final Optional<ScheduledGame> scheduledGame;

        // WHEN
        scheduledGame = repository.findOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(scheduledGame)
            .as("scheduled game")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is an existing scheduled game, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(ScheduledGames.weeklyOneshot()));
    }

    @Test
    @DisplayName("When there is no scheduled game, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<ScheduledGame> scheduledGame;

        // WHEN
        scheduledGame = repository.findOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(scheduledGame)
            .as("When there is no scheduled game, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is an existing scheduled game with a table, then it is returned")
    @ScheduledGameWithTable
    void testFindOne_WithTable() {
        final Optional<ScheduledGame> scheduledGame;

        // WHEN
        scheduledGame = repository.findOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(scheduledGame)
            .as("scheduled game")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is an existing scheduled game with a table, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(ScheduledGames.withTable()));
    }

}
