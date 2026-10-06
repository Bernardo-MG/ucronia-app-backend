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

package com.bernardomg.association.fee.adapter.inbound.event;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.time.YearMonth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.fee.usecase.service.FeeMaintenanceService;
import com.bernardomg.schedule.event.MonthStartEvent;

class TestRegisterFeesOnMonthStartEventListener {

    private RegisterFeesOnMonthStartEventListener listener;

    private FeeMaintenanceService                 service;

    @BeforeEach
    void setUp() {
        service = mock(FeeMaintenanceService.class);
        listener = new RegisterFeesOnMonthStartEventListener(service);
    }

    @Test
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
