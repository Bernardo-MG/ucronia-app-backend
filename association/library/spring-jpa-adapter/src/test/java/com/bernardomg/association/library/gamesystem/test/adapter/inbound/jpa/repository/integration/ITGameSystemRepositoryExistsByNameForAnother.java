
package com.bernardomg.association.library.gamesystem.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.test.configuration.data.annotation.ValidGameSystem;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameSystemRepository - existsByNameForAnother")
class ITGameSystemRepositoryExistsByNameForAnother {

    @Autowired
    private GameSystemRepository repository;

    @Test
    @DisplayName("When another game system with the same name is provided, then it exists")
    @ValidGameSystem
    void testExistsByNameForAnother_Another() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameForAnother(GameSystemConstants.NAME, -1L);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When only the edited game system is provided, then nothing exists")
    @ValidGameSystem
    void testExistsByNameForAnother_Itself() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameForAnother(GameSystemConstants.NAME, GameSystemConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is no data, then nothing exists")
    void testExistsByNameForAnother_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameForAnother(GameSystemConstants.NAME, GameSystemConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
