
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
@DisplayName("GameSystemRepository - existsByName")
class ITGameSystemRepositoryExistsByName {

    @Autowired
    private GameSystemRepository repository;

    @Test
    @DisplayName("When there is an author, then it existByName")
    @ValidGameSystem
    void testExistsByName() {
        final boolean existByName;

        // WHEN
        existByName = repository.existsByName(GameSystemConstants.NAME);

        // THEN
        Assertions.assertThat(existByName)
            .as("existByName")
            .isTrue();
    }

    @Test
    @DisplayName("When there is no data, then nothing existByName")
    void testExistsByName_NoData() {
        final boolean existByName;

        // WHEN
        existByName = repository.existsByName(GameSystemConstants.NAME);

        // THEN
        Assertions.assertThat(existByName)
            .as("existByName")
            .isFalse();
    }

}
