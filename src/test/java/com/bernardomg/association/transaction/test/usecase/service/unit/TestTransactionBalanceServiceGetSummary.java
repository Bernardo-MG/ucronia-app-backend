/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.transaction.domain.model.TransactionSummary;
import com.bernardomg.association.transaction.domain.repository.TransactionSummaryRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionCurrentBalances;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionSummaryService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Transaction summary service - get summary")
class TestTransactionBalanceServiceGetSummary {

    @InjectMocks
    private DefaultTransactionSummaryService service;

    @Mock
    private TransactionSummaryRepository     transactionSummaryRepository;

    @Test
    @DisplayName("When there is data it is returned")
    void testGetBalance() {
        final TransactionSummary summary;

        // GIVEN
        given(transactionSummaryRepository.findSummary()).willReturn(Optional.of(TransactionCurrentBalances.amount(1)));

        // WHEN
        summary = service.getSummary();

        // THEN
        Assertions.assertThat(summary)
            .isEqualTo(TransactionCurrentBalances.amount(1));
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetBalance_NoData() {
        final TransactionSummary summary;

        // GIVEN
        given(transactionSummaryRepository.findSummary()).willReturn(Optional.empty());

        // WHEN
        summary = service.getSummary();

        // THEN
        Assertions.assertThat(summary)
            .isEqualTo(TransactionCurrentBalances.amount(0));
    }

}
