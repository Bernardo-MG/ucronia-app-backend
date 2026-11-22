
package com.bernardomg.association.calendar.activity.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.calendar.activity.test.configuration.data.annotation.SingleDayActivity;
import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarInfoSpringRepository;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("ActivityRepository - delete")
class ITActivityRepositoryDelete {

    @Autowired
    private ActivityRepository           repository;

    @Autowired
    private CalendarInfoSpringRepository springRepository;

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
    @DisplayName("When the activity exists, then it is removed")
    @SingleDayActivity
    void testDelete_RemovesEntity() {
        // WHEN
        repository.delete(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("activities")
            .isZero();
    }

}
