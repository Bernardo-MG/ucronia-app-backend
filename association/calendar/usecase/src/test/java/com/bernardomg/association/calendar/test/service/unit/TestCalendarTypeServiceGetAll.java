/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
@DisplayName("DefaultCalendarTypeService - get all")
class TestCalendarTypeServiceGetAll {

    @Mock
    private CalendarTypeRepository     calendarTypeRepository;

    @InjectMocks
    private DefaultCalendarTypeService service;

    public TestCalendarTypeServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("When there is data it is returned")
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
    @DisplayName("When there is no data nothing is returned")
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
