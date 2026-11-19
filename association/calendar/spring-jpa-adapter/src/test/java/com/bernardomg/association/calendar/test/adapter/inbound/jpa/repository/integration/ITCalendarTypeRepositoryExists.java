
package com.bernardomg.association.calendar.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("CalendarTypeRepository - exists")
class ITCalendarTypeRepositoryExists {

    @Autowired
    private CalendarTypeRepository repository;

    @Test
    @DisplayName("When there is an existing calendar type, then it exists")
    void testExists() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ActivityConstants.CALENDAR_TYPE_NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is a not existing calendar type, then nothing exists")
    void testExists_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.exists(-1);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
