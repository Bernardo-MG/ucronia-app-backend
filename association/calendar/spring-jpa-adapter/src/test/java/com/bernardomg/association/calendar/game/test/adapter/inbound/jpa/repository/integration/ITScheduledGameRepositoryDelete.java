
package com.bernardomg.association.calendar.game.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.repository.ScheduledGameSpringRepository;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.WeeklyScheduledGame;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ScheduledGameRepository - delete")
class ITScheduledGameRepositoryDelete {

    @Autowired
    private ScheduledGameRepository       repository;

    @Autowired
    private ScheduledGameSpringRepository springRepository;

    @Test
    @DisplayName("When there is no data, then nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("activities")
            .isZero();
    }

    @Test
    @DisplayName("When the scheduled game exists, then it is removed")
    @WeeklyScheduledGame
    void testDelete_RemovesEntity() {
        // WHEN
        repository.delete(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("activities")
            .isZero();
    }

}
