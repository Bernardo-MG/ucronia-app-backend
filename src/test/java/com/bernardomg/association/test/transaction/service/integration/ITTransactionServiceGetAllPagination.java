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

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.test.transaction.util.model.TransactionsQuery;
import com.bernardomg.association.transaction.model.ImmutableTransaction;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.request.TransactionQuery;
import com.bernardomg.association.transaction.service.TransactionService;

@IntegrationTest
@DisplayName("Transaction service - get all - pagination")
@Sql({ "/db/queries/transaction/multiple.sql" })
public class ITTransactionServiceGetAllPagination {

    @Autowired
    private TransactionService service;

    public ITTransactionServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("With an active pagination, the returned data is contained in a page")
    public void testGetAll_Page_Container() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        pageable = Pageable.ofSize(10);

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable);

        Assertions.assertThat(transactions)
            .isInstanceOf(Page.class);
    }

    @Test
    @DisplayName("With pagination for the first page, it returns the first page")
    public void testGetAll_Page1() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Transaction           transaction;
        final Pageable              pageable;

        pageable = PageRequest.of(0, 1);

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable);

        Assertions.assertThat(IterableUtils.size(transactions))
            .isEqualTo(1);

        transaction = transactions.iterator()
            .next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());
    }

    @Test
    @DisplayName("With pagination for the second page, it returns the second page")
    public void testGetAll_Page2() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Transaction           transaction;
        final Pageable              pageable;

        pageable = PageRequest.of(1, 1);

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable);

        Assertions.assertThat(IterableUtils.size(transactions))
            .isEqualTo(1);

        transaction = transactions.iterator()
            .next();
        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 2")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 2))
            .build());
    }

    @Test
    @DisplayName("With an inactive pagination, the returned data is contained in a page")
    public void testGetAll_Unpaged_Container() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        pageable = Pageable.unpaged();

        transactionQuery = TransactionsQuery.empty();

        transactions = service.getAll(transactionQuery, pageable);

        Assertions.assertThat(transactions)
            .isInstanceOf(Page.class);
    }

}
