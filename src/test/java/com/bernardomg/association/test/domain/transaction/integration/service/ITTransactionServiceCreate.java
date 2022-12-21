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

package com.bernardomg.association.test.domain.transaction.integration.service;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.domain.transaction.model.DtoTransaction;
import com.bernardomg.association.domain.transaction.model.PersistentTransaction;
import com.bernardomg.association.domain.transaction.model.Transaction;
import com.bernardomg.association.domain.transaction.repository.TransactionRepository;
import com.bernardomg.association.domain.transaction.service.TransactionService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction service - create")
public class ITTransactionServiceCreate {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionService    service;

    public ITTransactionServiceCreate() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when creating")
    public void testCreate_AddsEntity() {
        final DtoTransaction transaction;

        transaction = new DtoTransaction();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        service.create(transaction);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Persists the data with decimal values")
    public void testCreate_Decimal_PersistedData() {
        final DtoTransaction        transaction;
        final PersistentTransaction entity;

        transaction = new DtoTransaction();
        transaction.setDescription("Transaction");
        transaction.setAmount(1.2f);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        service.create(transaction);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("Transaction", entity.getDescription());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), entity.getDate()
            .toInstant());
        Assertions.assertEquals(1.2f, entity.getAmount());
    }

    @Test
    @DisplayName("Returns the created data with decimal values")
    public void testCreate_Decimal_ReturnedData() {
        final Transaction    result;
        final DtoTransaction transaction;

        transaction = new DtoTransaction();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        result = service.create(transaction);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Transaction", result.getDescription());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertEquals(1f, result.getAmount());
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final DtoTransaction        transaction;
        final PersistentTransaction entity;

        transaction = new DtoTransaction();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        service.create(transaction);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("Transaction", entity.getDescription());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), entity.getDate()
            .toInstant());
        Assertions.assertEquals(1f, entity.getAmount());
    }

    @Test
    @DisplayName("Adds entities when creating the same twice")
    public void testCreate_Repeat_AddsEntity() {
        final DtoTransaction transaction;

        transaction = new DtoTransaction();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        service.create(transaction);

        service.create(transaction);

        Assertions.assertEquals(2L, repository.count());
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final Transaction    result;
        final DtoTransaction transaction;

        transaction = new DtoTransaction();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        result = service.create(transaction);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Transaction", result.getDescription());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertEquals(1f, result.getAmount());
    }

}
