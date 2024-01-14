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

package com.bernardomg.association.transaction.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.association.transaction.model.CurrentBalance;
import com.bernardomg.association.transaction.service.BalanceService;
import com.bernardomg.association.transaction.test.config.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.test.config.factory.CurrentBalances;
import com.bernardomg.association.transaction.test.util.initializer.TransactionInitializer;
import com.bernardomg.test.config.annotation.IntegrationTest;

/**
 * TODO: Test with transactions for the previous month, the results should be 0
 */
@IntegrationTest
@DisplayName("Balance service - get balance")
class ITBalanceServiceGetBalance {

    @Autowired
    private BalanceService         service;

    @Autowired
    private TransactionInitializer transactionInitializer;

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With values around zero it returns the correct amounts")
    void testGetBalance_AroundZero(final float amount) {
        final CurrentBalance balance;

        // GIVEN
        transactionInitializer.registerCurrentMonth(amount);

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(amount));
    }

    @Test
    @DisplayName("With data for the current month it returns the balance")
    void testGetBalance_CurrentMonth() {
        final CurrentBalance balance;

        // GIVEN
        transactionInitializer.registerCurrentMonth(1F);

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(1));
    }

    @Test
    @DisplayName("With data for the current month and previous months it returns the balance")
    void testGetBalance_CurrentMonthAndPrevious() {
        final CurrentBalance balance;

        // GIVEN
        transactionInitializer.registerMonthsBack(1F, 0, 1L);
        transactionInitializer.registerMonthsBack(2F, 1, 2L);
        transactionInitializer.registerMonthsBack(3F, 2, 3L);

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(1, 6));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With decimal values it returns the correct amounts")
    void testGetBalance_Decimal(final Float amount) {
        final CurrentBalance balance;

        // GIVEN
        transactionInitializer.registerCurrentMonth(amount);

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(amount));
    }

    @Test
    @DisplayName("With decimal values which sum zero the returned balance is zero")
    void testGetBalance_DecimalsAddUpToZero() {
        final CurrentBalance balance;

        // GIVEN
        transactionInitializer.registerCurrentMonth(-40.8F, 1L);
        transactionInitializer.registerCurrentMonth(13.6F, 2L);
        transactionInitializer.registerCurrentMonth(13.6F, 3L);
        transactionInitializer.registerCurrentMonth(13.6F, 4L);

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(0));
    }

    @Test
    @DisplayName("With a full year it returns the correct data")
    @FullTransactionYear
    void testGetBalance_FullYear() {
        final CurrentBalance balance;

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(0, 12));
    }

    @Test
    @DisplayName("With multiple transactions for a single month it returns the correct data")
    void testGetBalance_Multiple() {
        final CurrentBalance balance;

        // GIVEN
        transactionInitializer.registerCurrentMonth(1F, 1L);
        transactionInitializer.registerCurrentMonth(1F, 2L);
        transactionInitializer.registerCurrentMonth(1F, 3L);
        transactionInitializer.registerCurrentMonth(1F, 4L);
        transactionInitializer.registerCurrentMonth(1F, 5L);

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(5));
    }

    @Test
    @DisplayName("With data for the next month it returns no balance")
    void testGetBalance_NextMonth() {
        final CurrentBalance balance;

        // GIVEN
        transactionInitializer.registerNextMonth(1F);

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(0));
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testGetBalance_NoData() {
        final CurrentBalance balance;

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(0));
    }

    @Test
    @DisplayName("With data for the previous month it returns the balance but no results")
    void testGetBalance_PreviousMonth() {
        final CurrentBalance balance;

        // GIVEN
        transactionInitializer.registerPreviousMonth(1F, 1L);
        transactionInitializer.registerPreviousMonth(2F, 2L);
        transactionInitializer.registerPreviousMonth(3F, 3L);

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(0, 6));
    }

    @Test
    @DisplayName("With data for the previous months it returns the balance but no results")
    void testGetBalance_PreviousMonths() {
        final CurrentBalance balance;

        // GIVEN
        transactionInitializer.registerMonthsBack(1F, 1, 1L);
        transactionInitializer.registerMonthsBack(2F, 2, 2L);
        transactionInitializer.registerMonthsBack(3F, 3, 3L);

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(0, 6));
    }

    @Test
    @DisplayName("With data for the previous months, including gaps, it returns the balance but no results")
    void testGetBalance_PreviousMonths_Gaps() {
        final CurrentBalance balance;

        // GIVEN
        transactionInitializer.registerMonthsBack(1F, 1, 1L);
        transactionInitializer.registerMonthsBack(2F, 2, 2L);
        transactionInitializer.registerMonthsBack(3F, 5, 3L);

        // WHEN
        balance = service.getBalance();

        // THEN
        Assertions.assertThat(balance)
            .isEqualTo(CurrentBalances.amount(0, 6));
    }

}
