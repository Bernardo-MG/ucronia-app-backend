
package com.bernardomg.association.calendar.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarTypeSpringRepository;
import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypeConstants;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypes;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("CalendarTypeRepository - save")
class ITCalendarTypeRepositorySave {

    @Autowired
    private CalendarTypeRepository       repository;

    @Autowired
    private CalendarTypeSpringRepository springRepository;

    @Test
    @DisplayName("When an existing calendar type is saved, then it is updated")
    void testSave_Existing() {
        final CalendarType calendarType;
        final CalendarType toSave;
        final long         previousCount;

        // GIVEN
        previousCount = springRepository.count();
        toSave = CalendarTypes.nameChange();

        // WHEN
        calendarType = repository.save(toSave);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("calendar type count")
            .isEqualTo(previousCount);
        Assertions.assertThat(calendarType.number())
            .as("number")
            .isEqualTo(CalendarTypeConstants.NUMBER);
        Assertions.assertThat(calendarType.name())
            .as("name")
            .isEqualTo(CalendarTypeConstants.ALTERNATIVE_NAME);
        Assertions.assertThat(calendarType.color())
            .as("color")
            .isEqualTo(CalendarTypeConstants.COLOR);
    }

    @Test
    @DisplayName("When a new calendar type is saved, then it is persisted")
    void testSave_New() {
        final CalendarType calendarType;
        final CalendarType toSave;
        final long         previousCount;

        // GIVEN
        previousCount = springRepository.count();
        toSave = new CalendarType(0, CalendarTypeConstants.ALTERNATIVE_NAME, CalendarTypeConstants.ALTERNATIVE_COLOR);

        // WHEN
        calendarType = repository.save(toSave);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("calendar type count")
            .isGreaterThan(previousCount);
        Assertions.assertThat(calendarType.name())
            .as("name")
            .isEqualTo(CalendarTypeConstants.ALTERNATIVE_NAME);
        Assertions.assertThat(calendarType.color())
            .as("color")
            .isEqualTo(CalendarTypeConstants.ALTERNATIVE_COLOR);
    }

}
