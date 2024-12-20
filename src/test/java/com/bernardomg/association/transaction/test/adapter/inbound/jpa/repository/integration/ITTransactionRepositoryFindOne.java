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

package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.configuration.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.configuration.argument.DecimalArgumentsProvider;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.configuration.data.annotation.NegativeTransaction;
import com.bernardomg.association.transaction.configuration.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEntities;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("TransactionRepository - find one")
class ITTransactionRepositoryFindOne {

    @Autowired
    private TransactionRepository       repository;

    @Autowired
    private TransactionSpringRepository springRepository;

    @Test
    @DisplayName("With an existing transaction, it is returned")
    @PositiveTransaction
    void testFindOne() {
        final Optional<Transaction> transactionOptional;

        // WHEN
        transactionOptional = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .contains(Transactions.positive());
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With a value around zero, the related entity is returned")
    void testFindOne_AroundZero(final Float amount) {
        final Optional<Transaction> transactionOptional;

        // GIVEN
        springRepository.save(TransactionEntities.forAmount(amount));

        // WHEN
        transactionOptional = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .contains(Transactions.forAmount(amount));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal value, the related entity is returned")
    void testFindOne_Decimal(final Float amount) {
        final Optional<Transaction> transactionOptional;

        // GIVEN
        springRepository.save(TransactionEntities.forAmount(amount));

        // WHEN
        transactionOptional = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .contains(Transactions.forAmount(amount));
    }

    @Test
    @DisplayName("Returns the correct data when reading a negative value")
    @NegativeTransaction
    void testFindOne_Negative() {
        final Optional<Transaction> transactionOptional;

        // WHEN
        transactionOptional = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .contains(Transactions.forAmount(-1F));
    }

    @Test
    @DisplayName("With no transaction, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Transaction> transactionOptional;

        // WHEN
        transactionOptional = repository.findOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(transactionOptional)
            .isEmpty();
    }

}
