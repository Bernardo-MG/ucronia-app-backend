
package com.bernardomg.association.fee.schedule;

import java.util.Objects;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import com.bernardomg.association.fee.service.FeeMaintenanceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeeMaintenanceScheduleTask {

    private final FeeMaintenanceService service;

    public FeeMaintenanceScheduleTask(final FeeMaintenanceService feeMaintenanceService) {
        super();

        service = Objects.requireNonNull(feeMaintenanceService);
    }

    @Async
    @Scheduled(cron = "0 0 1 * *")
    public void registerMonthFees() {
        log.info("Starting current month fee registering");
        service.registerMonthFees();
        log.info("Finished current month fee registering");
    }

}
