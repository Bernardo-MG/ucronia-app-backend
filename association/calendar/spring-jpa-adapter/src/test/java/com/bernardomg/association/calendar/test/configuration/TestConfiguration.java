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

package com.bernardomg.association.calendar.test.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bernardomg.association.calendar.activity.adapter.inbound.jpa.repository.JpaActivityRepository;
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarDateSpringRepository;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarInfoSpringRepository;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarStatusSpringRepository;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarTypeSpringRepository;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.JpaCalendarTypeRepository;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.RecurrenceStatusSpringRepository;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.repository.JpaScheduledGameRepository;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.repository.ScheduledGameProfileSpringRepository;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.repository.ScheduledGameSpringRepository;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;

@Configuration
@EnableJpaRepositories(basePackages = { "com.bernardomg.association.calendar.adapter.inbound.jpa",
        "com.bernardomg.association.calendar.game.adapter.inbound.jpa" })
@EntityScan(basePackages = { "com.bernardomg.association.calendar.adapter.inbound.jpa",
        "com.bernardomg.association.calendar.game.adapter.inbound.jpa" })
public class TestConfiguration {

    @Bean("activityRepository")
    public ActivityRepository getActivityRepository(final CalendarInfoSpringRepository calendarInfoSpringRepository,
            final CalendarDateSpringRepository calendarDateSpringRepository,
            final CalendarTypeSpringRepository calendarTypeSpringRepository,
            final CalendarStatusSpringRepository calendarStatusSpringRepository) {
        return new JpaActivityRepository(calendarInfoSpringRepository, calendarDateSpringRepository,
            calendarTypeSpringRepository, calendarStatusSpringRepository);
    }

    @Bean("calendarTypeRepository")
    public CalendarTypeRepository
            getCalendarTypeRepository(final CalendarTypeSpringRepository calendarTypeSpringRepository) {
        return new JpaCalendarTypeRepository(calendarTypeSpringRepository);
    }

    @Bean("scheduledGameRepository")
    public ScheduledGameRepository getScheduledGameRepository(
            final ScheduledGameProfileSpringRepository scheduledGameProfileSpringRepository,
            final ScheduledGameSpringRepository scheduledGameSpringRepository,
            final CalendarTypeSpringRepository calendarTypeSpringRepository,
            final CalendarStatusSpringRepository calendarStatusSpringRepository,
            final RecurrenceStatusSpringRepository recurrenceStatusSpringRepository) {
        return new JpaScheduledGameRepository(scheduledGameSpringRepository, scheduledGameProfileSpringRepository,
            calendarTypeSpringRepository, calendarStatusSpringRepository, recurrenceStatusSpringRepository);
    }

}
