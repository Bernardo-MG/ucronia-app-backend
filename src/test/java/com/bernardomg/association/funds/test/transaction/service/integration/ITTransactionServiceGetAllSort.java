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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.bernardomg.association.funds.test.transaction.configuration.MultipleTransactionsSameMonth;
import com.bernardomg.association.funds.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.funds.test.transaction.util.model.TransactionsQuery;
import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.association.funds.transaction.model.request.TransactionQuery;
import com.bernardomg.association.funds.transaction.service.TransactionService;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction service - get all - sort")
@MultipleTransactionsSameMonth
class ITTransactionServiceGetAllSort {

    @Autowired
    private TransactionService service;

    public ITTransactionServiceGetAllSort() {
        super();
    }

    @Test
    @DisplayName("With ascending order by date it returns the ordered data")
    void testGetAll_Date_Asc() {
        final Iterator<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        Transaction                 transaction;
        final Pageable              pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "date");

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable)
            .iterator();

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 2")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 2))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 3")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 3))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 4")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 4))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 5")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 5))
            .build());
    }

    @Test
    @DisplayName("With descending order by date it returns the ordered data")
    void testGetAll_Date_Desc() {
        final Iterator<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        Transaction                 transaction;
        final Pageable              pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "date");

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable)
            .iterator();

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 5")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 5))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 4")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 4))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 3")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 3))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 2")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 2))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .build());
    }

    @Test
    @DisplayName("With ascending order by description it returns the ordered data")
    void testGetAll_Description_Asc() {
        final Iterator<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        Transaction                 transaction;
        final Pageable              pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "description");

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable)
            .iterator();

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 2")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 2))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 3")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 3))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 4")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 4))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 5")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 5))
            .build());
    }

    @Test
    @DisplayName("With descending order by description it returns the ordered data")
    void testGetAll_Description_Desc() {
        final Iterator<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;
        Transaction                 transaction;

        pageable = PageRequest.of(0, 10, Direction.DESC, "description");

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable)
            .iterator();

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 5")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 5))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 4")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 4))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 3")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 3))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 2")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 2))
            .build());

        transaction = transactions.next();
        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .build());
    }

}
