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

package com.bernardomg.association.transaction.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;
import com.bernardomg.association.transaction.domain.repository.TransactionBalanceRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionBalanceQueries;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionMonthlyBalances;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionBalanceService;
import com.bernardomg.data.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("Transaction balance service - get monthly balance")
class TestTransactionBalanceServiceGetMonthlyBalance {

    @InjectMocks
    private DefaultTransactionBalanceService service;

    @Mock
    private TransactionBalanceRepository     transactionBalanceRepository;

    @Test
    @DisplayName("When there is data it is returned")
    void testGetMonthlyBalance() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sorting                               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        query = TransactionBalanceQueries.empty();

        given(transactionBalanceRepository.findMonthlyBalance(query, sorting))
            .willReturn(List.of(TransactionMonthlyBalances.currentMonth(1)));

        // WHEN
        balances = service.getMonthlyBalance(query, sorting);

        // THEN
        Assertions.assertThat(balances)
            .containsExactly(TransactionMonthlyBalances.currentMonth(1));
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetMonthlyBalance_NoData() {
        final Collection<TransactionMonthlyBalance> balances;
        final TransactionBalanceQuery               query;
        final Sorting                               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        query = TransactionBalanceQueries.empty();

        given(transactionBalanceRepository.findMonthlyBalance(query, sorting)).willReturn(List.of());

        // WHEN
        balances = service.getMonthlyBalance(query, sorting);

        // THEN
        Assertions.assertThat(balances)
            .isEmpty();
    }

}
