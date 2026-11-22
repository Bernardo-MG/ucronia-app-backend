
package com.bernardomg.association.calendar.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypes;
import com.bernardomg.association.calendar.usecase.service.DefaultCalendarTypeService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultCalendarTypeService - getAll")
class TestCalendarTypeServiceGetAll {

    @Mock
    private CalendarTypeRepository     calendarTypeRepository;

    @InjectMocks
    private DefaultCalendarTypeService service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetAll() {
        final Page<CalendarType> calendarTypes;
        final Page<CalendarType> existing;
        final Pagination         pagination;
        final Sorting            sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(CalendarTypes.activity()), 0, 0, 0, 0, 0, false, false, sorting);
        given(calendarTypeRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        calendarTypes = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(calendarTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("calendar types")
            .containsExactly(CalendarTypes.activity());
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testGetAll_NoData() {
        final Page<CalendarType> calendarTypes;
        final Page<CalendarType> existing;
        final Pagination         pagination;
        final Sorting            sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(calendarTypeRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        calendarTypes = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(calendarTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("calendar types")
            .isEmpty();
    }

}
