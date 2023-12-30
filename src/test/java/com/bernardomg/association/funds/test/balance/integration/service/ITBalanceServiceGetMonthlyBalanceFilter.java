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

package com.bernardomg.association.funds.test.balance.integration.service;

import java.time.Month;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.funds.balance.model.BalanceQuery;
import com.bernardomg.association.funds.balance.model.MonthlyBalance;
import com.bernardomg.association.funds.balance.service.BalanceService;
import com.bernardomg.association.funds.test.balance.util.model.BalanceQueries;
import com.bernardomg.association.funds.test.balance.util.model.MonthlyBalances;
import com.bernardomg.association.funds.test.transaction.configuration.FullTransactionYear;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Balance service - get monthly balance - filter")
class ITBalanceServiceGetMonthlyBalanceFilter {

    @Autowired
    private BalanceService service;

    @Test
    @DisplayName("Filtering ending before the year returns no month")
    @FullTransactionYear
    void testGetMonthlyBalance_EndBeforeStart() {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.endDate(2019, Month.DECEMBER);

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
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.endDate(2020, Month.DECEMBER);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyBalances.forAmount(Month.JANUARY, 1, 1),
                MonthlyBalances.forAmount(Month.FEBRUARY, 1, 2), MonthlyBalances.forAmount(Month.MARCH, 1, 3),
                MonthlyBalances.forAmount(Month.APRIL, 1, 4), MonthlyBalances.forAmount(Month.MAY, 1, 5),
                MonthlyBalances.forAmount(Month.JUNE, 1, 6), MonthlyBalances.forAmount(Month.JULY, 1, 7),
                MonthlyBalances.forAmount(Month.AUGUST, 1, 8), MonthlyBalances.forAmount(Month.SEPTEMBER, 1, 9),
                MonthlyBalances.forAmount(Month.OCTOBER, 1, 10), MonthlyBalances.forAmount(Month.NOVEMBER, 1, 11),
                MonthlyBalances.forAmount(Month.DECEMBER, 1, 12));
    }

    @Test
    @DisplayName("Filtering the full year returns all the months")
    @FullTransactionYear
    void testGetMonthlyBalance_FullYear() {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.range(2020, Month.JANUARY, Month.DECEMBER);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyBalances.forAmount(Month.JANUARY, 1, 1),
                MonthlyBalances.forAmount(Month.FEBRUARY, 1, 2), MonthlyBalances.forAmount(Month.MARCH, 1, 3),
                MonthlyBalances.forAmount(Month.APRIL, 1, 4), MonthlyBalances.forAmount(Month.MAY, 1, 5),
                MonthlyBalances.forAmount(Month.JUNE, 1, 6), MonthlyBalances.forAmount(Month.JULY, 1, 7),
                MonthlyBalances.forAmount(Month.AUGUST, 1, 8), MonthlyBalances.forAmount(Month.SEPTEMBER, 1, 9),
                MonthlyBalances.forAmount(Month.OCTOBER, 1, 10), MonthlyBalances.forAmount(Month.NOVEMBER, 1, 11),
                MonthlyBalances.forAmount(Month.DECEMBER, 1, 12));
    }

    @Test
    @DisplayName("Filtering by January returns only that month")
    @FullTransactionYear
    void testGetMonthlyBalance_January() {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.range(2020, Month.JANUARY, Month.JANUARY);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyBalances.forAmount(Month.JANUARY, 1, 1));
    }

    @Test
    @DisplayName("Filtering by January and February returns only those months")
    @FullTransactionYear
    void testGetMonthlyBalance_JanuaryToFebruary() {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.range(2020, Month.JANUARY, Month.FEBRUARY);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyBalances.forAmount(Month.JANUARY, 1, 1),
                MonthlyBalances.forAmount(Month.FEBRUARY, 1, 2));
    }

    @Test
    @DisplayName("Filtering with a range where the end is before the start returns nothing")
    @FullTransactionYear
    void testGetMonthlyBalance_RangeEndBeforeStart() {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.range(2020, Month.DECEMBER, Month.JANUARY);

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
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.startDate(2021, Month.JANUARY);

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
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.startDate(2020, Month.JANUARY);

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyBalances.forAmount(Month.JANUARY, 1, 1),
                MonthlyBalances.forAmount(Month.FEBRUARY, 1, 2), MonthlyBalances.forAmount(Month.MARCH, 1, 3),
                MonthlyBalances.forAmount(Month.APRIL, 1, 4), MonthlyBalances.forAmount(Month.MAY, 1, 5),
                MonthlyBalances.forAmount(Month.JUNE, 1, 6), MonthlyBalances.forAmount(Month.JULY, 1, 7),
                MonthlyBalances.forAmount(Month.AUGUST, 1, 8), MonthlyBalances.forAmount(Month.SEPTEMBER, 1, 9),
                MonthlyBalances.forAmount(Month.OCTOBER, 1, 10), MonthlyBalances.forAmount(Month.NOVEMBER, 1, 11),
                MonthlyBalances.forAmount(Month.DECEMBER, 1, 12));
    }

}
