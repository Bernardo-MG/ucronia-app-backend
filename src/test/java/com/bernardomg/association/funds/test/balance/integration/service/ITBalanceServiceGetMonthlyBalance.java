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
import java.time.YearMonth;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.funds.balance.model.BalanceQuery;
import com.bernardomg.association.funds.balance.model.MonthlyBalance;
import com.bernardomg.association.funds.balance.service.BalanceService;
import com.bernardomg.association.funds.test.balance.util.model.BalanceQueries;
import com.bernardomg.association.funds.test.balance.util.model.MonthlyBalances;
import com.bernardomg.association.funds.test.configuration.argument.CurrentAndPreviousMonthProvider;
import com.bernardomg.association.funds.test.transaction.configuration.DecimalsAddZeroTransaction;
import com.bernardomg.association.funds.test.transaction.configuration.FullTransactionYear;
import com.bernardomg.association.funds.test.transaction.configuration.MultipleTransactionsSameMonth;
import com.bernardomg.association.funds.test.transaction.util.initializer.TransactionInitializer;
import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Balance service - get monthly balance")
class ITBalanceServiceGetMonthlyBalance {

    @Autowired
    private BalanceService         service;

    @Autowired
    private TransactionInitializer transactionInitializer;

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With values around zero it returns the correct amounts")
    void testGetMonthlyBalance_AroundZero(final Float amount) {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        transactionInitializer.registerCurrentMonth(amount);

        sort = Sort.unsorted();

        query = BalanceQueries.empty();

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyBalances.currentMonth(amount));
    }

    @ParameterizedTest(name = "Date: {0}")
    @ArgumentsSource(CurrentAndPreviousMonthProvider.class)
    @DisplayName("Returns balance for the current month and adjacents")
    void testGetMonthlyBalance_Dates(final YearMonth date) {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        transactionInitializer.registerAt(date.getYear(), date.getMonth());

        sort = Sort.unsorted();

        query = BalanceQueries.empty();

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyBalances.forAmount(date, 1F));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With decimal values it returns the correct amounts")
    void testGetMonthlyBalance_Decimal(final Float amount) {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        transactionInitializer.registerCurrentMonth(amount);

        sort = Sort.unsorted();

        query = BalanceQueries.empty();

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyBalances.currentMonth(amount));
    }

    @Test
    @DisplayName("With decimal values which sum zero the returned balance is zero")
    @DecimalsAddZeroTransaction
    void testGetMonthlyBalance_DecimalsAddUpToZero() {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.empty();

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyBalances.forAmount(0F));
    }

    @Test
    @DisplayName("With a full year it returns twelve months")
    @FullTransactionYear
    void testGetMonthlyBalance_FullYear() {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.empty();

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
    @DisplayName("With multiple transactions for a single month it returns a single month")
    @MultipleTransactionsSameMonth
    void testGetMonthlyBalance_Multiple() {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.empty();

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(MonthlyBalances.forAmount(5F));
    }

    @Test
    @DisplayName("Returns no balance for the next month")
    void testGetMonthlyBalance_NextMonth() {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        transactionInitializer.registerNextMonth(1F);

        sort = Sort.unsorted();

        query = BalanceQueries.empty();

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testGetMonthlyBalance_NoData() {
        final Collection<MonthlyBalance> balances;
        final BalanceQuery               query;
        final Sort                       sort;

        // GIVEN
        sort = Sort.unsorted();

        query = BalanceQueries.empty();

        // WHEN
        balances = service.getMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

}
