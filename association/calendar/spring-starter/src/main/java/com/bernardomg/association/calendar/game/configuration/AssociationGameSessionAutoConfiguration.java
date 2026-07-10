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

package com.bernardomg.association.calendar.game.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarStatusSpringRepository;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarTypeSpringRepository;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.repository.JpaScheduledGameRepository;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.repository.ScheduledGameProfileSpringRepository;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.repository.ScheduledGameSpringRepository;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.usecase.service.DefaultScheduledGameService;
import com.bernardomg.association.calendar.game.usecase.service.ScheduledGameService;

@AutoConfiguration
@ComponentScan({ "com.bernardomg.association.calendar.game.adapter.outbound.rest.controller" })
public class AssociationGameSessionAutoConfiguration {

    @Bean("scheduledGameRepository")
    public ScheduledGameRepository getScheduledGameRepository(
            final ScheduledGameProfileSpringRepository scheduledGameProfileSpringRepository,
            final ScheduledGameSpringRepository scheduledGameSpringRepository,
            final CalendarTypeSpringRepository calendarTypeSpringRepository,
            final CalendarStatusSpringRepository calendarStatusSpringRepository) {
        return new JpaScheduledGameRepository(scheduledGameSpringRepository, scheduledGameProfileSpringRepository,
            calendarTypeSpringRepository, calendarStatusSpringRepository);
    }

    @Bean("scheduledGameService")
    public ScheduledGameService getScheduledGameService(final ScheduledGameRepository scheduledGameRepository) {
        return new DefaultScheduledGameService(scheduledGameRepository);
    }

}
