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

import java.time.LocalDate;
import java.time.Month;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.transaction.configuration.data.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.configuration.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionsQueries;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("TransactionRepository - get all - filtered")
class ITTransactionRepositoryFindAllFilter {

    @Autowired
    private TransactionRepository repository;

    public ITTransactionRepositoryFindAllFilter() {
        super();
    }

    @Test
    @DisplayName("With a filter applied to the start date, the returned data is filtered")
    @MultipleTransactionsSameMonth
    void testFindAll_AfterDate() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQueries.startDate(LocalDate.of(2020, Month.JANUARY, 2));

        // WHEN
        transactions = repository.findAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndDay(2, Month.JANUARY),
                Transactions.forIndexAndDay(3, Month.JANUARY), Transactions.forIndexAndDay(4, Month.JANUARY),
                Transactions.forIndexAndDay(5, Month.JANUARY));
    }

    @Test
    @DisplayName("With a filter applied to the end date, the returned data is filtered")
    @MultipleTransactionsSameMonth
    void testFindAll_BeforeDate() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQueries.endDate(LocalDate.of(2020, Month.JANUARY, 2));

        // WHEN
        transactions = repository.findAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndDay(1, Month.JANUARY),
                Transactions.forIndexAndDay(2, Month.JANUARY));
    }

    @Test
    @DisplayName("With a filter applied to the date, the returned data is filtered")
    @MultipleTransactionsSameMonth
    void testFindAll_InDate() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQueries.date(LocalDate.of(2020, Month.JANUARY, 2));

        // WHEN
        transactions = repository.findAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndDay(2, Month.JANUARY));
    }

    @Test
    @DisplayName("With a filter applied to the date for the first day of the year, the returned data is filtered")
    @FullTransactionYear
    void testFindAll_InDate_FirstDay() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQueries.date(LocalDate.of(2020, Month.JANUARY, 1));

        // WHEN
        transactions = repository.findAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndDay(1, Month.JANUARY));
    }

    @Test
    @DisplayName("With a filter applied to the date for the last day of the year, the returned data is filtered")
    @FullTransactionYear
    void testFindAll_InDate_LastDay() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // TODO: This is not the last day of the year
        transactionQuery = TransactionsQueries.date(LocalDate.of(2020, Month.DECEMBER, 1));

        // WHEN
        transactions = repository.findAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndex(12, Month.DECEMBER));
    }

}
