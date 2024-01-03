/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.funds.test.calendar.service.integration;

import java.time.Month;
import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.funds.calendar.model.CalendarFundsDate;
import com.bernardomg.association.funds.calendar.service.FundsCalendarService;
import com.bernardomg.association.funds.test.calendar.config.factory.CalendarFundsDates;
import com.bernardomg.association.funds.test.transaction.configuration.FullTransactionYear;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Funds calendar service - get year month")
class ITFundsCalendarServiceGetYearMonth {

    @Autowired
    private FundsCalendarService service;

    public ITFundsCalendarServiceGetYearMonth() {
        super();
    }

    @Test
    @DisplayName("Only the data for the month is returned")
    @FullTransactionYear
    void testGetRange_FullYear() {
        final YearMonth                   month;
        final Iterable<CalendarFundsDate> dates;

        // GIVEN
        month = YearMonth.of(2020, Month.FEBRUARY);

        // WHEN
        dates = service.getYearMonth(month);

        // THEN
        Assertions.assertThat(dates)
            .as("dates")
            .containsExactly(CalendarFundsDates.february());
    }

    @Test
    @DisplayName("Reading for a not existing month returns nothing")
    @FullTransactionYear
    void testGetRange_FullYear_NotExisting() {
        final YearMonth                             month;
        final Iterable<? extends CalendarFundsDate> dates;

        // GIVEN
        month = YearMonth.of(2019, Month.DECEMBER);

        // WHEN
        dates = service.getYearMonth(month);

        // THEN
        Assertions.assertThat(dates)
            .as("dates")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testGetRange_NoData() {
        final YearMonth                             month;
        final Iterable<? extends CalendarFundsDate> dates;

        // GIVEN
        month = YearMonth.of(2020, Month.FEBRUARY);

        // WHEN
        dates = service.getYearMonth(month);

        // THEN
        Assertions.assertThat(dates)
            .as("dates")
            .isEmpty();
    }

}
