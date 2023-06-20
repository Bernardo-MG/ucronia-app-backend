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
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.association.test.config.factory.ModelFactory;
import com.bernardomg.association.test.transaction.assertion.TransactionAssertions;
import com.bernardomg.association.transaction.model.ImmutableTransaction;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.transaction.service.TransactionService;

@IntegrationTest
@DisplayName("Transaction service - get one")
public class ITTransactionServiceGetOne {

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
    public void testGetOne() {
        final Optional<Transaction> result;
        final Transaction           data;

        result = service.getOne(1L);

        Assertions.assertThat(result)
            .isPresent();

        data = result.get();

        TransactionAssertions.isEqualTo(data, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With a value around zero, the related entity is returned")
    @Sql({ "/db/queries/transaction/negative.sql" })
    public void testGetOne_AroundZero(final Float amount) {
        final Optional<Transaction> result;
        final Transaction           data;

        repository.save(ModelFactory.transaction(amount));

        result = service.getOne(1L);

        Assertions.assertThat(result)
            .isPresent();

        data = result.get();

        TransactionAssertions.isEqualTo(data, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(-1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal value, the related entity is returned")
    @Sql({ "/db/queries/transaction/negative.sql" })
    public void testGetOne_Decimal(final Float amount) {
        final Optional<Transaction> result;
        final Transaction           data;

        repository.save(ModelFactory.transaction(amount));

        result = service.getOne(1L);

        Assertions.assertThat(result)
            .isPresent();

        data = result.get();

        TransactionAssertions.isEqualTo(data, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(-1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());
    }

    @Test
    @DisplayName("Returns the correct data when reading a negative value")
    @Sql({ "/db/queries/transaction/negative.sql" })
    public void testGetOne_Negative() {
        final Optional<Transaction> result;
        final Transaction           data;

        result = service.getOne(1L);

        Assertions.assertThat(result)
            .isPresent();

        data = result.get();

        TransactionAssertions.isEqualTo(data, ImmutableTransaction.builder()
            .description("Transaction 1")
            .amount(-1f)
            .date(new GregorianCalendar(2020, 0, 1))
            .build());
    }

    @Test
    @DisplayName("When reading a single entity with an invalid id, no entity is returned")
    public void testGetOne_NotExisting() {
        final Optional<Transaction> result;

        result = service.getOne(1L);

        Assertions.assertThat(result)
            .isNotPresent();
    }

}
