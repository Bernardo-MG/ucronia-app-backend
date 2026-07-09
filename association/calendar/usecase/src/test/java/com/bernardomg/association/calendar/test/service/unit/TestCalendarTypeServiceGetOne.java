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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.calendar.domain.exception.MissingCalendarTypeException;
import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypeConstants;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypes;
import com.bernardomg.association.calendar.usecase.service.DefaultCalendarTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultCalendarTypeService - get one")
class TestCalendarTypeServiceGetOne {

    @Mock
    private CalendarTypeRepository     calendarTypeRepository;

    @InjectMocks
    private DefaultCalendarTypeService service;

    public TestCalendarTypeServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("When there is data it is returned")
    void testGetOne() {
        final Optional<CalendarType> calendarType;

        // GIVEN
        given(calendarTypeRepository.findOne(CalendarTypeConstants.NUMBER))
            .willReturn(Optional.of(CalendarTypes.activity()));

        // WHEN
        calendarType = service.getOne(CalendarTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(calendarType)
            .as("calendar type")
            .contains(CalendarTypes.activity());
    }

    @Test
    @DisplayName("When there is no data an exception is thrown")
    void testGetOne_NoData() {
        final ThrowingCallable execution;

        // GIVEN
        given(calendarTypeRepository.findOne(CalendarTypeConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(CalendarTypeConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingCalendarTypeException.class);
    }

}
