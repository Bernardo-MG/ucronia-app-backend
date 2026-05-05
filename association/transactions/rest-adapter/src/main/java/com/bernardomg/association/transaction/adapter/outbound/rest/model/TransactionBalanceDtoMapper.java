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

package com.bernardomg.association.transaction.adapter.outbound.rest.model;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;

import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionMonthlyBalanceDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionMonthlyBalanceResponseDto;
import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;

public final class TransactionBalanceDtoMapper {

    public static final TransactionMonthlyBalanceResponseDto
            toResponseDto(final Collection<TransactionMonthlyBalance> balance) {
        return new TransactionMonthlyBalanceResponseDto().content(balance.stream()
            .map(TransactionBalanceDtoMapper::toDto)
            .toList());
    }

    private static final TransactionMonthlyBalanceDto toDto(final TransactionMonthlyBalance balance) {
        final Instant month;

        month = balance.month()
            .atDay(1)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant();
        return new TransactionMonthlyBalanceDto().month(month)
            .results(balance.results())
            .total(balance.total());
    }

    private TransactionBalanceDtoMapper() {
        super();
    }

}
