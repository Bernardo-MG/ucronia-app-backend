/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.transaction.test.service.unit;

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

import com.bernardomg.association.transaction.domain.exception.MissingTransactionException;
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Transaction type service - update")
class TestTransactionTypeServiceUpdate {

    @InjectMocks
    private DefaultTransactionTypeService service;

    @Mock
    private TransactionTypeRepository     transactionTypeRepository;

    @Test
    @DisplayName("With a valid transaction type, it is persisted")
    void testCreate_PersistedData() {
        final TransactionType transactionType;

        // GIVEN
        transactionType = TransactionTypes.valid();

        given(transactionTypeRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        service.update(transactionType);

        // THEN
        verify(transactionTypeRepository).save(transactionType);
    }

    @Test
    @DisplayName("With a valid transaction type, it is returned")
    void testCreate_ReturnedData() {
        final TransactionType transactionType;
        final TransactionType updated;

        // GIVEN
        transactionType = TransactionTypes.valid();

        given(transactionTypeRepository.save(transactionType)).willReturn(transactionType);
        given(transactionTypeRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        updated = service.update(transactionType);

        // THEN
        Assertions.assertThat(updated)
            .as("transaction")
            .isEqualTo(transactionType);
    }

    @Test
    @DisplayName("With a not existing transaction type, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final TransactionType  transactionType;
        final ThrowingCallable execution;

        // GIVEN
        transactionType = TransactionTypes.valid();

        given(transactionTypeRepository.exists(TransactionConstants.INDEX)).willReturn(false);

        // WHEN
        execution = () -> service.update(transactionType);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingTransactionException.class);
    }

}
