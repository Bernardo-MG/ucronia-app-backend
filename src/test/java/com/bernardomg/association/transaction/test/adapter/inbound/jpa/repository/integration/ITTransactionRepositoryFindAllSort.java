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
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mapping.PropertyReferenceException;

import com.bernardomg.association.transaction.configuration.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionsQueries;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("TransactionRepository - get all - sort")
@MultipleTransactionsSameMonth
class ITTransactionRepositoryFindAllSort {

    @Autowired
    private TransactionRepository repository;

    @Test
    @DisplayName("With ascending order by date it returns the ordered data")
    void testFindAll_Date_Asc() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.ASC, "date");

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = repository.findAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndDay(1, Month.JANUARY),
                Transactions.forIndexAndDay(2, Month.JANUARY), Transactions.forIndexAndDay(3, Month.JANUARY),
                Transactions.forIndexAndDay(4, Month.JANUARY), Transactions.forIndexAndDay(5, Month.JANUARY));
    }

    @Test
    @DisplayName("With descending order by date it returns the ordered data")
    void testFindAll_Date_Desc() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.DESC, "date");

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = repository.findAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndDay(5, Month.JANUARY),
                Transactions.forIndexAndDay(4, Month.JANUARY), Transactions.forIndexAndDay(3, Month.JANUARY),
                Transactions.forIndexAndDay(2, Month.JANUARY), Transactions.forIndexAndDay(1, Month.JANUARY));
    }

    @Test
    @DisplayName("With ascending order by description it returns the ordered data")
    void testFindAll_Description_Asc() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.ASC, "description");

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = repository.findAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndDay(1, Month.JANUARY),
                Transactions.forIndexAndDay(2, Month.JANUARY), Transactions.forIndexAndDay(3, Month.JANUARY),
                Transactions.forIndexAndDay(4, Month.JANUARY), Transactions.forIndexAndDay(5, Month.JANUARY));
    }

    @Test
    @DisplayName("With descending order by description it returns the ordered data")
    void testFindAll_Description_Desc() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.DESC, "description");

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = repository.findAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndDay(5, Month.JANUARY),
                Transactions.forIndexAndDay(4, Month.JANUARY), Transactions.forIndexAndDay(3, Month.JANUARY),
                Transactions.forIndexAndDay(2, Month.JANUARY), Transactions.forIndexAndDay(1, Month.JANUARY));
    }

    @Test
    @DisplayName("Ordering by a not existing field generates an error")
    void testGetAll_NotExisting() {
        final TransactionQuery transactionQuery;
        final Pageable         pageable;
        final ThrowingCallable executable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.ASC, "abc");

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        executable = () -> repository.findAll(transactionQuery, pageable)
            .iterator();

        // THEN
        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(PropertyReferenceException.class);
    }

}
