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

package com.bernardomg.association.transaction.test.usecase.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.config.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionChange;
import com.bernardomg.association.transaction.test.config.factory.TransactionChanges;
import com.bernardomg.association.transaction.test.config.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.config.factory.TransactionEntities;
import com.bernardomg.association.transaction.test.config.factory.Transactions;
import com.bernardomg.association.transaction.usecase.service.TransactionService;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction service - update")
class ITTransactionServiceUpdate {

    @Autowired
    private TransactionSpringRepository repository;

    @Autowired
    private TransactionService          service;

    public ITTransactionServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With an existing entity, no new entity is persisted")
    @PositiveTransaction
    void testUpdate_AddsNoEntity() {
        final TransactionChange transactionRequest;

        // GIVEN
        transactionRequest = TransactionChanges.descriptionChange();

        // WHEN
        service.update(TransactionConstants.INDEX, transactionRequest);

        Assertions.assertThat(repository.count())
            .as("transactions")
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a transaction containing a decimal value, the values are persisted")
    @PositiveTransaction
    void testUpdate_Decimal_PersistedData() {
        final TransactionChange           transactionRequest;
        final Iterable<TransactionEntity> entities;

        // GIVEN
        transactionRequest = TransactionChanges.decimal();

        // WHEN
        service.update(TransactionConstants.INDEX, transactionRequest);

        // THEN
        entities = repository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(TransactionEntities.decimal());
    }

    @Test
    @DisplayName("With a transaction containing a decimal value, the data is returned")
    @PositiveTransaction
    void testUpdate_Decimal_ReturnedData() {
        final TransactionChange transactionRequest;
        final Transaction       transaction;

        // GIVEN
        transactionRequest = TransactionChanges.decimal();

        // WHEN
        transaction = service.update(TransactionConstants.INDEX, transactionRequest);

        // THEN
        Assertions.assertThat(transaction)
            .isEqualTo(Transactions.decimal());
    }

    @Test
    @DisplayName("With a transaction having padding whitespaces in description, these whitespaces are removed")
    @PositiveTransaction
    void testUpdate_Padded_PersistedData() {
        final TransactionChange           transactionRequest;
        final Iterable<TransactionEntity> entities;

        // GIVEN
        transactionRequest = TransactionChanges.paddedWithWhitespaces();

        // WHEN
        service.update(TransactionConstants.INDEX, transactionRequest);

        // THEN
        entities = repository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(TransactionEntities.valid());
    }

    @Test
    @DisplayName("With a changed entity, the change is persisted")
    @PositiveTransaction
    void testUpdate_PersistedData() {
        final TransactionChange           transactionRequest;
        final Iterable<TransactionEntity> entities;

        // GIVEN
        transactionRequest = TransactionChanges.descriptionChange();

        // WHEN
        service.update(TransactionConstants.INDEX, transactionRequest);

        // THEN
        entities = repository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(TransactionEntities.descriptionChange());
    }

    @Test
    @DisplayName("With a changed entity, the changed data is returned")
    @PositiveTransaction
    void testUpdate_ReturnedData() {
        final TransactionChange transactionRequest;
        final Transaction       transaction;

        // GIVEN
        transactionRequest = TransactionChanges.descriptionChange();

        // WHEN
        transaction = service.update(TransactionConstants.INDEX, transactionRequest);

        // THEN
        Assertions.assertThat(transaction)
            .isEqualTo(Transactions.descriptionChange());
    }

}
