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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.request.DtoTransactionCreationQuery;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.transaction.service.TransactionService;

@IntegrationTest
@DisplayName("Transaction service - create with decimals")
public class ITTransactionServiceCreateDecimal {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionService    service;

    public ITTransactionServiceCreateDecimal() {
        super();
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("Persists the data with a decimal value")
    public void testCreate_Decimal_Low_PersistedData(final Float amount) {
        final DtoTransactionCreationQuery transaction;
        final PersistentTransaction       entity;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription("Transaction");
        transaction.setAmount(amount);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        service.create(transaction);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getAmount())
            .isEqualTo(amount);
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("Returns the created data with a decimal value")
    public void testCreate_Decimal_Low_ReturnedData(final Float amount) {
        final Transaction                 result;
        final DtoTransactionCreationQuery transaction;

        transaction = new DtoTransactionCreationQuery();
        transaction.setDescription("Transaction");
        transaction.setAmount(amount);
        transaction.setDate(new GregorianCalendar(2020, 1, 1));

        result = service.create(transaction);

        Assertions.assertThat(result.getAmount())
            .isEqualTo(amount);
    }

}
