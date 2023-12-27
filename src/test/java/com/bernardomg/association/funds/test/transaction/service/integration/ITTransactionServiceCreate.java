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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.funds.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.funds.test.transaction.util.model.PersistentTransactions;
import com.bernardomg.association.funds.test.transaction.util.model.TransactionChanges;
import com.bernardomg.association.funds.test.transaction.util.model.Transactions;
import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.association.funds.transaction.model.TransactionChange;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.funds.transaction.service.TransactionService;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction service - create")
class ITTransactionServiceCreate {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionService    service;

    public ITTransactionServiceCreate() {
        super();
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal value, the transaction is persisted")
    void testCreate_Decimal_PersistedData(final Float amount) {
        final TransactionChange     transactionRequest;
        final PersistentTransaction entity;

        transactionRequest = TransactionChanges.amount(amount);

        service.create(transactionRequest);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getAmount())
            .as("amount")
            .isEqualTo(amount);
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal value, the persisted transaction is returned")
    void testCreate_Decimal_ReturnedData(final Float amount) {
        final TransactionChange transactionRequest;
        final Transaction       transaction;

        transactionRequest = TransactionChanges.amount(amount);

        transaction = service.create(transactionRequest);

        Assertions.assertThat(transaction.getAmount())
            .as("amount")
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("With a valid transaction, the transaction is persisted")
    void testCreate_FirstDay_AddsEntity() {
        final TransactionChange     transactionRequest;
        final PersistentTransaction entity;

        transactionRequest = TransactionChanges.valid();

        service.create(transactionRequest);

        Assertions.assertThat(repository.count())
            .as("transactions")
            .isOne();

        entity = repository.findAll()
            .iterator()
            .next();

        TransactionAssertions.isEqualTo(entity, PersistentTransactions.valid());
    }

    @Test
    @DisplayName("With a valid transaction, the persisted data is returned")
    void testCreate_FirstDay_ReturnedData() {
        final TransactionChange transactionRequest;
        final Transaction       transaction;

        transactionRequest = TransactionChanges.valid();

        transaction = service.create(transactionRequest);

        TransactionAssertions.isEqualTo(transaction, Transactions.valid());
    }

    @Test
    @DisplayName("With a transaction having padding whitespaces in description, these whitespaces are removed")
    void testCreate_Padded_AddsEntity() {
        final TransactionChange     transactionRequest;
        final PersistentTransaction entity;

        transactionRequest = TransactionChanges.paddedWithWhitespaces();

        service.create(transactionRequest);

        Assertions.assertThat(repository.count())
            .as("transactions")
            .isOne();

        entity = repository.findAll()
            .iterator()
            .next();

        TransactionAssertions.isEqualTo(entity, PersistentTransactions.valid());
    }

    @Test
    @DisplayName("With a repeated creation, two transactions are persisted")
    void testCreate_Repeat_AddsEntity() {
        final TransactionChange transactionRequest;

        transactionRequest = TransactionChanges.inYear();

        service.create(transactionRequest);

        service.create(transactionRequest);

        Assertions.assertThat(repository.count())
            .as("transactions")
            .isEqualTo(2);
    }

}
