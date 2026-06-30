
package com.bernardomg.association.calendar.game.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.ScheduledGameEntity;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.repository.ScheduledGameSpringRepository;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.WeeklyScheduledGame;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.association.calendar.game.test.factory.ScheduledGameEntities;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ScheduledGameRepository - save")
class ITScheduledGameRepositorySave {

    @Autowired
    private ScheduledGameRepository       repository;

    @Autowired
    private ScheduledGameSpringRepository springRepository;

    @Test
    @DisplayName("Persists the data")
    @ValidProfile
    void testSave_PersistedData() {
        final Iterable<ScheduledGameEntity> scheduledGames;
        final ScheduledGame                 scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.weekly();

        // WHEN
        repository.save(scheduledGame);

        // THEN
        scheduledGames = springRepository.findAll();

        Assertions.assertThat(scheduledGames)
            .as("scheduled games")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "master.id", "master.number")
            .containsExactly(ScheduledGameEntities.weekly());
    }

    @Test
    @DisplayName("Returns the created data")
    @ValidProfile
    void testSave_ReturnedData() {
        final ScheduledGame created;
        final ScheduledGame scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.weekly();

        // WHEN
        created = repository.save(scheduledGame);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(ScheduledGames.weekly());
    }

    @Test
    @DisplayName("When changing the title, the data is persisted")
    @WeeklyScheduledGame
    void testSave_TitleChange_PersistedData() {
        final Iterable<ScheduledGameEntity> scheduledGames;
        final ScheduledGame                 scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.titleChange();

        // WHEN
        repository.save(scheduledGame);

        // THEN
        scheduledGames = springRepository.findAll();

        Assertions.assertThat(scheduledGames)
            .as("scheduled games")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number", "master.id", "master.number")
            .containsExactly(ScheduledGameEntities.titleChange());
    }

    @Test
    @DisplayName("When changing the title, the data is returned")
    @WeeklyScheduledGame
    void testSave_TitleChange_ReturnedData() {
        final ScheduledGame created;
        final ScheduledGame scheduledGame;

        // GIVEN
        scheduledGame = ScheduledGames.titleChange();

        // WHEN
        created = repository.save(scheduledGame);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(ScheduledGames.titleChange());
    }

}
