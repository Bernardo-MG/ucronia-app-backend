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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.funds.test.transaction.configuration.PositiveTransaction;
import com.bernardomg.association.funds.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.funds.test.transaction.util.model.TransactionChanges;
import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.association.funds.transaction.model.TransactionChange;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.funds.transaction.service.TransactionService;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction service - update")
class ITTransactionServiceUpdate {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionService    service;

    public ITTransactionServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With an existing entity, no new entity is persisted")
    @PositiveTransaction
    void testUpdate_AddsNoEntity() {
        final TransactionChange transactionRequest;

        transactionRequest = TransactionChanges.descriptionChange();

        service.update(1L, transactionRequest);

        Assertions.assertThat(repository.count())
            .as("transactions")
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a transaction containing a decimal value, the values are persisted")
    @PositiveTransaction
    void testUpdate_Decimal_PersistedData() {
        final TransactionChange     transactionRequest;
        final PersistentTransaction transaction;

        transactionRequest = TransactionChanges.decimal();

        service.update(1L, transactionRequest);
        transaction = repository.findAll()
            .iterator()
            .next();

        TransactionAssertions.isEqualTo(transaction, PersistentTransaction.builder()
            .description("Transaction")
            .amount(1.2f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build());
    }

    @Test
    @DisplayName("With a transaction containing a decimal value, the data is returned")
    @PositiveTransaction
    void testUpdate_Decimal_ReturnedData() {
        final TransactionChange transactionRequest;
        final Transaction       transaction;

        transactionRequest = TransactionChanges.decimal();

        transaction = service.update(1L, transactionRequest);

        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction")
            .amount(1.2f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build());
    }

    @Test
    @DisplayName("With a transaction having padding whitespaces in description, these whitespaces are removed")
    @PositiveTransaction
    void testUpdate_Padded_PersistedData() {
        final TransactionChange     transactionRequest;
        final PersistentTransaction transaction;

        transactionRequest = TransactionChanges.paddedWithWhitespaces();

        service.update(1L, transactionRequest);
        transaction = repository.findAll()
            .iterator()
            .next();

        TransactionAssertions.isEqualTo(transaction, PersistentTransaction.builder()
            .description("Transaction 123")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build());
    }

    @Test
    @DisplayName("With a changed entity, the change is persisted")
    @PositiveTransaction
    void testUpdate_PersistedData() {
        final TransactionChange     transactionRequest;
        final PersistentTransaction transaction;

        transactionRequest = TransactionChanges.descriptionChange();

        service.update(1L, transactionRequest);
        transaction = repository.findAll()
            .iterator()
            .next();

        TransactionAssertions.isEqualTo(transaction, PersistentTransaction.builder()
            .description("Transaction 123")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build());
    }

    @Test
    @DisplayName("With a changed entity, the changed data is returned")
    @PositiveTransaction
    void testUpdate_ReturnedData() {
        final TransactionChange transactionRequest;
        final Transaction       transaction;

        transactionRequest = TransactionChanges.descriptionChange();

        transaction = service.update(1L, transactionRequest);

        TransactionAssertions.isEqualTo(transaction, Transaction.builder()
            .description("Transaction 123")
            .amount(1f)
            .date(LocalDate.of(2020, Month.FEBRUARY, 1))
            .build());
    }

}
