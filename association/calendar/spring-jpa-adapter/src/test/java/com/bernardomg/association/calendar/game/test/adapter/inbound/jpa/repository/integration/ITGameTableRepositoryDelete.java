
package com.bernardomg.association.calendar.game.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.GameTableSpringRepository;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.ValidTable;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTableConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameTableRepository - delete")
class ITGameTableRepositoryDelete {

    @Autowired
    private GameTableRepository       repository;

    @Autowired
    private GameTableSpringRepository springRepository;

    @Test
    @DisplayName("When the game table exists, then it is deleted")
    @ValidTable
    void testDelete() {
        final long count;

        // GIVEN
        count = springRepository.count();

        // WHEN
        repository.delete(GameTableConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("game tables")
            .isLessThan(count);
    }

    @Test
    @DisplayName("When the game table doesn't exist, then nothing is removed")
    void testDelete_NotExisting() {
        final long count;

        // GIVEN
        count = springRepository.count();

        // WHEN
        repository.delete(-1);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("game tables")
            .isEqualTo(count);
    }

}
