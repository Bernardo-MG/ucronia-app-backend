
package com.bernardomg.association.fee.adapter.inbound.event;

import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.time.YearMonth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.usecase.service.FeeMaintenanceService;
import com.bernardomg.schedule.event.MonthStartEvent;

@DisplayName("RegisterFeesOnMonthStartEventListener - handle")
@ExtendWith(MockitoExtension.class)
class TestRegisterFeesOnMonthStartEventListener {

    @InjectMocks
    private RegisterFeesOnMonthStartEventListener listener;

    @Mock
    private FeeMaintenanceService                 service;

    @Test
    @DisplayName("When a month-start event is handled, then fees are registered for that month")
    void testHandle() {
        final MonthStartEvent event;

        // GIVEN
        event = new MonthStartEvent("com.bernardomg.ucronia", YearMonth.of(2026, 10));

        // WHEN
        listener.handle(event);

        // THEN
        verify(service).registerMonthFees(Instant.parse("2026-10-01T00:00:00Z"));
    }

}
