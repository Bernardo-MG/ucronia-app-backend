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

import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.funds.balance.model.BalanceQuery;
import com.bernardomg.association.funds.balance.model.MonthlyBalance;
import com.bernardomg.association.funds.balance.service.BalanceService;
import com.bernardomg.association.funds.test.balance.util.assertion.BalanceAssertions;
import com.bernardomg.association.funds.test.transaction.configuration.FullTransactionYear;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Balance service - get monthly balance - filter")
class ITBalanceServiceGetMonthlyBalanceFilter {

    @Autowired
    private BalanceService service;

    @Test
    @DisplayName("Filtering ending before the year returns no month")
    @FullTransactionYear
    void testGetMonthlyBalance_EndBeforeStart() {
        final Collection<? extends MonthlyBalance> balances;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQuery.builder()
            .endDate(YearMonth.of(2019, Month.DECEMBER))
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("Filtering ending on December returns all the months")
    @FullTransactionYear
    void testGetMonthlyBalance_EndDecember() {
        final Collection<? extends MonthlyBalance> balances;
        final Iterator<? extends MonthlyBalance>   balancesItr;
        MonthlyBalance                             balance;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQuery.builder()
            .endDate(YearMonth.of(2020, Month.DECEMBER))
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(12);

        balancesItr = balances.iterator();

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JANUARY))
            .results(1f)
            .total(1f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.FEBRUARY))
            .results(1f)
            .total(2f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.MARCH))
            .results(1f)
            .total(3f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.APRIL))
            .results(1f)
            .total(4f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.MAY))
            .results(1f)
            .total(5f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JUNE))
            .results(1f)
            .total(6f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JULY))
            .results(1f)
            .total(7f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.AUGUST))
            .results(1f)
            .total(8f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.SEPTEMBER))
            .results(1f)
            .total(9f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.OCTOBER))
            .results(1f)
            .total(10f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.NOVEMBER))
            .results(1f)
            .total(11f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.DECEMBER))
            .results(1f)
            .total(12f)
            .build());
    }

    @Test
    @DisplayName("Filtering the full year returns all the months")
    @FullTransactionYear
    void testGetMonthlyBalance_FullYear() {
        final Collection<? extends MonthlyBalance> balances;
        final Iterator<? extends MonthlyBalance>   balancesItr;
        MonthlyBalance                             balance;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQuery.builder()
            .startDate(YearMonth.of(2020, Month.JANUARY))
            .endDate(YearMonth.of(2020, Month.DECEMBER))
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(12);

        balancesItr = balances.iterator();

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JANUARY))
            .results(1f)
            .total(1f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.FEBRUARY))
            .results(1f)
            .total(2f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.MARCH))
            .results(1f)
            .total(3f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.APRIL))
            .results(1f)
            .total(4f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.MAY))
            .results(1f)
            .total(5f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JUNE))
            .results(1f)
            .total(6f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JULY))
            .results(1f)
            .total(7f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.AUGUST))
            .results(1f)
            .total(8f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.SEPTEMBER))
            .results(1f)
            .total(9f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.OCTOBER))
            .results(1f)
            .total(10f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.NOVEMBER))
            .results(1f)
            .total(11f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.DECEMBER))
            .results(1f)
            .total(12f)
            .build());
    }

    @Test
    @DisplayName("Filtering by January returns only that month")
    @FullTransactionYear
    void testGetMonthlyBalance_January() {
        final Collection<? extends MonthlyBalance> balances;
        final Iterator<? extends MonthlyBalance>   balancesItr;
        MonthlyBalance                             balance;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQuery.builder()
            .startDate(YearMonth.of(2020, Month.JANUARY))
            .endDate(YearMonth.of(2020, Month.JANUARY))
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(1);

        balancesItr = balances.iterator();

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JANUARY))
            .results(1f)
            .total(1f)
            .build());
    }

    @Test
    @DisplayName("Filtering by January and February returns only those months")
    @FullTransactionYear
    void testGetMonthlyBalance_JanuaryToFebruary() {
        final Collection<? extends MonthlyBalance> balances;
        final Iterator<? extends MonthlyBalance>   balancesItr;
        MonthlyBalance                             balance;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQuery.builder()
            .startDate(YearMonth.of(2020, Month.JANUARY))
            .endDate(YearMonth.of(2020, Month.FEBRUARY))
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(2);

        balancesItr = balances.iterator();

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JANUARY))
            .results(1f)
            .total(1f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.FEBRUARY))
            .results(1f)
            .total(2f)
            .build());
    }

    @Test
    @DisplayName("Filtering with a range where the end is before the start returns nothing")
    @FullTransactionYear
    void testGetMonthlyBalance_RangeEndBeforeStart() {
        final Collection<? extends MonthlyBalance> balances;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQuery.builder()
            .startDate(YearMonth.of(2020, Month.DECEMBER))
            .endDate(YearMonth.of(2020, Month.JANUARY))
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("Filtering beginning after the year returns no month")
    @FullTransactionYear
    void testGetMonthlyBalance_StartAfterEnd() {
        final Collection<? extends MonthlyBalance> balances;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQuery.builder()
            .startDate(YearMonth.of(2021, Month.JANUARY))
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .isEmpty();
    }

    @Test
    @DisplayName("Filtering beginning on January returns all the months")
    @FullTransactionYear
    void testGetMonthlyBalance_StartInJanuary() {
        final Collection<? extends MonthlyBalance> balances;
        final Iterator<? extends MonthlyBalance>   balancesItr;
        MonthlyBalance                             balance;
        final BalanceQuery                         query;
        final Sort                                 sort;

        sort = Sort.unsorted();

        query = BalanceQuery.builder()
            .startDate(YearMonth.of(2020, Month.JANUARY))
            .build();
        balances = service.getMonthlyBalance(query, sort);

        Assertions.assertThat(balances)
            .as("balances")
            .hasSize(12);

        balancesItr = balances.iterator();

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JANUARY))
            .results(1f)
            .total(1f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.FEBRUARY))
            .results(1f)
            .total(2f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.MARCH))
            .results(1f)
            .total(3f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.APRIL))
            .results(1f)
            .total(4f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.MAY))
            .results(1f)
            .total(5f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JUNE))
            .results(1f)
            .total(6f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.JULY))
            .results(1f)
            .total(7f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.AUGUST))
            .results(1f)
            .total(8f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.SEPTEMBER))
            .results(1f)
            .total(9f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.OCTOBER))
            .results(1f)
            .total(10f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.NOVEMBER))
            .results(1f)
            .total(11f)
            .build());

        balance = balancesItr.next();
        BalanceAssertions.isEqualTo(balance, MonthlyBalance.builder()
            .month(YearMonth.of(2020, Month.DECEMBER))
            .results(1f)
            .total(12f)
            .build());
    }

}
