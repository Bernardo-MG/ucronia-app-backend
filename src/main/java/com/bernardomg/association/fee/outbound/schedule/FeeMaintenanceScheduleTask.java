
package com.bernardomg.association.fee.outbound.schedule;

import java.util.Objects;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import com.bernardomg.association.fee.usecase.FeeMaintenanceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeeMaintenanceScheduleTask {

    private final FeeMaintenanceService service;

    public FeeMaintenanceScheduleTask(final FeeMaintenanceService feeMaintenanceService) {
        super();

        service = Objects.requireNonNull(feeMaintenanceService);
    }

    @Async
    @Scheduled(cron = "@monthly")
    public void registerMonthFees() {
        log.info("Starting current month fee registering");
        service.registerMonthFees();
        log.info("Finished current month fee registering");
    }

}
