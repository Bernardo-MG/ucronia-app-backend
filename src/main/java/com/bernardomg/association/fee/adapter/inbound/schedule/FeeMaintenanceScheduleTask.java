
package com.bernardomg.association.fee.adapter.inbound.schedule;

import java.util.Objects;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bernardomg.association.fee.usecase.service.FeeMaintenanceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
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
