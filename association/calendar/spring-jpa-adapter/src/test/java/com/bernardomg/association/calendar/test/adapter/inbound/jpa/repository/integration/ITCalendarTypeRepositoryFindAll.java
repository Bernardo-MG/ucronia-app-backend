
package com.bernardomg.association.calendar.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypes;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("CalendarTypeRepository - findAll")
class ITCalendarTypeRepositoryFindAll {

    @Autowired
    private CalendarTypeRepository repository;

    @Test
    @DisplayName("When the default calendar types is provided, then all are returned")
    void testFindAll() {
        final Page<CalendarType> calendarTypes;
        final Pagination         pagination;
        final Sorting            sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        calendarTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(calendarTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("calendar types")
            .contains(CalendarTypes.activity());
    }

    @Test
    @DisplayName("When the default calendar types are loaded, then they have the expected count")
    void testFindAll_DefaultCount() {
        final Page<CalendarType> calendarTypes;
        final Pagination         pagination;
        final Sorting            sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        calendarTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(calendarTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("calendar types count")
            .hasSize(3);
    }

}
