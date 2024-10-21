
package com.bernardomg.association.schedule.adapter.inbound.schedule;

import java.util.Objects;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bernardomg.association.schedule.usecase.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MonthStartScheduleTask {

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
