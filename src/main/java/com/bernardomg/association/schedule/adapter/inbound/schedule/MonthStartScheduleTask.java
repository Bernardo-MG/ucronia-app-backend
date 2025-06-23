
package com.bernardomg.association.schedule.adapter.inbound.schedule;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bernardomg.association.schedule.usecase.service.ScheduleService;

@Component
public class MonthStartScheduleTask {

    /**
     * Logger for the class.
     */
    private static final Logger   log = LoggerFactory.getLogger(MonthStartScheduleTask.class);

    private final ScheduleService service;

    public MonthStartScheduleTask(final ScheduleService scheduleService) {
        super();

        service = Objects.requireNonNull(scheduleService);
    }

    @Async
    @Scheduled(cron = "@monthly")
    public void registerMonthFees() {
        log.info("Notifying new month");
        service.monthStarts();
        log.info("Notified new month");
    }

}
