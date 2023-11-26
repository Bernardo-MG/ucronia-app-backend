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
import java.time.YearMonth;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.funds.balance.model.ImmutableMonthlyBalance;
import com.bernardomg.association.funds.balance.model.MonthlyBalance;
import com.bernardomg.association.funds.balance.model.request.BalanceQuery;
import com.bernardomg.association.funds.balance.model.request.BalanceQueryRequest;
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

    private static Stream<Arguments> geValidDates() {
        return Stream.of(
            // This month
            Arguments.of(YearMonth.now()),
            // Previous month
            Arguments.of(YearMonth.now()
                .minusMonths(1)));
    }

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

    private final void persist(final Integer year, final Month month) {
        final PersistentTransaction entity;

        entity = PersistentTransaction.builder()
            .date(LocalDate.of(year, month, 1))
            .description("Description")
            .amount(1F)
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
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        persist(amount);

        query = BalanceQueryRequest.builder()
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();

        Assertions.assertThat(balance.getResults())
            .isEqualTo(amount);
        Assertions.assertThat(balance.getTotal())
            .isEqualTo(amount);
    }

    @ParameterizedTest(name = "Date: {0}")
    @MethodSource("geValidDates")
    @DisplayName("Returns balance for the current month and adjacents")
    void testGetMonthlyBalance_Dates(final YearMonth date) {
        final Collection<? extends MonthlyBalance> balances;
        final MonthlyBalance                       balance;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        persist(date.getYear(), date.getMonth());

        query = BalanceQueryRequest.builder()
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();

        Assertions.assertThat(balance.getResults())
            .isEqualTo(1F);
        Assertions.assertThat(balance.getTotal())
            .isEqualTo(1F);
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With decimal values it returns the correct amounts")
    void testGetMonthlyBalance_Decimal(final Float amount) {
        final Collection<? extends MonthlyBalance> balances;
        final MonthlyBalance                       balance;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        persist(amount);

        query = BalanceQueryRequest.builder()
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();

        Assertions.assertThat(balance.getResults())
            .isEqualTo(amount);
        Assertions.assertThat(balance.getTotal())
            .isEqualTo(amount);
    }

    @Test
    @DisplayName("With decimal values which sum zero the returned balance is zero")
    @Sql({ "/db/queries/transaction/decimal_adds_zero.sql" })
    void testGetMonthlyBalance_DecimalsAddUpToZero() {
        final Collection<? extends MonthlyBalance> balances;
        final MonthlyBalance                       balance;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQueryRequest.builder()
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(1);

        balance = balances.iterator()
            .next();

        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JANUARY))
            .results(0f)
            .total(0f)
            .build());
    }

    @Test
    @DisplayName("With a full year it returns twelve months")
    @Sql({ "/db/queries/transaction/full_year.sql" })
    void testGetMonthlyBalance_FullYear() {
        final Collection<? extends MonthlyBalance> balances;
        final Iterator<? extends MonthlyBalance>   balancesItr;
        MonthlyBalance                             balance;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQueryRequest.builder()
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(12);

        balancesItr = balances.iterator();

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JANUARY))
            .results(1f)
            .total(1f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.FEBRUARY))
            .results(1f)
            .total(2f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.MARCH))
            .results(1f)
            .total(3f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.APRIL))
            .results(1f)
            .total(4f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.MAY))
            .results(1f)
            .total(5f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JUNE))
            .results(1f)
            .total(6f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JULY))
            .results(1f)
            .total(7f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.AUGUST))
            .results(1f)
            .total(8f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.SEPTEMBER))
            .results(1f)
            .total(9f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.OCTOBER))
            .results(1f)
            .total(10f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.NOVEMBER))
            .results(1f)
            .total(11f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.DECEMBER))
            .results(1f)
            .total(12f)
            .build());
    }

    @Test
    @DisplayName("With multiple transactions for a single month it returns a single month")
    @Sql({ "/db/queries/transaction/multiple_same_month.sql" })
    void testGetMonthlyBalance_Multiple() {
        final Collection<? extends MonthlyBalance> balances;
        final Iterator<? extends MonthlyBalance>   balancesItr;
        MonthlyBalance                             balance;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQueryRequest.builder()
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .hasSize(1);

        balancesItr = balances.iterator();

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, ImmutableMonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JANUARY))
            .results(5f)
            .total(5f)
            .build());
    }

    @Test
    @DisplayName("Returns no balance for the next month")
    void testGetMonthlyBalance_NextMonth() {
        final Collection<? extends MonthlyBalance> balances;
        final BalanceQuery                         query;
        final Sort                                 sort;
        final YearMonth                            date;

        date = YearMonth.now()
            .plusMonths(1);
        persist(date.getYear(), date.getMonth());

        sort = Sort.unsorted();

        query = BalanceQueryRequest.builder()
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .isEmpty();
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testGetMonthlyBalance_NoData() {
        final Collection<? extends MonthlyBalance> balances;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQueryRequest.builder()
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .isEmpty();
    }

}
