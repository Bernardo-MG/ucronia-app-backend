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

package com.bernardomg.association.transaction.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionsQueries;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("Transaction service - get all")
class TestTransactionServiceGetAll {

    @InjectMocks
    private DefaultTransactionService service;

    @Mock
    private TransactionRepository     transactionRepository;

    public TestTransactionServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("When there is data it is returned")
    void testGetAll() {
        final Page<Transaction> transactions;
        final Page<Transaction> existing;
        final TransactionQuery  transactionQuery;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        transactionQuery = TransactionsQueries.empty();

        existing = new Page<>(List.of(Transactions.positive()), 0, 0, 0, 0, 0, false, false, sorting);
        given(transactionRepository.findAll(transactionQuery, pagination, sorting)).willReturn(existing);

        // WHEN
        transactions = service.getAll(transactionQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("transactions")
            .containsExactly(Transactions.positive());
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetAll_NoData() {
        final Page<Transaction> transactions;
        final Page<Transaction> existing;
        final TransactionQuery  transactionQuery;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        transactionQuery = TransactionsQueries.empty();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(transactionRepository.findAll(transactionQuery, pagination, sorting)).willReturn(existing);

        // WHEN
        transactions = service.getAll(transactionQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("transactions")
            .isEmpty();
    }

}
