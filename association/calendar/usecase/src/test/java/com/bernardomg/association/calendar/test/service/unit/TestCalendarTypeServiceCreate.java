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

import org.assertj.core.api.Assertions;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultCalendarTypeService - create")
class TestCalendarTypeServiceCreate {

    @Mock
    private CalendarTypeRepository     calendarTypeRepository;

    @InjectMocks
    private DefaultCalendarTypeService service;

    public TestCalendarTypeServiceCreate() {
        super();
    }

    @Test
    @DisplayName("When creating a calendar type, the persisted data is returned")
    void testCreate() {
        final CalendarType calendarType;
        final CalendarType toCreate;

        // GIVEN
        toCreate = CalendarTypes.activity();
        given(calendarTypeRepository.save(toCreate)).willReturn(CalendarTypes.activity());

        // WHEN
        calendarType = service.create(toCreate);

        // THEN
        Assertions.assertThat(calendarType)
            .as("calendar type")
            .isEqualTo(CalendarTypes.activity());
    }

}
