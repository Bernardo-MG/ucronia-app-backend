/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023-2025 the original author or authors.
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

package com.bernardomg.schedule.springframework.task;

import java.time.YearMonth;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.schedule.event.MonthStartEvent;

/**
 * Scheduled task which generates a {@link MonthStartEvent} at the start of month.
 */
public class MonthStartScheduleSpringTask {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(MonthStartScheduleSpringTask.class);

    private final EventEmitter  eventEmitter;

    public MonthStartScheduleSpringTask(final EventEmitter eventEmit) {
        super();

        eventEmitter = Objects.requireNonNull(eventEmit);
    }

    @Async
    @Scheduled(cron = "@monthly")
    public void registerMonthFees() {
        // TODO: What about UTC hour displacement?
        log.info("Notifying new month");
        // TODO: set a source
        eventEmitter.emit(new MonthStartEvent(null, YearMonth.now()));
        log.info("Notified new month");
    }

}
