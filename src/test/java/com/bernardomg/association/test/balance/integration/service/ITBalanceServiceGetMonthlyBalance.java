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

package com.bernardomg.association.test.balance.integration.service;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.balance.model.ImmutableMonthlyBalance;
import com.bernardomg.association.balance.model.MonthlyBalance;
import com.bernardomg.association.balance.service.BalanceService;
import com.bernardomg.association.test.balance.assertion.BalanceAssertions;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.config.argument.AroundZeroArgumentsProvider;
import com.bernardomg.association.test.config.argument.DecimalArgumentsProvider;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;

@IntegrationTest
@DisplayName("Balance service - get monthly balance")
public class ITBalanceServiceGetMonthlyBalance {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private BalanceService        service;

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With values around zero it returns the correct amounts")
    public void testGetMonthlyBalance_AroundZero(final Float amount) {
        final MonthlyBalance        data;
        final PersistentTransaction entity;

        entity = PersistentTransaction.builder()
            .date(new GregorianCalendar(2020, 0, 1))
            .description("Description")
            .amount(amount)
            .build();

        repository.save(entity);
        repository.flush();

        data = service.getMonthlyBalance()
            .iterator()
            .next();

        Assertions.assertThat(data.getTotal())
            .isEqualTo(amount);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(amount);
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With decimal values it returns the correct amounts")
    public void testGetMonthlyBalance_Decimal(final Float amount) {
        final MonthlyBalance        data;
        final PersistentTransaction entity;

        entity = PersistentTransaction.builder()
            .date(new GregorianCalendar(2020, 0, 1))
            .description("Description")
            .amount(amount)
            .build();

        repository.save(entity);
        repository.flush();

        data = service.getMonthlyBalance()
            .iterator()
            .next();

        Assertions.assertThat(data.getTotal())
            .isEqualTo(amount);
        Assertions.assertThat(data.getCumulative())
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("With decimal values which sum zero the returned balance is zero")
    @Sql({ "/db/queries/transaction/decimal_adds_zero.sql" })
    public void testGetMonthlyBalance_DecimalsAddUpToZero() {
        final MonthlyBalance data;

        data = service.getMonthlyBalance()
            .iterator()
            .next();

        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 0, 1))
            .total(0f)
            .cumulative(0f)
            .build());
    }

    @Test
    @DisplayName("With a full year it returns twelve months")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    public void testGetMonthlyBalance_FullYear_Count() {
        final Collection<MonthlyBalance> read;

        read = service.getMonthlyBalance();

        Assertions.assertThat(read)
            .hasSize(12);
    }

    @Test
    @DisplayName("With a full year it returns the correct data for all the months")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    public void testGetMonthlyBalance_FullYear_Data() {
        final Iterator<MonthlyBalance> read;
        MonthlyBalance                 data;

        read = service.getMonthlyBalance()
            .iterator();

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 0, 1))
            .total(1f)
            .cumulative(1f)
            .build());

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 1, 1))
            .total(1f)
            .cumulative(2f)
            .build());

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 2, 1))
            .total(1f)
            .cumulative(3f)
            .build());

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 3, 1))
            .total(1f)
            .cumulative(4f)
            .build());

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 4, 1))
            .total(1f)
            .cumulative(5f)
            .build());

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 5, 1))
            .total(1f)
            .cumulative(6f)
            .build());

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 6, 1))
            .total(1f)
            .cumulative(7f)
            .build());

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 7, 1))
            .total(1f)
            .cumulative(8f)
            .build());

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 8, 1))
            .total(1f)
            .cumulative(9f)
            .build());

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 9, 1))
            .total(1f)
            .cumulative(10f)
            .build());

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 10, 1))
            .total(1f)
            .cumulative(11f)
            .build());

        data = read.next();
        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 11, 1))
            .total(1f)
            .cumulative(12f)
            .build());
    }

    @Test
    @DisplayName("With multiple transactions for a single month it returns a single month")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetMonthlyBalance_Multiple_Count() {
        final Collection<MonthlyBalance> read;

        read = service.getMonthlyBalance();

        Assertions.assertThat(read)
            .hasSize(1);
    }

    @Test
    @DisplayName("With multiple transactions for a single month it returns the correct data")
    @Sql({ "/db/queries/transaction/multiple.sql" })
    public void testGetMonthlyBalance_Multiple_Data() {
        final MonthlyBalance data;

        data = service.getMonthlyBalance()
            .iterator()
            .next();

        BalanceAssertions.isEqualTo(data, ImmutableMonthlyBalance.builder()
            .date(new GregorianCalendar(2020, 0, 1))
            .total(5f)
            .cumulative(5f)
            .build());
    }

    @Test
    @DisplayName("With no data it returns nothing")
    public void testGetMonthlyBalance_NoData() {
        final Collection<MonthlyBalance> read;

        read = service.getMonthlyBalance();

        Assertions.assertThat(read)
            .isEmpty();
    }

}
