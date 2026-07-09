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
@DisplayName("CalendarTypeRepository - find one")
class ITCalendarTypeRepositoryFindOne {

    @Autowired
    private CalendarTypeRepository repository;

    public ITCalendarTypeRepositoryFindOne() {
        super();
    }

    @Test
    @DisplayName("With an existing calendar type, it is returned")
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
    @DisplayName("With a not existing calendar type number, nothing is returned")
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
