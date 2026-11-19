
package com.bernardomg.association.library.gamesystem.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository.GameSystemSpringRepository;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.test.configuration.data.annotation.ValidGameSystem;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemEntities;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameSystemRepository - save")
class ITGameSystemRepositorySave {

    @Autowired
    private GameSystemRepository       repository;

    @Autowired
    private GameSystemSpringRepository springRepository;

    @Test
    @DisplayName("When an entity is created or updated, then audit timestamps are set and the creation time is preserved")
    void testSave_Audit() {
        final GameSystem created;
        final GameSystem updated;

        // GIVEN
        created = repository.findOne(repository.save(new GameSystem(1L, "Audit original"))
            .number())
            .orElseThrow();

        // WHEN
        updated = repository.save(new GameSystem(created.number(), "Audit changed"));

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(created.audit()
                .createdAt())
                .as("creation timestamp is set")
                .isNotNull();
            softly.assertThat(created.audit()
                .updatedAt())
                .as("update timestamp is set")
                .isNotNull();
            softly.assertThat(updated.audit()
                .createdAt())
                .as("creation timestamp is preserved")
                .isEqualTo(created.audit()
                    .createdAt());
            softly.assertThat(updated.audit()
                .updatedAt())
                .as("update timestamp advances")
                .isAfterOrEqualTo(created.audit()
                    .updatedAt());
            softly.assertThat(repository.findOne(created.number())
                .orElseThrow()
                .audit()
                .createdAt())
                .as("persisted creation timestamp is preserved")
                .isEqualTo(created.audit()
                    .createdAt());
        });
    }

    @Test
    @DisplayName("When the game system name is changed, then it is persisted")
    void testSave_NameChange_Persisted() {
        final GameSystem gameSystem;

        // GIVEN
        gameSystem = GameSystems.nameChange();

        // WHEN
        repository.save(gameSystem);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("game systems")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .contains(GameSystemEntities.nameChange());
    }

    @Test
    @DisplayName("When the game system name is changed, then it is returned")
    @ValidGameSystem
    void testSave_NameChange_Returned() {
        final GameSystem gameSystem;
        final GameSystem created;

        // GIVEN
        gameSystem = GameSystems.nameChange();

        // WHEN
        created = repository.save(gameSystem);

        // THEN
        Assertions.assertThat(created)
            .as("game system")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(GameSystems.nameChange());
    }

    @Test
    @DisplayName("When saving, then a game system is persisted")
    void testSave_Persisted() {
        final GameSystem gameSystem;

        // GIVEN
        gameSystem = GameSystems.valid();

        // WHEN
        repository.save(gameSystem);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("game systems")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .contains(GameSystemEntities.valid());
    }

    @Test
    @DisplayName("When saving, then the persisted game system is returned")
    void testSave_Returned() {
        final GameSystem gameSystem;
        final GameSystem created;

        // GIVEN
        gameSystem = GameSystems.valid();

        // WHEN
        created = repository.save(gameSystem);

        // THEN
        Assertions.assertThat(created)
            .as("game system")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(GameSystems.valid());
    }

}
