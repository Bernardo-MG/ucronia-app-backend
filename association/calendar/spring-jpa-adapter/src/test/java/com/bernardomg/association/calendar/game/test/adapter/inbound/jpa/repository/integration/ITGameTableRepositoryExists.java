
package com.bernardomg.association.calendar.game.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.ValidTable;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTableConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameTableRepository - exists")
class ITGameTableRepositoryExists {

    @Autowired
    private GameTableRepository repository;

    @Test
    @DisplayName("When there is an existing game table, then it exists")
    @ValidTable
    void testExists() {
        final boolean exists;

        // WHEN
        exists = repository.exists(GameTableConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is a not existing game table, then nothing exists")
    void testExists_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.exists(GameTableConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
