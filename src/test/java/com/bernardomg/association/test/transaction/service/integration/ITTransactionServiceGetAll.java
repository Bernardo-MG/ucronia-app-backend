/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.test.transaction.service.integration;

import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.association.test.config.factory.ModelFactory;
import com.bernardomg.association.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.test.transaction.util.model.TransactionsQuery;
import com.bernardomg.association.transaction.model.ImmutableTransaction;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.request.TransactionQuery;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.transaction.service.TransactionService;

@IntegrationTest
@AllAuthoritiesMockUser
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
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a transaction with value around zero, it returns it")
    void testGetAll_AroundZero(final Float amount) {
        final Iterator<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;
        Transaction                 transaction;

        repository.save(ModelFactory.transaction(amount));

        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable)
            .iterator();

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction")
            .amount(amount)
            .date(new GregorianCalendar(2020, 1, 1))
            .build());
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal transaction, it returns it")
    void testGetAll_Decimal(final Float amount) {
        final Iterator<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;
        Transaction                 transaction;

        repository.save(ModelFactory.transaction(amount));

        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable)
            .iterator();

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction")
            .amount(amount)
            .date(new GregorianCalendar(2020, 1, 1))
            .build());
    }

    @Test
    @DisplayName("With a full year, it returns all the transactions")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    void testGetAll_FullYear_Count() {
        final Iterable<Transaction> transactions;
        final Iterator<Transaction> transactionsItr;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;
        Transaction                 transaction;

        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable);

        Assertions.assertThat(IterableUtils.size(transactions))
            .isEqualTo(12);

        transactionsItr = transactions.iterator();

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 2")
            .amount(1f)
            .date(new GregorianCalendar(2020, 1, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 3")
            .amount(1f)
            .date(new GregorianCalendar(2020, 2, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 4")
            .amount(1f)
            .date(new GregorianCalendar(2020, 3, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 5")
            .amount(1f)
            .date(new GregorianCalendar(2020, 4, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 6")
            .amount(1f)
            .date(new GregorianCalendar(2020, 5, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 7")
            .amount(1f)
            .date(new GregorianCalendar(2020, 6, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 8")
            .amount(1f)
            .date(new GregorianCalendar(2020, 7, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 9")
            .amount(1f)
            .date(new GregorianCalendar(2020, 8, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 10")
            .amount(1f)
            .date(new GregorianCalendar(2020, 9, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 11")
            .amount(1f)
            .date(new GregorianCalendar(2020, 10, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 12")
            .amount(1f)
            .date(new GregorianCalendar(2020, 11, 1))
            .build());
    }

    @Test
    @DisplayName("With multiple transactions, it returns all the transactions")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    void testGetAll_Multiple_Count() {
        final Iterable<Transaction> transactions;
        final Iterator<Transaction> transactionsItr;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;
        Transaction                 transaction;

        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable);

        Assertions.assertThat(IterableUtils.size(transactions))
            .isEqualTo(5);

        transactionsItr = transactions.iterator();

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 2")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 2))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 3")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 3))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 4")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 4))
            .build());

        transaction = transactionsItr.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 5")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 5))
            .build());
    }

}
