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

package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.transaction.configuration.data.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.configuration.data.annotation.OutOfOrderMonth;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonth;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("TransactionRepository - get for month")
class ITTransactionRepositoryFindInMonth {

    @Autowired
    private TransactionRepository repository;

    @Test
    @DisplayName("Only the data for the month is returned")
    @FullTransactionYear
    void testFindForMonth_FullYear() {
        final YearMonth                month;
        final TransactionCalendarMonth calendar;

        // GIVEN
        month = YearMonth.of(2020, Month.FEBRUARY);

        // WHEN
        calendar = repository.findInMonth(month);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(calendar.date())
                .as("date")
                .isEqualTo(month);
            softly.assertThat(calendar.transactions())
                .as("transactions")
                .containsExactly(Transactions.february());
        });
    }

    @Test
    @DisplayName("Reading for a not existing month returns nothing")
    @FullTransactionYear
    void testFindForMonth_FullYear_NotExisting() {
        final YearMonth                month;
        final TransactionCalendarMonth calendar;

        // GIVEN
        month = YearMonth.of(2019, Month.DECEMBER);

        // WHEN
        calendar = repository.findInMonth(month);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(calendar.date())
                .as("date")
                .isEqualTo(month);
            softly.assertThat(calendar.transactions())
                .as("transactions")
                .isEmpty();
        });
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testFindForMonth_NoData() {
        final YearMonth                month;
        final TransactionCalendarMonth calendar;

        // GIVEN
        month = YearMonth.of(2020, Month.FEBRUARY);

        // WHEN
        calendar = repository.findInMonth(month);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(calendar.date())
                .as("date")
                .isEqualTo(month);
            softly.assertThat(calendar.transactions())
                .as("transactions")
                .isEmpty();
        });
    }

    @Test
    @DisplayName("When the transactions are out of order they are sorted")
    @OutOfOrderMonth
    void testFindForMonth_Sorts() {
        final YearMonth                month;
        final TransactionCalendarMonth calendar;

        // GIVEN
        month = YearMonth.of(2020, Month.FEBRUARY);

        // WHEN
        calendar = repository.findInMonth(month);

        // THEN
        Assertions.assertThat(calendar.transactions())
            .as("transactions")
            .containsExactly(Transactions.forIndexAndMonth(1, Month.FEBRUARY),
                Transactions.forIndexAndMonth(2, Month.FEBRUARY), Transactions.forIndexAndMonth(3, Month.FEBRUARY),
                Transactions.forIndexAndMonth(4, Month.FEBRUARY));
    }

}
