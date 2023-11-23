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

package com.bernardomg.association.funds.test.balance.integration.service;

import java.time.LocalDate;
import java.time.Month;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.funds.balance.model.CurrentBalance;
import com.bernardomg.association.funds.balance.service.BalanceService;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Balance service - get balance")
class ITBalanceServiceGetBalance {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private BalanceService        service;

    private final void persist(final Float amount) {
        final PersistentTransaction entity;

        entity = PersistentTransaction.builder()
            .date(LocalDate.of(2020, Month.JANUARY, 1))
            .description("Description")
            .amount(amount)
            .build();

        repository.save(entity);
        repository.flush();
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With values around zero it returns the correct amounts")
    void testGetBalance_AroundZero(final Float amount) {
        final CurrentBalance balance;

        persist(amount);

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isEqualTo(amount);
        Assertions.assertThat(balance.getDifference())
            .isEqualTo(amount);
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With decimal values it returns the correct amounts")
    void testGetBalance_Decimal(final Float amount) {
        final CurrentBalance balance;

        persist(amount);

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isEqualTo(amount);
        Assertions.assertThat(balance.getDifference())
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("With decimal values which sum zero the returned balance is zero")
    @Sql({ "/db/queries/transaction/decimal_adds_zero.sql" })
    void testGetBalance_DecimalsAddUpToZero() {
        final CurrentBalance balance;

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isZero();
        Assertions.assertThat(balance.getDifference())
            .isZero();
    }

    @Test
    @DisplayName("With a full year it returns the correct data")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    void testGetBalance_FullYear() {
        final CurrentBalance balance;

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isEqualTo(12);
        Assertions.assertThat(balance.getDifference())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With multiple transactions for a single month it returns the correct data")
    @Sql({ "/db/queries/transaction/multiple_same_month.sql" })
    void testGetBalance_Multiple() {
        final CurrentBalance balance;

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isEqualTo(5);
        Assertions.assertThat(balance.getDifference())
            .isEqualTo(5);
    }

    @Test
    @DisplayName("With not data it returns nothing")
    void testGetBalance_NoData() {
        final CurrentBalance balance;

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isZero();
        Assertions.assertThat(balance.getDifference())
            .isZero();
    }

}
