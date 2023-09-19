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

package com.bernardomg.association.funds.test.balance.integration.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.funds.balance.model.DtoMonthlyBalance;
import com.bernardomg.association.funds.balance.model.MonthlyBalance;
import com.bernardomg.association.funds.balance.service.BalanceService;
import com.bernardomg.association.funds.test.balance.assertion.BalanceAssertions;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Balance service - get monthly balance")
class ITBalanceServiceGetMonthlyBalance {

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
    void testGetMonthlyBalance_AroundZero(final Float amount) {
        final Collection<? extends MonthlyBalance> balances;
        final MonthlyBalance                       balance;

        persist(amount);

        balances = service.getMonthlyBalance();

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();

        Assertions.assertThat(balance.getMonthlyTotal())
            .isEqualTo(amount);
        Assertions.assertThat(balance.getCumulative())
            .isEqualTo(amount);
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With decimal values it returns the correct amounts")
    void testGetMonthlyBalance_Decimal(final Float amount) {
        final Collection<? extends MonthlyBalance> balances;
        final MonthlyBalance                       balance;

        persist(amount);

        balances = service.getMonthlyBalance();

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();

        Assertions.assertThat(balance.getMonthlyTotal())
            .isEqualTo(amount);
        Assertions.assertThat(balance.getCumulative())
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("With decimal values which sum zero the returned balance is zero")
    @Sql({ "/db/queries/transaction/decimal_adds_zero.sql" })
    void testGetMonthlyBalance_DecimalsAddUpToZero() {
        final Collection<? extends MonthlyBalance> balances;
        final MonthlyBalance                       balance;

        balances = service.getMonthlyBalance();

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();

        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.JANUARY, 1))
            .monthlyTotal(0f)
            .cumulative(0f)
            .build());
    }

    @Test
    @DisplayName("With a full year it returns twelve months")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    void testGetMonthlyBalance_FullYear() {
        final Collection<? extends MonthlyBalance> balances;
        final Iterator<? extends MonthlyBalance>   balancesItr;
        MonthlyBalance                             balance;

        balances = service.getMonthlyBalance();

        Assertions.assertThat(balances)
            .hasSize(12);

        balancesItr = balances.iterator();

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.JANUARY, 1))
            .monthlyTotal(1f)
            .cumulative(1f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.FEBRUARY, 1))
            .monthlyTotal(1f)
            .cumulative(2f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.MARCH, 1))
            .monthlyTotal(1f)
            .cumulative(3f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.APRIL, 1))
            .monthlyTotal(1f)
            .cumulative(4f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.MAY, 1))
            .monthlyTotal(1f)
            .cumulative(5f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.JUNE, 1))
            .monthlyTotal(1f)
            .cumulative(6f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.JULY, 1))
            .monthlyTotal(1f)
            .cumulative(7f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.AUGUST, 1))
            .monthlyTotal(1f)
            .cumulative(8f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.SEPTEMBER, 1))
            .monthlyTotal(1f)
            .cumulative(9f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.OCTOBER, 1))
            .monthlyTotal(1f)
            .cumulative(10f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.NOVEMBER, 1))
            .monthlyTotal(1f)
            .cumulative(11f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.DECEMBER, 1))
            .monthlyTotal(1f)
            .cumulative(12f)
            .build());
    }

    @Test
    @DisplayName("With multiple transactions for a single month it returns a single month")
    @Sql({ "/db/queries/transaction/multiple_same_month.sql" })
    void testGetMonthlyBalance_Multiple() {
        final Collection<? extends MonthlyBalance> balances;
        final Iterator<? extends MonthlyBalance>   balancesItr;
        MonthlyBalance                             balance;

        balances = service.getMonthlyBalance();

        Assertions.assertThat(balances)
            .hasSize(1);

        balancesItr = balances.iterator();

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, DtoMonthlyBalance.builder()
            .month(LocalDate.of(2020, Month.JANUARY, 1))
            .monthlyTotal(5f)
            .cumulative(5f)
            .build());
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testGetMonthlyBalance_NoData() {
        final Collection<? extends MonthlyBalance> balances;

        balances = service.getMonthlyBalance();

        Assertions.assertThat(balances)
            .isEmpty();
    }

}
