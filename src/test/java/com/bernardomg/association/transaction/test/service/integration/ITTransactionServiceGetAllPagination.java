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

package com.bernardomg.association.transaction.test.service.integration;

import java.time.Month;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.test.data.transaction.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.TransactionQuery;
import com.bernardomg.association.transaction.service.TransactionService;
import com.bernardomg.association.transaction.test.config.factory.Transactions;
import com.bernardomg.association.transaction.test.config.factory.TransactionsQueries;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction service - get all - pagination")
@MultipleTransactionsSameMonth
class ITTransactionServiceGetAllPagination {

    @Autowired
    private TransactionService service;

    public ITTransactionServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("With an active pagination, the returned data is contained in a page")
    void testGetAll_Page_Container() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = Pageable.ofSize(10);

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = service.getAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .as("transactions")
            .isInstanceOf(Page.class);
    }

    @Test
    @DisplayName("With pagination for the first page, it returns the first page")
    void testGetAll_Page1() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = PageRequest.of(0, 1);

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = service.getAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndex(1, Month.JANUARY));
    }

    @Test
    @DisplayName("With pagination for the second page, it returns the second page")
    void testGetAll_Page2() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = PageRequest.of(1, 1);

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = service.getAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndDay(2, Month.JANUARY));
    }

    @Test
    @DisplayName("With an inactive pagination, the returned data is contained in a page")
    void testGetAll_Unpaged_Container() {
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
            .isInstanceOf(Page.class);
    }

}
