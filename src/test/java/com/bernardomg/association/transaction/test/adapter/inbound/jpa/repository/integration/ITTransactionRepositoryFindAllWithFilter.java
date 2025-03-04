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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.configuration.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.configuration.argument.DecimalArgumentsProvider;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.configuration.data.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.configuration.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionsQueries;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("TransactionRepository - get all with filter")
class ITTransactionRepositoryFindAllWithFilter {

    @Autowired
    private TransactionRepository       repository;

    @Autowired
    private TransactionSpringRepository springRepository;

    public ITTransactionRepositoryFindAllWithFilter() {
        super();
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With a transaction with value around zero, it returns it")
    void testFindAll_AroundZero(final Float amount) {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        springRepository.save(TransactionEntities.forAmount(amount));

        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = repository.findAll(transactionQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forAmount(amount));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal transaction, it returns it")
    void testFindAll_Decimal(final Float amount) {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        springRepository.save(TransactionEntities.forAmount(amount));

        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = repository.findAll(transactionQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forAmount(amount));
    }

    @Test
    @DisplayName("With a full year, it returns all the transactions")
    @FullTransactionYear
    void testFindAll_FullYear() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = repository.findAll(transactionQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactlyInAnyOrder(Transactions.forIndex(1, Month.JANUARY),
                Transactions.forIndex(2, Month.FEBRUARY), Transactions.forIndex(3, Month.MARCH),
                Transactions.forIndex(4, Month.APRIL), Transactions.forIndex(5, Month.MAY),
                Transactions.forIndex(6, Month.JUNE), Transactions.forIndex(7, Month.JULY),
                Transactions.forIndex(8, Month.AUGUST), Transactions.forIndex(9, Month.SEPTEMBER),
                Transactions.forIndex(10, Month.OCTOBER), Transactions.forIndex(11, Month.NOVEMBER),
                Transactions.forIndex(12, Month.DECEMBER));
    }

    @Test
    @DisplayName("With multiple transactions in the month, it returns all the transactions")
    @MultipleTransactionsSameMonth
    void testFindAll_Multiple() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = repository.findAll(transactionQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndMonth(1, Month.JANUARY),
                Transactions.forIndexAndMonth(2, Month.JANUARY), Transactions.forIndexAndMonth(3, Month.JANUARY),
                Transactions.forIndexAndMonth(4, Month.JANUARY), Transactions.forIndexAndMonth(5, Month.JANUARY));
    }

}
