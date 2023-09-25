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

package com.bernardomg.association.funds.test.calendar;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.funds.calendar.service.TransactionCalendarService;
import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Transaction calendar service - get range")
class ITTransactionCalendarServiceGetYearMonth {

    @Autowired
    private TransactionCalendarService service;

    public ITTransactionCalendarServiceGetYearMonth() {
        super();
    }

    @Test
    @DisplayName("Only the data for the month is returned")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    void testGetRange_FullYear() {
        final YearMonth                       date;
        final Iterable<? extends Transaction> data;
        final Transaction                     transaction;

        date = YearMonth.of(2020, Month.FEBRUARY);
        data = service.getYearMonth(date);

        Assertions.assertThat(data)
            .hasSize(1);

        transaction = data.iterator()
            .next();
        Assertions.assertThat(transaction.getDate())
            .isEqualTo(LocalDate.of(2020, Month.FEBRUARY, 1));
        Assertions.assertThat(transaction.getDescription())
            .isEqualTo("Transaction 2");
        Assertions.assertThat(transaction.getAmount())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Reading for a not existing month returns nothing")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    void testGetRange_FullYear_NotExisting() {
        final YearMonth                       date;
        final Iterable<? extends Transaction> data;

        date = YearMonth.of(2019, Month.DECEMBER);
        data = service.getYearMonth(date);

        Assertions.assertThat(data)
            .hasSize(0);
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testGetRange_NoData() {
        final YearMonth                       date;
        final Iterable<? extends Transaction> data;

        date = YearMonth.of(2020, Month.FEBRUARY);
        data = service.getYearMonth(date);

        Assertions.assertThat(data)
            .isEmpty();
    }

}
