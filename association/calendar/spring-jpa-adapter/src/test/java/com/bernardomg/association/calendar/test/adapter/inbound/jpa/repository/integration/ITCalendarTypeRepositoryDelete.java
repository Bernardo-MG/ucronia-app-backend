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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.calendar.TestApplication;
import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarTypeSpringRepository;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.association.calendar.game.test.configuration.data.annotation.WeeklyScheduledGame;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("CalendarTypeRepository - delete")
class ITCalendarTypeRepositoryDelete {

    @Autowired
    private CalendarTypeRepository       repository;

    @Autowired
    private CalendarTypeSpringRepository springRepository;

    public ITCalendarTypeRepositoryDelete() {
        super();
    }

    @Test
    @DisplayName("When the calendar type doesn't exist, nothing is removed")
    void testDelete_NotExisting() {
        final long count;

        // GIVEN
        count = springRepository.count();

        // WHEN
        repository.delete(-1);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("activities")
            .isEqualTo(count);
    }

    @Test
    @DisplayName("When the calendar type exists, it is deleted")
    @WeeklyScheduledGame
    void testDelete_RemovesEntity() {
        final long count;

        // GIVEN
        count = springRepository.count();

        // WHEN
        repository.delete(ActivityConstants.CALENDAR_TYPE_NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("activities")
            .isLessThan(count);
    }

}
