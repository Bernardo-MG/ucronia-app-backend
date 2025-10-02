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
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionService;
import com.bernardomg.exception.MissingIdException;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("Transaction service - update")
class TestTransactionServiceUpdate {

    @InjectMocks
    private DefaultTransactionService service;

    @Mock
    private TransactionRepository     transactionRepository;

    @Test
    @DisplayName("With a member with padded name, the member is persisted")
    void testCreate_Padded_PersistedData() {
        final Transaction transaction;

        // GIVEN
        transaction = Transactions.padded();

        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        service.update(transaction);

        // THEN
        verify(transactionRepository).save(Transactions.positive());
    }

    @Test
    @DisplayName("With a valid transaction, it is persisted")
    void testCreate_PersistedData() {
        final Transaction transaction;

        // GIVEN
        transaction = Transactions.positive();

        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        service.update(transaction);

        // THEN
        verify(transactionRepository).save(Transactions.positive());
    }

    @Test
    @DisplayName("With a valid transaction, it is returned")
    void testCreate_ReturnedData() {
        final Transaction transaction;
        final Transaction updated;

        // GIVEN
        transaction = Transactions.positive();

        given(transactionRepository.save(Transactions.positive())).willReturn(Transactions.positive());
        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        updated = service.update(transaction);

        // THEN
        Assertions.assertThat(updated)
            .as("transaction")
            .isEqualTo(Transactions.positive());
    }

    @Test
    @DisplayName("With the transaction with a future date, it throws an exception")
    void testUpdate_Future() {
        final ThrowingCallable execution;
        final FieldFailure     failure;

        // WHEN
        execution = () -> service.update(Transactions.future());

        // THEN
        failure = new FieldFailure("invalid", "date", "date.invalid", FeeConstants.PAYMENT_DATE_FUTURE);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("With a not existing entity, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Transaction      transaction;
        final ThrowingCallable execution;

        // GIVEN
        transaction = Transactions.positive();

        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(false);

        // WHEN
        execution = () -> service.update(transaction);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingIdException.class);
    }

}
