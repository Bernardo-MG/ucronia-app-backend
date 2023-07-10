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

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.transaction.util.model.TransactionsUpdate;
import com.bernardomg.association.transaction.model.request.TransactionUpdate;
import com.bernardomg.association.transaction.service.TransactionService;
import com.bernardomg.exception.InvalidIdException;

@IntegrationTest
@DisplayName("Transaction service - update errors")
class ITTransactionServiceUpdateError {

    @Autowired
    private TransactionService service;

    public ITTransactionServiceUpdateError() {
        super();
    }

    @Test
    @DisplayName("With a not existing entity, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final TransactionUpdate transactionRequest;
        final ThrowingCallable  execution;

        transactionRequest = TransactionsUpdate.descriptionChange();

        execution = () -> service.update(10L, transactionRequest);

        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(InvalidIdException.class);
    }

}
