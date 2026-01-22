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

import java.util.Optional;

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionCurrentBalance;
import com.bernardomg.association.transaction.domain.model.TransactionMonthsRange;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;
import com.bernardomg.ucronia.openapi.model.TransactionChangeDto;
import com.bernardomg.ucronia.openapi.model.TransactionCreationDto;
import com.bernardomg.ucronia.openapi.model.TransactionCurrentBalanceDto;
import com.bernardomg.ucronia.openapi.model.TransactionCurrentBalanceResponseDto;
import com.bernardomg.ucronia.openapi.model.TransactionDto;
import com.bernardomg.ucronia.openapi.model.TransactionMonthsRangeDto;
import com.bernardomg.ucronia.openapi.model.TransactionMonthsRangeResponseDto;
import com.bernardomg.ucronia.openapi.model.TransactionPageResponseDto;
import com.bernardomg.ucronia.openapi.model.TransactionResponseDto;

public final class TransactionDtoMapper {

    public static final Transaction toDomain(final Long index, final TransactionChangeDto change) {
        return new Transaction(index, change.getDate(), change.getAmount(), change.getDescription());
    }

    public static final Transaction toDomain(final TransactionCreationDto creation) {
        return new Transaction(-1, creation.getDate(), creation.getAmount(), creation.getDescription());
    }

    public static final TransactionResponseDto toResponseDto(final Optional<Transaction> transaction) {
        return new TransactionResponseDto().content(transaction.map(TransactionDtoMapper::toDto)
            .orElse(null));
    }

    public static final TransactionPageResponseDto toResponseDto(final Page<Transaction> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(TransactionDtoMapper::toDto)
            .toList());
        return new TransactionPageResponseDto().content(page.content()
            .stream()
            .map(TransactionDtoMapper::toDto)
            .toList())
            .size(page.size())
            .page(page.page())
            .totalElements(page.totalElements())
            .totalPages(page.totalPages())
            .elementsInPage(page.elementsInPage())
            .first(page.first())
            .last(page.last())
            .sort(sortingResponse);
    }

    public static final TransactionResponseDto toResponseDto(final Transaction transaction) {
        return new TransactionResponseDto().content(TransactionDtoMapper.toDto(transaction));
    }

    public static final TransactionCurrentBalanceResponseDto toResponseDto(final TransactionCurrentBalance balance) {
        final TransactionCurrentBalanceDto balanceDto;

        balanceDto = new TransactionCurrentBalanceDto().results(balance.results())
            .total(balance.total());
        return new TransactionCurrentBalanceResponseDto().content(balanceDto);
    }

    public static final TransactionMonthsRangeResponseDto toResponseDto(final TransactionMonthsRange range) {
        final TransactionMonthsRangeDto rangeDto;

        rangeDto = new TransactionMonthsRangeDto().months(range.months()
            .stream()
            .toList());
        return new TransactionMonthsRangeResponseDto().content(rangeDto);
    }

    private static final PropertyDto toDto(final Property property) {
        final DirectionEnum direction;

        if (property.direction() == Direction.ASC) {
            direction = DirectionEnum.ASC;
        } else {
            direction = DirectionEnum.DESC;
        }
        return new PropertyDto().name(property.name())
            .direction(direction);
    }

    private static final TransactionDto toDto(final Transaction transaction) {
        return new TransactionDto().index(transaction.index())
            .date(transaction.date())
            .amount(transaction.amount())
            .description(transaction.description());
    }

    private TransactionDtoMapper() {
        super();
    }

}
