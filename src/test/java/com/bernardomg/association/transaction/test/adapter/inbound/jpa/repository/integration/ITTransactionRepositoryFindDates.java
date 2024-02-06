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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.transaction.config.data.annotation.FullConsecutiveTransactionYears;
import com.bernardomg.association.transaction.config.data.annotation.FullNotConsecutiveTransactionYears;
import com.bernardomg.association.transaction.config.data.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.config.data.annotation.MultipleTransactionsSameDay;
import com.bernardomg.association.transaction.config.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonthsRange;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.config.factory.TransactionCalendarMonthsRanges;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("TransactionRepository - find dates")
class ITTransactionRepositoryFindDates {

    @Autowired
    private TransactionRepository repository;

    public ITTransactionRepositoryFindDates() {
        super();
    }

    @Test
    @DisplayName("With two full consecutive years, a range for them is returned")
    @FullConsecutiveTransactionYears
    void testGetRange_ConsecutiveFullYear() {
        final TransactionCalendarMonthsRange range;

        // WHEN
        range = repository.findDates();

        // THEN
        Assertions.assertThat(range.getMonths())
            .as("months")
            .hasSize(24)
            .containsExactlyElementsOf(TransactionCalendarMonthsRanges.CONSECUTIVE_FULL_YEAR_MONTHS);
    }

    @Test
    @DisplayName("With a full year, a range for the full year is returned")
    @FullTransactionYear
    void testGetRange_FullYear() {
        final TransactionCalendarMonthsRange range;

        // WHEN
        range = repository.findDates();

        // THEN
        Assertions.assertThat(range.getMonths())
            .as("months")
            .hasSize(12)
            .containsExactlyElementsOf(TransactionCalendarMonthsRanges.FULL_YEAR_MONTHS);
    }

    @Test
    @DisplayName("With multiple transactions the same day, a single month is returned")
    @MultipleTransactionsSameDay
    void testGetRange_MultipleSameDay() {
        final TransactionCalendarMonthsRange range;

        // WHEN
        range = repository.findDates();

        // THEN
        Assertions.assertThat(range.getMonths())
            .as("months")
            .hasSize(1)
            .containsExactly(YearMonth.of(2020, Month.JANUARY));
    }

    @Test
    @DisplayName("With multiple transactions the same month, a single month is returned")
    @MultipleTransactionsSameMonth
    void testGetRange_MultipleSameMonth() {
        final TransactionCalendarMonthsRange range;

        // WHEN
        range = repository.findDates();

        // THEN
        Assertions.assertThat(range.getMonths())
            .as("months")
            .hasSize(1)
            .containsExactly(YearMonth.of(2020, Month.JANUARY));
    }

    @Test
    @DisplayName("With no data, an empty range is returned")
    void testGetRange_NoData() {
        final TransactionCalendarMonthsRange range;

        // WHEN
        range = repository.findDates();

        // THEN
        Assertions.assertThat(range.getMonths())
            .as("months")
            .isEmpty();
    }

    @Test
    @DisplayName("With two full not consecutive years, a range for them is returned")
    @FullNotConsecutiveTransactionYears
    void testGetRange_NotConsecutiveFullYear() {
        final TransactionCalendarMonthsRange range;

        // WHEN
        range = repository.findDates();

        // THEN
        Assertions.assertThat(range.getMonths())
            .as("months")
            .hasSize(24)
            .containsExactlyElementsOf(TransactionCalendarMonthsRanges.NOT_CONSECUTIVE_FULL_YEAR_MONTHS);
    }

}
