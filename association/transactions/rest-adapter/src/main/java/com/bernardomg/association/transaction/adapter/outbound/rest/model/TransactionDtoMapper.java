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
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.transaction.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionCreationDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionMonthsRangeDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionMonthsRangeResponseDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionPageResponseDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionResponseDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionSummaryDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionSummaryResponseDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionUpdateDto;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionMonthsRange;
import com.bernardomg.association.transaction.domain.model.TransactionSummary;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

public final class TransactionDtoMapper {

    public static final Transaction toDomain(final Long index, final TransactionUpdateDto change) {
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

    public static final TransactionMonthsRangeResponseDto toResponseDto(final TransactionMonthsRange range) {
        final TransactionMonthsRangeDto rangeDto;
        final List<Instant>             months;

        months = range.months()
            .stream()
            .map(TransactionDtoMapper::toInstant)
            .toList();

        rangeDto = new TransactionMonthsRangeDto().months(months);
        return new TransactionMonthsRangeResponseDto().content(rangeDto);
    }

    public static final TransactionSummaryResponseDto toResponseDto(final TransactionSummary summary) {
        final TransactionSummaryDto summaryDto;

        summaryDto = new TransactionSummaryDto().results(summary.results())
            .total(summary.total());
        return new TransactionSummaryResponseDto().content(summaryDto);
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

    private static final Instant toInstant(final YearMonth month) {
        return month.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
    }

    private TransactionDtoMapper() {
        super();
    }

}
