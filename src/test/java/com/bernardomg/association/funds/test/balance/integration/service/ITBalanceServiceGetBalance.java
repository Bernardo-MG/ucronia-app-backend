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

/**
 * TODO: Test with transactions for the previous month, the results should be 0
 */
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
        final LocalDate             month;

        month = LocalDate.now();
        entity = PersistentTransaction.builder()
            .date(month)
            .description("Description")
            .amount(amount)
            .build();

        repository.save(entity);
        repository.flush();
    }

    private final void persistNextMonth(final Float amount) {
        final PersistentTransaction entity;
        final LocalDate             month;

        month = LocalDate.now()
            .plusMonths(1);
        entity = PersistentTransaction.builder()
            .date(month)
            .description("Description")
            .amount(amount)
            .build();

        repository.save(entity);
        repository.flush();
    }

    private final void persistPreviousMonth(final Float amount) {
        final PersistentTransaction entity;
        final LocalDate             month;

        month = LocalDate.now()
            .minusMonths(1);
        entity = PersistentTransaction.builder()
            .date(month)
            .description("Description")
            .amount(amount)
            .build();

        repository.save(entity);
        repository.flush();
    }

    private final void persistPreviousMonth(final Float amount, final int monthDiff) {
        final PersistentTransaction entity;
        final LocalDate             month;

        month = LocalDate.now()
            .minusMonths(monthDiff);
        entity = PersistentTransaction.builder()
            .date(month)
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
        Assertions.assertThat(balance.getResults())
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("With data for the current month it returns the balance")
    void testGetBalance_CurrentMonth() {
        final CurrentBalance balance;

        persist(1F);

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isEqualTo(1);
        Assertions.assertThat(balance.getResults())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With data for the current month and previous months it returns the balance")
    void testGetBalance_CurrentMonthAndPrevious() {
        final CurrentBalance balance;

        persist(1F);
        persistPreviousMonth(2F, 1);
        persistPreviousMonth(3F, 2);

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isEqualTo(6);
        Assertions.assertThat(balance.getResults())
            .isEqualTo(1);
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
        Assertions.assertThat(balance.getResults())
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("With decimal values which sum zero the returned balance is zero")
    void testGetBalance_DecimalsAddUpToZero() {
        final CurrentBalance balance;

        persist(-40.8F);
        persist(13.6F);
        persist(13.6F);
        persist(13.6F);

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isZero();
        Assertions.assertThat(balance.getResults())
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
        Assertions.assertThat(balance.getResults())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With multiple transactions for a single month it returns the correct data")
    void testGetBalance_Multiple() {
        final CurrentBalance balance;

        persist(1F);
        persist(1F);
        persist(1F);
        persist(1F);
        persist(1F);

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isEqualTo(5);
        Assertions.assertThat(balance.getResults())
            .isEqualTo(5);
    }

    @Test
    @DisplayName("With data for the next month it returns no balance")
    void testGetBalance_NextMonth() {
        final CurrentBalance balance;

        persistNextMonth(1F);

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isEqualTo(0);
        Assertions.assertThat(balance.getResults())
            .isEqualTo(0);
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testGetBalance_NoData() {
        final CurrentBalance balance;

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isZero();
        Assertions.assertThat(balance.getResults())
            .isZero();
    }

    @Test
    @DisplayName("With data for the previous month it returns the balance but no results")
    void testGetBalance_PreviousMonth() {
        final CurrentBalance balance;

        persistPreviousMonth(1F);
        persistPreviousMonth(2F);
        persistPreviousMonth(3F);

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isEqualTo(6);
        Assertions.assertThat(balance.getResults())
            .isEqualTo(0);
    }

    @Test
    @DisplayName("With data for the previous months it returns the balance but no results")
    void testGetBalance_PreviousMonths() {
        final CurrentBalance balance;

        persistPreviousMonth(1F, 1);
        persistPreviousMonth(2F, 2);
        persistPreviousMonth(3F, 3);

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isEqualTo(6);
        Assertions.assertThat(balance.getResults())
            .isEqualTo(0);
    }

    @Test
    @DisplayName("With data for the previous months, including gaps, it returns the balance but no results")
    void testGetBalance_PreviousMonths_Gaps() {
        final CurrentBalance balance;

        persistPreviousMonth(1F, 1);
        persistPreviousMonth(2F, 2);
        persistPreviousMonth(3F, 5);

        balance = service.getBalance();

        Assertions.assertThat(balance.getTotal())
            .isEqualTo(6);
        Assertions.assertThat(balance.getResults())
            .isEqualTo(0);
    }

}
