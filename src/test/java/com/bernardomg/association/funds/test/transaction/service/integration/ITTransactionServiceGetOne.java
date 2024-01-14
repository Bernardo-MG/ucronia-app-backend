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

package com.bernardomg.association.funds.test.transaction.service.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.funds.test.transaction.config.factory.TransactionConstants;
import com.bernardomg.association.funds.test.transaction.config.factory.TransactionEntities;
import com.bernardomg.association.funds.test.transaction.config.factory.Transactions;
import com.bernardomg.association.funds.test.transaction.configuration.NegativeTransaction;
import com.bernardomg.association.funds.test.transaction.configuration.PositiveTransaction;
import com.bernardomg.association.model.transaction.Transaction;
import com.bernardomg.association.persistence.transaction.repository.TransactionRepository;
import com.bernardomg.association.service.transaction.TransactionService;
import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction service - get one")
class ITTransactionServiceGetOne {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionService    service;

    public ITTransactionServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("With a valid id, the related entity is returned")
    @PositiveTransaction
    void testGetOne() {
        final Optional<Transaction> transactionOptional;

        // WHEN
        transactionOptional = service.getOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .contains(Transactions.valid());
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With a value around zero, the related entity is returned")
    void testGetOne_AroundZero(final Float amount) {
        final Optional<Transaction> transactionOptional;

        // GIVEN
        repository.save(TransactionEntities.forAmount(amount));

        // WHEN
        transactionOptional = service.getOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .contains(Transactions.forAmount(amount));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal value, the related entity is returned")
    void testGetOne_Decimal(final Float amount) {
        final Optional<Transaction> transactionOptional;

        // GIVEN
        repository.save(TransactionEntities.forAmount(amount));

        // WHEN
        transactionOptional = service.getOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .contains(Transactions.forAmount(amount));
    }

    @Test
    @DisplayName("Returns the correct data when reading a negative value")
    @NegativeTransaction
    void testGetOne_Negative() {
        final Optional<Transaction> transactionOptional;

        // WHEN
        transactionOptional = service.getOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .contains(Transactions.forAmount(-1F));
    }

}
