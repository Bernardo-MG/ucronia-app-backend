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

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.request.DtoTransactionCreationQuery;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.transaction.service.TransactionService;

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
    @DisplayName("Adds an entity when creating for the first day of the year")
    public void testCreate_FirstDay_AddsEntity() {
        final DtoTransactionCreationQuery transaction;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 0, 1));

        service.create(transaction);

        Assertions.assertThat(repository.count())
            .isOne();
    }

    @Test
    @DisplayName("Persists the data when creating for the first day of the year")
    public void testCreate_FirstDay_PersistedData() {
        final DtoTransactionCreationQuery transaction;
        final PersistentTransaction       entity;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 0, 1));

        service.create(transaction);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getId())
            .isNotNull();
        Assertions.assertThat(entity.getDescription())
            .isEqualTo("Transaction");
        Assertions.assertThat(entity.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 0, 1).getTime());
        Assertions.assertThat(entity.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("Returns the created data when creating for the first day of the year")
    public void testCreate_FirstDay_ReturnedData() {
        final Transaction                 result;
        final DtoTransactionCreationQuery transaction;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 0, 1));

        result = service.create(transaction);

        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getDescription())
            .isEqualTo("Transaction");
        Assertions.assertThat(result.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 0, 1).getTime());
        Assertions.assertThat(result.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("Adds an entity when creating during the year")
    public void testCreate_InYear_AddsEntity() {
        final DtoTransactionCreationQuery transaction;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        service.create(transaction);

        Assertions.assertThat(repository.count())
            .isOne();
    }

    @Test
    @DisplayName("Persists the data when creating during the year")
    public void testCreate_InYear_PersistedData() {
        final DtoTransactionCreationQuery transaction;
        final PersistentTransaction       entity;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        service.create(transaction);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getId())
            .isNotNull();
        Assertions.assertThat(entity.getDescription())
            .isEqualTo("Transaction");
        Assertions.assertThat(entity.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 1).getTime());
        Assertions.assertThat(entity.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("Returns the created data when creating during the year")
    public void testCreate_InYear_ReturnedData() {
        final Transaction                 result;
        final DtoTransactionCreationQuery transaction;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        result = service.create(transaction);

        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getDescription())
            .isEqualTo("Transaction");
        Assertions.assertThat(result.getDate()
            .getTime())
            .isEqualTo(new GregorianCalendar(2020, 1, 1).getTime());
        Assertions.assertThat(result.getAmount())
            .isEqualTo(1f);
    }

    @Test
    @DisplayName("Adds entities when creating the same twice")
    public void testCreate_Repeat_AddsEntity() {
        final DtoTransactionCreationQuery transaction;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription("Transaction");
        transaction.setAmount(1f);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        service.create(transaction);

        service.create(transaction);

        Assertions.assertThat(repository.count())
            .isEqualTo(2);
    }

}
