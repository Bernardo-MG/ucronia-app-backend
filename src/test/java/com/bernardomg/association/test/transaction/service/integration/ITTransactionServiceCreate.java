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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.association.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.test.transaction.util.model.TransactionsCreate;
import com.bernardomg.association.transaction.model.ImmutableTransaction;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.request.TransactionCreate;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.transaction.service.TransactionService;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
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
        final TransactionCreate     transactionRequest;
        final PersistentTransaction entity;

        transactionRequest = TransactionsCreate.amount(amount);

        service.create(transactionRequest);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getAmount())
            .isEqualTo(amount);
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal value, the persisted transaction is returned")
    void testCreate_Decimal_ReturnedData(final Float amount) {
        final TransactionCreate transactionRequest;
        final Transaction       transaction;

        transactionRequest = TransactionsCreate.amount(amount);

        transaction = service.create(transactionRequest);

        Assertions.assertThat(transaction.getAmount())
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("With a transaction for the first day of the year, the member is persisted")
    void testCreate_FirstDay_AddsEntity() {
        final TransactionCreate     transactionRequest;
        final PersistentTransaction entity;

        transactionRequest = TransactionsCreate.firstDay();

        service.create(transactionRequest);

        Assertions.assertThat(repository.count())
            .isOne();

        entity = repository.findAll()
            .iterator()
            .next();

        TransactionAssertions.isEqualTo(entity, PersistentTransaction.builder()
            .description("Transaction")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());
    }

    @Test
    @DisplayName("With a transaction for the first day of the year, the persisted data is returned")
    void testCreate_FirstDay_ReturnedData() {
        final TransactionCreate transactionRequest;
        final Transaction       transaction;

        transactionRequest = TransactionsCreate.firstDay();

        transaction = service.create(transactionRequest);

        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());
    }

    @Test
    @DisplayName("With a transaction for the a day during year, the member is persisted")
    void testCreate_InYear_AddsEntity() {
        final TransactionCreate     transactionRequest;
        final PersistentTransaction entity;

        transactionRequest = TransactionsCreate.inYear();

        service.create(transactionRequest);

        Assertions.assertThat(repository.count())
            .isOne();

        entity = repository.findAll()
            .iterator()
            .next();

        TransactionAssertions.isEqualTo(entity, PersistentTransaction.builder()
            .description("Transaction")
            .amount(1f)
            .date(new GregorianCalendar(2020, 1, 1))
            .build());
    }

    @Test
    @DisplayName("With a transaction for the a day during year, the persisted member is created")
    void testCreate_InYear_ReturnedData() {
        final TransactionCreate transactionRequest;
        final Transaction       transaction;

        transactionRequest = TransactionsCreate.inYear();

        transaction = service.create(transactionRequest);

        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction")
            .amount(1f)
            .date(new GregorianCalendar(2020, 1, 1))
            .build());
    }

    @Test
    @DisplayName("With a repeated creation, two members are persisted")
    void testCreate_Repeat_AddsEntity() {
        final TransactionCreate transactionRequest;

        transactionRequest = TransactionsCreate.inYear();

        service.create(transactionRequest);

        service.create(transactionRequest);

        Assertions.assertThat(repository.count())
            .isEqualTo(2);
    }

}
