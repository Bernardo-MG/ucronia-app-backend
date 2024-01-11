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

package com.bernardomg.association.funds.test.transaction.service.integration;

import java.time.Month;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.funds.test.transaction.config.factory.TransactionEntities;
import com.bernardomg.association.funds.test.transaction.config.factory.Transactions;
import com.bernardomg.association.funds.test.transaction.config.factory.TransactionsQueries;
import com.bernardomg.association.funds.test.transaction.configuration.FullTransactionYear;
import com.bernardomg.association.funds.test.transaction.configuration.MultipleTransactionsSameMonth;
import com.bernardomg.association.funds.transaction.service.TransactionService;
import com.bernardomg.association.model.transaction.Transaction;
import com.bernardomg.association.model.transaction.TransactionQuery;
import com.bernardomg.association.persistence.transaction.repository.TransactionRepository;
import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction service - get all")
class ITTransactionServiceGetAll {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionService    service;

    public ITTransactionServiceGetAll() {
        super();
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With a transaction with value around zero, it returns it")
    void testGetAll_AroundZero(final Float amount) {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        repository.save(TransactionEntities.forAmount(amount));

        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = service.getAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forAmount(amount));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal transaction, it returns it")
    void testGetAll_Decimal(final Float amount) {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        repository.save(TransactionEntities.forAmount(amount));

        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = service.getAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forAmount(amount));
    }

    @Test
    @DisplayName("With a full year, it returns all the transactions")
    @FullTransactionYear
    void testGetAll_FullYear() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = service.getAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndex(1, Month.JANUARY), Transactions.forIndex(2, Month.FEBRUARY),
                Transactions.forIndex(3, Month.MARCH), Transactions.forIndex(4, Month.APRIL),
                Transactions.forIndex(5, Month.MAY), Transactions.forIndex(6, Month.JUNE),
                Transactions.forIndex(7, Month.JULY), Transactions.forIndex(8, Month.AUGUST),
                Transactions.forIndex(9, Month.SEPTEMBER), Transactions.forIndex(10, Month.OCTOBER),
                Transactions.forIndex(11, Month.NOVEMBER), Transactions.forIndex(12, Month.DECEMBER));
    }

    @Test
    @DisplayName("With multiple transactions in the month, it returns all the transactions")
    @MultipleTransactionsSameMonth
    void testGetAll_Multiple() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = service.getAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndDay(1, Month.JANUARY),
                Transactions.forIndexAndDay(2, Month.JANUARY), Transactions.forIndexAndDay(3, Month.JANUARY),
                Transactions.forIndexAndDay(4, Month.JANUARY), Transactions.forIndexAndDay(5, Month.JANUARY));
    }

}
