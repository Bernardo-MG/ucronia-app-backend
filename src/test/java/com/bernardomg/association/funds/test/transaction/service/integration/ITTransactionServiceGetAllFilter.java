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

import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.funds.test.transaction.util.model.TransactionsQuery;
import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.association.funds.transaction.model.request.TransactionQuery;
import com.bernardomg.association.funds.transaction.service.TransactionService;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Transaction service - get all - filter")
class ITTransactionServiceGetAllFilter {

    @Autowired
    private TransactionService service;

    public ITTransactionServiceGetAllFilter() {
        super();
    }

    @Test
    @DisplayName("With a filter applied to the start date, the returned data is filtered")
    @Sql({ "/db/queries/transaction/multiple_same_month.sql" })
    void testGetAll_AfterDate() {
        final Iterable<Transaction> transactions;
        final Iterator<Transaction> transactionsItr;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;
        Transaction                 transaction;

        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQuery.startDate(LocalDate.of(2020, Month.JANUARY, 2));

        transactions = service.getAll(transactionQuery, pageable);

        Assertions.assertThat(transactions)
            .hasSize(4);

        transactionsItr = transactions.iterator();

        transaction = transactionsItr.next();
        Assertions.assertThat(transaction.getId())
            .isNotNull();
        Assertions.assertThat(transaction.getDescription())
            .isEqualTo("Transaction 2");
        Assertions.assertThat(transaction.getDate())
            .isEqualTo(LocalDate.of(2020, Month.JANUARY, 2));
        Assertions.assertThat(transaction.getAmount())
            .isEqualTo(1f);

        transaction = transactionsItr.next();
        Assertions.assertThat(transaction.getId())
            .isNotNull();
        Assertions.assertThat(transaction.getDescription())
            .isEqualTo("Transaction 3");
        Assertions.assertThat(transaction.getDate())
            .isEqualTo(LocalDate.of(2020, Month.JANUARY, 3));
        Assertions.assertThat(transaction.getAmount())
            .isEqualTo(1f);

        transaction = transactionsItr.next();
        Assertions.assertThat(transaction.getId())
            .isNotNull();
        Assertions.assertThat(transaction.getDescription())
            .isEqualTo("Transaction 4");
        Assertions.assertThat(transaction.getDate())
            .isEqualTo(LocalDate.of(2020, Month.JANUARY, 4));
        Assertions.assertThat(transaction.getAmount())
            .isEqualTo(1f);

        transaction = transactionsItr.next();
        Assertions.assertThat(transaction.getId())
            .isNotNull();
        Assertions.assertThat(transaction.getDescription())
            .isEqualTo("Transaction 5");
        Assertions.assertThat(transaction.getDate())
            .isEqualTo(LocalDate.of(2020, Month.JANUARY, 5));
        Assertions.assertThat(transaction.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("With a filter applied to the end date, the returned data is filtered")
    @Sql({ "/db/queries/transaction/multiple_same_month.sql" })
    void testGetAll_BeforeDate() {
        final Iterable<Transaction> transactions;
        final Iterator<Transaction> transactionsItr;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;
        Transaction                 transaction;

        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQuery.endDate(LocalDate.of(2020, Month.JANUARY, 2));

        transactions = service.getAll(transactionQuery, pageable);

        Assertions.assertThat(transactions)
            .hasSize(2);

        transactionsItr = transactions.iterator();

        transaction = transactionsItr.next();
        Assertions.assertThat(transaction.getId())
            .isNotNull();
        Assertions.assertThat(transaction.getDescription())
            .isEqualTo("Transaction 1");
        Assertions.assertThat(transaction.getDate())
            .isEqualTo(LocalDate.of(2020, Month.JANUARY, 1));
        Assertions.assertThat(transaction.getAmount())
            .isEqualTo(1f);

        transaction = transactionsItr.next();
        Assertions.assertThat(transaction.getId())
            .isNotNull();
        Assertions.assertThat(transaction.getDescription())
            .isEqualTo("Transaction 2");
        Assertions.assertThat(transaction.getDate())
            .isEqualTo(LocalDate.of(2020, Month.JANUARY, 2));
        Assertions.assertThat(transaction.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("With a filter applied to the date, the returned data is filtered")
    @Sql({ "/db/queries/transaction/multiple_same_month.sql" })
    void testGetAll_InDate() {
        final Iterable<Transaction> transactions;
        final Iterator<Transaction> transactionsItr;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;
        Transaction                 transaction;

        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQuery.date(LocalDate.of(2020, Month.JANUARY, 2));

        transactions = service.getAll(transactionQuery, pageable);

        Assertions.assertThat(transactions)
            .hasSize(1);

        transactionsItr = transactions.iterator();

        transaction = transactionsItr.next();
        Assertions.assertThat(transaction.getId())
            .isNotNull();
        Assertions.assertThat(transaction.getDescription())
            .isEqualTo("Transaction 2");
        Assertions.assertThat(transaction.getDate())
            .isEqualTo(LocalDate.of(2020, Month.JANUARY, 2));
        Assertions.assertThat(transaction.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("With a filter applied to the date for the first day of the year, the returned data is filtered")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    void testGetAll_InDate_FirstDay() {
        final Iterable<Transaction> transactions;
        final Iterator<Transaction> transactionsItr;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;
        Transaction                 transaction;

        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQuery.date(LocalDate.of(2020, Month.JANUARY, 1));

        transactions = service.getAll(transactionQuery, pageable);

        Assertions.assertThat(transactions)
            .hasSize(1);

        transactionsItr = transactions.iterator();

        transaction = transactionsItr.next();
        Assertions.assertThat(transaction.getId())
            .isNotNull();
        Assertions.assertThat(transaction.getDescription())
            .isEqualTo("Transaction 1");
        Assertions.assertThat(transaction.getDate())
            .isEqualTo(LocalDate.of(2020, Month.JANUARY, 1));
        Assertions.assertThat(transaction.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("With a filter applied to the date for the last day of the year, the returned data is filtered")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    void testGetAll_InDate_LastDay() {
        final Iterable<Transaction> transactions;
        final Iterator<Transaction> transactionsItr;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;
        Transaction                 transaction;

        pageable = Pageable.unpaged();

        // TODO: This is not the last day of the year
        transactionQuery = TransactionsQuery.date(LocalDate.of(2020, Month.DECEMBER, 1));

        transactions = service.getAll(transactionQuery, pageable);

        Assertions.assertThat(transactions)
            .hasSize(1);

        transactionsItr = transactions.iterator();

        transaction = transactionsItr.next();
        Assertions.assertThat(transaction.getId())
            .isNotNull();
        Assertions.assertThat(transaction.getDescription())
            .isEqualTo("Transaction 12");
        Assertions.assertThat(transaction.getDate())
            .isEqualTo(LocalDate.of(2020, Month.DECEMBER, 1));
        Assertions.assertThat(transaction.getAmount())
            .isEqualTo(1f);
    }

}
