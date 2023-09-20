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

package com.bernardomg.association.funds.test.transaction.service.integration;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.funds.test.transaction.util.assertion.TransactionAssertions;
import com.bernardomg.association.funds.test.transaction.util.model.PersistentTransactions;
import com.bernardomg.association.funds.transaction.model.ImmutableTransaction;
import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.funds.transaction.service.TransactionService;
import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
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
    @Sql({ "/db/queries/transaction/single.sql" })
    void testGetOne() {
        final Optional<Transaction> transactionOptional;
        final Transaction           transaction;

        transactionOptional = service.getOne(1L);

        Assertions.assertThat(transactionOptional)
            .isPresent();

        transaction = transactionOptional.get();

        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .build());
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With a value around zero, the related entity is returned")
    @Sql({ "/db/queries/transaction/negative.sql" })
    void testGetOne_AroundZero(final Float amount) {
        final Optional<Transaction> transactionOptional;
        final Transaction           transaction;

        repository.save(PersistentTransactions.transaction(amount));

        transactionOptional = service.getOne(1L);

        Assertions.assertThat(transactionOptional)
            .isPresent();

        transaction = transactionOptional.get();

        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(-1f)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .build());
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal value, the related entity is returned")
    @Sql({ "/db/queries/transaction/negative.sql" })
    void testGetOne_Decimal(final Float amount) {
        final Optional<Transaction> transactionOptional;
        final Transaction           transaction;

        repository.save(PersistentTransactions.transaction(amount));

        transactionOptional = service.getOne(1L);

        Assertions.assertThat(transactionOptional)
            .isPresent();

        transaction = transactionOptional.get();

        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(-1f)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .build());
    }

    @Test
    @DisplayName("Returns the correct data when reading a negative value")
    @Sql({ "/db/queries/transaction/negative.sql" })
    void testGetOne_Negative() {
        final Optional<Transaction> transactionOptional;
        final Transaction           transaction;

        transactionOptional = service.getOne(1L);

        Assertions.assertThat(transactionOptional)
            .isPresent();

        transaction = transactionOptional.get();

        TransactionAssertions.isEqualTo(transaction, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(-1f)
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .build());
    }

}