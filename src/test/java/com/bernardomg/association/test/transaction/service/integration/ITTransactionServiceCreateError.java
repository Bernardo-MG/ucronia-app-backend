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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.transaction.util.model.TransactionsCreate;
import com.bernardomg.association.transaction.model.request.TransactionCreate;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.transaction.service.TransactionService;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Transaction service - create errors")
@Sql({ "/db/queries/member/single.sql" })
class ITTransactionServiceCreateError {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionService    service;

    public ITTransactionServiceCreateError() {
        super();
    }

    @Test
    @DisplayName("With a transaction missing the amount, an exception is thrown")
    void testCreate_MissingAmount() {
        final TransactionCreate transactionRequest;
        final ThrowingCallable  execution;

        transactionRequest = TransactionsCreate.missingAmount();

        execution = () -> {
            service.create(transactionRequest);
            repository.flush();
        };

        // TODO: Shouldn't this be a validation error?
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("With a transaction missing the date, an exception is thrown")
    void testCreate_MissingDate() {
        final TransactionCreate transactionRequest;
        final ThrowingCallable  execution;

        transactionRequest = TransactionsCreate.missingDate();

        execution = () -> {
            service.create(transactionRequest);
            repository.flush();
        };

        // TODO: Shouldn't this be a validation error?
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("With a transaction missing the description, an exception is thrown")
    void testCreate_MissingDescription() {
        final TransactionCreate transactionRequest;
        final ThrowingCallable  execution;

        transactionRequest = TransactionsCreate.missingDescription();

        execution = () -> {
            service.create(transactionRequest);
            repository.flush();
        };

        // TODO: Shouldn't this be a validation error?
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

}
