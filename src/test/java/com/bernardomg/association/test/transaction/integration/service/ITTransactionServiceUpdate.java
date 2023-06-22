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

package com.bernardomg.association.test.transaction.integration.service;

import java.util.GregorianCalendar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.transaction.assertion.TransactionAssertions;
import com.bernardomg.association.transaction.model.ImmutableTransaction;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.request.ValidatedTransactionUpdate;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.transaction.service.TransactionService;

@IntegrationTest
@DisplayName("Transaction service - update")
@Sql({ "/db/queries/transaction/single.sql" })
public class ITTransactionServiceUpdate {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionService    service;

    public ITTransactionServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With an existing entity, no new entity is persisted")
    public void testUpdate_AddsNoEntity() {
        final ValidatedTransactionUpdate transactionRequest;

        transactionRequest = new ValidatedTransactionUpdate();
        transactionRequest.setDescription("Transaction 123");
        transactionRequest.setAmount(1f);
        transactionRequest.setDate(new GregorianCalendar(2020, 1, 1));

        service.update(1L, transactionRequest);

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a transaction containing a decimal value, the values are persisted")
    public void testUpdate_Decimal_PersistedData() {
        final ValidatedTransactionUpdate transactionRequest;
        final PersistentTransaction      transaction;

        transactionRequest = new ValidatedTransactionUpdate();
        transactionRequest.setDescription("Transaction");
        transactionRequest.setAmount(1.2f);
        transactionRequest.setDate(new GregorianCalendar(2020, 1, 1));

        service.update(1L, transactionRequest);
        transaction = repository.findAll()
            .iterator()
            .next();

        TransactionAssertions.isEqualTo(transaction, PersistentTransaction.builder()
            .description("Transaction")
            .amount(1.2f)
            .date(new GregorianCalendar(2020, 1, 1))
            .build());
    }

    @Test
    @DisplayName("With a transaction containing a decimal value, the data is returned")
    public void testUpdate_Decimal_ReturnedData() {
        final ValidatedTransactionUpdate transactionRequest;
        final Transaction                transaction;

        transactionRequest = new ValidatedTransactionUpdate();
        transactionRequest.setDescription("Transaction");
        transactionRequest.setAmount(1.2f);
        transactionRequest.setDate(new GregorianCalendar(2020, 1, 1));

        transaction = service.update(1L, transactionRequest);

        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction")
            .amount(1.2f)
            .date(new GregorianCalendar(2020, 1, 1))
            .build());
    }

    @Test
    @DisplayName("With a not existing entity, a new entity is persisted")
    public void testUpdate_NotExisting_AddsEntity() {
        final ValidatedTransactionUpdate transactionRequest;

        transactionRequest = new ValidatedTransactionUpdate();
        transactionRequest.setDescription("Transaction 123");
        transactionRequest.setAmount(1f);
        transactionRequest.setDate(new GregorianCalendar(2020, 1, 1));

        service.update(10L, transactionRequest);

        Assertions.assertThat(repository.count())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With a changed entity, the change is persisted")
    public void testUpdate_PersistedData() {
        final ValidatedTransactionUpdate transactionRequest;
        final PersistentTransaction      transaction;

        transactionRequest = new ValidatedTransactionUpdate();
        transactionRequest.setDescription("Transaction 123");
        transactionRequest.setAmount(1f);
        transactionRequest.setDate(new GregorianCalendar(2020, 1, 1));

        service.update(1L, transactionRequest);
        transaction = repository.findAll()
            .iterator()
            .next();

        TransactionAssertions.isEqualTo(transaction, PersistentTransaction.builder()
            .description("Transaction 123")
            .amount(1f)
            .date(new GregorianCalendar(2020, 1, 1))
            .build());
    }

    @Test
    @DisplayName("With a changed entity, the changed data is returned")
    public void testUpdate_ReturnedData() {
        final ValidatedTransactionUpdate transactionRequest;
        final Transaction                transaction;

        transactionRequest = new ValidatedTransactionUpdate();
        transactionRequest.setDescription("Transaction 123");
        transactionRequest.setAmount(1f);
        transactionRequest.setDate(new GregorianCalendar(2020, 1, 1));

        transaction = service.update(1L, transactionRequest);

        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 123")
            .amount(1f)
            .date(new GregorianCalendar(2020, 1, 1))
            .build());
    }

}
