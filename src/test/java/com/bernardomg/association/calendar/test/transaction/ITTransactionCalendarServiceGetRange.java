/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.calendar.test.transaction;

import java.time.Month;
import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.calendar.transaction.model.TransactionRange;
import com.bernardomg.association.calendar.transaction.service.TransactionCalendarService;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Transaction calendar service - get range")
class ITTransactionCalendarServiceGetRange {

    @Autowired
    private TransactionCalendarService service;

    public ITTransactionCalendarServiceGetRange() {
        super();
    }

    @Test
    @DisplayName("With a full year, a range for the full year is returned")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    void testGetRange_FullYear() {
        final TransactionRange range;

        range = service.getRange();

        Assertions.assertThat(range.getMonths())
            .hasSize(12)
            .containsExactly(YearMonth.of(2020, Month.JANUARY), YearMonth.of(2020, Month.FEBRUARY),
                YearMonth.of(2020, Month.MARCH), YearMonth.of(2020, Month.APRIL), YearMonth.of(2020, Month.MAY),
                YearMonth.of(2020, Month.JUNE), YearMonth.of(2020, Month.JULY), YearMonth.of(2020, Month.AUGUST),
                YearMonth.of(2020, Month.SEPTEMBER), YearMonth.of(2020, Month.OCTOBER),
                YearMonth.of(2020, Month.NOVEMBER), YearMonth.of(2020, Month.DECEMBER));
    }

    @Test
    @DisplayName("With no data, an empty range is returned")
    void testGetRange_NoData() {
        final TransactionRange range;

        range = service.getRange();

        Assertions.assertThat(range.getMonths())
            .hasSize(0);
    }

}
