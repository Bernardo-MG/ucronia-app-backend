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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.transaction.configuration.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionsQueries;
import com.bernardomg.test.configuration.annotation.IntegrationTest;
import com.bernardomg.test.pagination.AbstractPaginationIT;

@IntegrationTest
@DisplayName("TransactionRepository - get all - filtered")
@MultipleTransactionsSameMonth
class ITTransactionRepositoryFindAllPagination extends AbstractPaginationIT<Transaction> {

    @Autowired
    private TransactionRepository repository;

    public ITTransactionRepositoryFindAllPagination() {
        super(5);
    }

    @Override
    protected final Iterable<Transaction> read(final Pageable pageable) {
        return repository.findAll(TransactionsQueries.empty(), pageable);
    }

    @Test
    @DisplayName("With pagination for the first page, it returns the first page")
    void testFindAll_Page1() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = PageRequest.of(0, 1);

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = repository.findAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndex(1, Month.JANUARY));
    }

    @Test
    @DisplayName("With pagination for the second page, it returns the second page")
    void testFindAll_Page2() {
        final Iterable<Transaction> transactions;
        final TransactionQuery      transactionQuery;
        final Pageable              pageable;

        // GIVEN
        pageable = PageRequest.of(1, 1);

        transactionQuery = TransactionsQueries.empty();

        // WHEN
        transactions = repository.findAll(transactionQuery, pageable);

        // THEN
        Assertions.assertThat(transactions)
            .containsExactly(Transactions.forIndexAndDay(2, Month.JANUARY));
    }

}
