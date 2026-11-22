
package com.bernardomg.association.calendar.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarTypeSpringRepository;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.WeeklyScheduledGame;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("CalendarTypeRepository - delete")
class ITCalendarTypeRepositoryDelete {

    @Autowired
    private CalendarTypeRepository       repository;

    @Autowired
    private CalendarTypeSpringRepository springRepository;

    @Test
    @DisplayName("When the calendar type doesn't exist, then nothing is removed")
    void testDelete_NotExisting() {
        final long count;

        // GIVEN
        count = springRepository.count();

        // WHEN
        repository.delete(-1);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("activities")
            .isEqualTo(count);
    }

    @Test
    @DisplayName("When the calendar type exists, then it is deleted")
    @WeeklyScheduledGame
    void testDelete_RemovesEntity() {
        final long count;

        // GIVEN
        count = springRepository.count();

        // WHEN
        repository.delete(ActivityConstants.CALENDAR_TYPE_NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("activities")
            .isLessThan(count);
    }

}
