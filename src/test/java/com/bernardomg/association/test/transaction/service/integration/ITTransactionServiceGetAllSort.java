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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.test.transaction.util.model.TransactionsQuery;
import com.bernardomg.association.transaction.model.ImmutableTransaction;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.request.TransactionQuery;
import com.bernardomg.association.transaction.service.TransactionService;

@IntegrationTest
@DisplayName("Transaction service - get all - sort")
@Sql({ "/db/queries/transaction/multiple.sql" })
public class ITTransactionServiceGetAllSort {

    @Autowired
    private TransactionService service;

    public ITTransactionServiceGetAllSort() {
        super();
    }

    @Test
    @DisplayName("With ascending order by date it returns the ordered data")
    public void testGetAll_Date_Asc() {
        final Iterator<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        Transaction                 transaction;
        final Pageable              pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "date");

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable)
            .iterator();

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 2")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 2))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 3")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 3))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 4")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 4))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 5")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 5))
            .build());
    }

    @Test
    @DisplayName("With descending order by date it returns the ordered data")
    public void testGetAll_Date_Desc() {
        final Iterator<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        Transaction                 transaction;
        final Pageable              pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "date");

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable)
            .iterator();

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 5")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 5))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 4")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 4))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 3")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 3))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 2")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 2))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());
    }

    @Test
    @DisplayName("With ascending order by description it returns the ordered data")
    public void testGetAll_Description_Asc() {
        final Iterator<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        Transaction                 transaction;
        final Pageable              pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "description");

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable)
            .iterator();

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 2")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 2))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 3")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 3))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 4")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 4))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 5")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 5))
            .build());
    }

    @Test
    @DisplayName("With descending order by description it returns the ordered data")
    public void testGetAll_Description_Desc() {
        final Iterator<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;
        Transaction                 transaction;

        pageable = PageRequest.of(0, 10, Direction.DESC, "description");

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable)
            .iterator();

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 5")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 5))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 4")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 4))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 3")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 3))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 2")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 2))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());
    }

}