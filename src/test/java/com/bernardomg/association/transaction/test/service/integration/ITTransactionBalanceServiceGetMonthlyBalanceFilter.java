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

package com.bernardomg.association.transaction.test.service.integration;

import java.time.Month;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.test.data.transaction.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.model.TransactionMonthlyBalance;
import com.bernardomg.association.transaction.service.TransactionBalanceService;
import com.bernardomg.association.transaction.test.config.factory.TransactionBalanceQueries;
import com.bernardomg.association.transaction.test.config.factory.TransactionMonthlyBalances;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction balance service - get monthly balance - filter")
class ITTransactionBalanceServiceGetMonthlyBalanceFilter {

    @Autowired
    private TransactionBalanceService service;

    @Test
    @DisplayName("Filtering ending before the year returns no month")
    @FullTransactionYear
    void testGetMonthlyBalance_EndBeforeStart() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.endDate(2019, Month.DECEMBER);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("Filtering ending on December returns all the months")
    @FullTransactionYear
    void testGetMonthlyBalance_EndDecember() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.endDate(2020, Month.DECEMBER);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(TransactionMonthlyBalances.forAmount(Month.JANUARY, 1, 1),
                TransactionMonthlyBalances.forAmount(Month.FEBRUARY, 1, 2),
                TransactionMonthlyBalances.forAmount(Month.MARCH, 1, 3),
                TransactionMonthlyBalances.forAmount(Month.APRIL, 1, 4),
                TransactionMonthlyBalances.forAmount(Month.MAY, 1, 5),
                TransactionMonthlyBalances.forAmount(Month.JUNE, 1, 6),
                TransactionMonthlyBalances.forAmount(Month.JULY, 1, 7),
                TransactionMonthlyBalances.forAmount(Month.AUGUST, 1, 8),
                TransactionMonthlyBalances.forAmount(Month.SEPTEMBER, 1, 9),
                TransactionMonthlyBalances.forAmount(Month.OCTOBER, 1, 10),
                TransactionMonthlyBalances.forAmount(Month.NOVEMBER, 1, 11),
                TransactionMonthlyBalances.forAmount(Month.DECEMBER, 1, 12));
    }

    @Test
    @DisplayName("Filtering the full year returns all the months")
    @FullTransactionYear
    void testGetMonthlyBalance_FullYear() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.range(2020, Month.JANUARY, Month.DECEMBER);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(TransactionMonthlyBalances.forAmount(Month.JANUARY, 1, 1),
                TransactionMonthlyBalances.forAmount(Month.FEBRUARY, 1, 2),
                TransactionMonthlyBalances.forAmount(Month.MARCH, 1, 3),
                TransactionMonthlyBalances.forAmount(Month.APRIL, 1, 4),
                TransactionMonthlyBalances.forAmount(Month.MAY, 1, 5),
                TransactionMonthlyBalances.forAmount(Month.JUNE, 1, 6),
                TransactionMonthlyBalances.forAmount(Month.JULY, 1, 7),
                TransactionMonthlyBalances.forAmount(Month.AUGUST, 1, 8),
                TransactionMonthlyBalances.forAmount(Month.SEPTEMBER, 1, 9),
                TransactionMonthlyBalances.forAmount(Month.OCTOBER, 1, 10),
                TransactionMonthlyBalances.forAmount(Month.NOVEMBER, 1, 11),
                TransactionMonthlyBalances.forAmount(Month.DECEMBER, 1, 12));
    }

    @Test
    @DisplayName("Filtering by January returns only that month")
    @FullTransactionYear
    void testGetMonthlyBalance_January() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.range(2020, Month.JANUARY, Month.JANUARY);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(TransactionMonthlyBalances.forAmount(Month.JANUARY, 1, 1));
    }

    @Test
    @DisplayName("Filtering by January and February returns only those months")
    @FullTransactionYear
    void testGetMonthlyBalance_JanuaryToFebruary() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.range(2020, Month.JANUARY, Month.FEBRUARY);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(TransactionMonthlyBalances.forAmount(Month.JANUARY, 1, 1),
                TransactionMonthlyBalances.forAmount(Month.FEBRUARY, 1, 2));
    }

    @Test
    @DisplayName("Filtering with a range where the end is before the start returns nothing")
    @FullTransactionYear
    void testGetMonthlyBalance_RangeEndBeforeStart() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.range(2020, Month.DECEMBER, Month.JANUARY);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("Filtering beginning after the year returns no month")
    @FullTransactionYear
    void testGetMonthlyBalance_StartAfterEnd() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.startDate(2021, Month.JANUARY);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("Filtering beginning on January returns all the months")
    @FullTransactionYear
    void testGetMonthlyBalance_StartInJanuary() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.startDate(2020, Month.JANUARY);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(TransactionMonthlyBalances.forAmount(Month.JANUARY, 1, 1),
                TransactionMonthlyBalances.forAmount(Month.FEBRUARY, 1, 2),
                TransactionMonthlyBalances.forAmount(Month.MARCH, 1, 3),
                TransactionMonthlyBalances.forAmount(Month.APRIL, 1, 4),
                TransactionMonthlyBalances.forAmount(Month.MAY, 1, 5),
                TransactionMonthlyBalances.forAmount(Month.JUNE, 1, 6),
                TransactionMonthlyBalances.forAmount(Month.JULY, 1, 7),
                TransactionMonthlyBalances.forAmount(Month.AUGUST, 1, 8),
                TransactionMonthlyBalances.forAmount(Month.SEPTEMBER, 1, 9),
                TransactionMonthlyBalances.forAmount(Month.OCTOBER, 1, 10),
                TransactionMonthlyBalances.forAmount(Month.NOVEMBER, 1, 11),
                TransactionMonthlyBalances.forAmount(Month.DECEMBER, 1, 12));
    }

}
