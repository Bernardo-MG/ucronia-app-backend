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

package com.bernardomg.association.funds.test.transaction.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.funds.test.transaction.util.model.PersistentTransactions;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Transaction repository - sum all")
class ITTransactionRepositorySumAll {

    @Autowired
    private TransactionRepository repository;

    public ITTransactionRepositorySumAll() {
        super();
    }

    @Test
    @DisplayName("With multiple transactions, it returns the sum of all")
    @Sql({ "/db/queries/transaction/multiple_same_month.sql" })
    void testFindSumAll_Multiple() {
        final Float calendar;

        calendar = repository.sumAll();

        Assertions.assertThat(calendar)
            .isEqualTo(5);
    }

    @Test
    @DisplayName("With a negative transaction, it returns the sum of it")
    @Sql({ "/db/queries/transaction/negative.sql" })
    void testFindSumAll_Negative() {
        final Float calendar;

        calendar = repository.sumAll();

        Assertions.assertThat(calendar)
            .isEqualTo(-1);
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testFindSumAll_NoData() {
        final Float result;

        result = repository.sumAll();

        Assertions.assertThat(result)
            .isNull();
    }

    @Test
    @DisplayName("With a single transaction, it returns the sum of it")
    @Sql({ "/db/queries/transaction/single.sql" })
    void testFindSumAll_Single() {
        final Float result;

        result = repository.sumAll();

        Assertions.assertThat(result)
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a variety of transactions, it returns the sum of them")
    @Sql({ "/db/queries/transaction/variety.sql" })
    void testFindSumAll_Variety() {
        final Float result;

        result = repository.sumAll();

        Assertions.assertThat(result)
            .isEqualTo(1.5f);
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With a transaction with value around zero, it returns the sum of it")
    void testSumAll_AroundZero(final Float amount) {
        final Float sum;

        repository.save(PersistentTransactions.transaction(amount));

        sum = repository.sumAll();

        Assertions.assertThat(sum)
            .isEqualTo(amount);
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With a decimal transaction, it returns the sum of it")
    void testSumAll_Decimal(final Float amount) {
        final Float sum;

        repository.save(PersistentTransactions.transaction(amount));

        sum = repository.sumAll();

        Assertions.assertThat(sum)
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("With decimal transactions which add up to zero, it returns the sum of them")
    @Sql({ "/db/queries/transaction/decimal_adds_zero.sql" })
    void testSumAll_DecimalsAddUpToZero() {
        final Float sum;

        sum = repository.sumAll();

        Assertions.assertThat(sum)
            .isZero();
    }

}