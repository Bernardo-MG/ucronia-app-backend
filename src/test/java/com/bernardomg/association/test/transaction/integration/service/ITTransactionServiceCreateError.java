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
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.transaction.model.request.DtoTransactionCreationQuery;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.transaction.service.TransactionService;

@IntegrationTest
@DisplayName("Transaction service - create errors")
@Sql({ "/db/queries/member/single.sql" })
public class ITTransactionServiceCreateError {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionService    service;

    public ITTransactionServiceCreateError() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the amount is missing")
    public void testCreate_MissingAmount() {
        final DtoTransactionCreationQuery transaction;
        final ThrowingCallable            executable;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription("Transaction");
        transaction.setAmount(null);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        executable = () -> {
            service.create(transaction);
            repository.flush();
        };

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Throws an exception when the date is missing")
    public void testCreate_MissingDate() {
        final DtoTransactionCreationQuery transaction;
        final ThrowingCallable            executable;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(null);

        executable = () -> {
            service.create(transaction);
            repository.flush();
        };

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Throws an exception when the description is missing")
    public void testCreate_MissingName() {
        final DtoTransactionCreationQuery transaction;
        final ThrowingCallable            executable;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription(null);
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        executable = () -> {
            service.create(transaction);
            repository.flush();
        };

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

}
