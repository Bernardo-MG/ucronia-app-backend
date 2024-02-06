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
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.association.transaction.config.data.annotation.DecimalsAddZeroTransaction;
import com.bernardomg.association.transaction.config.data.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.config.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;
import com.bernardomg.association.transaction.domain.repository.TransactionBalanceRepository;
import com.bernardomg.association.transaction.test.config.argument.CurrentAndPreviousMonthProvider;
import com.bernardomg.association.transaction.test.config.factory.TransactionBalanceQueries;
import com.bernardomg.association.transaction.test.config.factory.TransactionMonthlyBalances;
import com.bernardomg.association.transaction.test.util.initializer.TransactionInitializer;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("TransactionBalanceRepository - find monthly balance")
class ITTransactionBalanceRepositoryFindMonthlyBalance {

    @Autowired
    private TransactionBalanceRepository repository;

    @Autowired
    private TransactionInitializer       transactionInitializer;

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With values around zero it returns the correct amounts")
    void testFindMonthlyBalance_AroundZero(final Float amount) {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        transactionInitializer.registerCurrentMonth(amount);

        sort = Sort.unsorted();

        query = TransactionBalanceQueries.empty();

        // WHEN
        balances = repository.findMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(TransactionMonthlyBalances.currentMonth(amount));
    }

    @ParameterizedTest(name = "Date: {0}")
    @ArgumentsSource(CurrentAndPreviousMonthProvider.class)
    @DisplayName("Returns balance for the current month and adjacents")
    void testFindMonthlyBalance_Dates(final YearMonth date) {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        transactionInitializer.registerAt(date.getYear(), date.getMonth());

        sort = Sort.unsorted();

        query = TransactionBalanceQueries.empty();

        // WHEN
        balances = repository.findMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(TransactionMonthlyBalances.forAmount(date, 1F));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With decimal values it returns the correct amounts")
    void testFindMonthlyBalance_Decimal(final Float amount) {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        transactionInitializer.registerCurrentMonth(amount);

        sort = Sort.unsorted();

        query = TransactionBalanceQueries.empty();

        // WHEN
        balances = repository.findMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(TransactionMonthlyBalances.currentMonth(amount));
    }

    @Test
    @DisplayName("With decimal values which sum zero the returned balance is zero")
    @DecimalsAddZeroTransaction
    void testFindMonthlyBalance_DecimalsAddUpToZero() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.empty();

        // WHEN
        balances = repository.findMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(TransactionMonthlyBalances.forAmount(0F));
    }

    @Test
    @DisplayName("With a full year it returns twelve months")
    @FullTransactionYear
    void testFindMonthlyBalance_FullYear() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.empty();

        // WHEN
        balances = repository.findMonthlyBalance(query, sort);

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
    @DisplayName("With multiple transactions for a single month it returns a single month")
    @MultipleTransactionsSameMonth
    void testFindMonthlyBalance_Multiple() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.empty();

        // WHEN
        balances = repository.findMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .containsExactly(TransactionMonthlyBalances.forAmount(5F));
    }

    @Test
    @DisplayName("Returns no balance for the next month")
    void testFindMonthlyBalance_NextMonth() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        transactionInitializer.registerNextMonth(1F);

        sort = Sort.unsorted();

        query = TransactionBalanceQueries.empty();

        // WHEN
        balances = repository.findMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testFindMonthlyBalance_NoData() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sort                                  sort;

        // GIVEN
        sort = Sort.unsorted();

        query = TransactionBalanceQueries.empty();

        // WHEN
        balances = repository.findMonthlyBalance(query, sort);

        // THEN
        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

}
