
package com.bernardomg.association.calendar.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypeConstants;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypes;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("CalendarTypeRepository - findOne")
class ITCalendarTypeRepositoryFindOne {

    @Autowired
    private CalendarTypeRepository repository;

    @Test
    @DisplayName("When there is an existing calendar type, then it is returned")
    void testFindOne() {
        final Optional<CalendarType> calendarType;

        // WHEN
        calendarType = repository.findOne(CalendarTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(calendarType)
            .as("calendar type")
            .contains(CalendarTypes.activity());
    }

    @Test
    @DisplayName("When there is a not existing calendar type number, then nothing is returned")
    void testFindOne_NotFound() {
        final Optional<CalendarType> calendarType;

        // WHEN
        calendarType = repository.findOne(-1L);

        // THEN
        Assertions.assertThat(calendarType)
            .as("calendar type")
            .isEmpty();
    }

}
