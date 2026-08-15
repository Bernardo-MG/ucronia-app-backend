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

import com.bernardomg.association.transaction.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionTypeCreationDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionTypeDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionTypePageResponseDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionTypeResponseDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionTypeUpdateDto;
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

public final class TransactionTypeDtoMapper {

    public static final TransactionType toDomain(final Long number, final TransactionTypeUpdateDto change) {
        return new TransactionType(number, change.getDescription(), change.getColor());
    }

    public static final TransactionType toDomain(final TransactionTypeCreationDto creation) {
        return new TransactionType(-1, creation.getDescription(), creation.getColor());
    }

    public static final TransactionTypeResponseDto toResponseDto(final Optional<TransactionType> transactionType) {
        return new TransactionTypeResponseDto().content(transactionType.map(TransactionTypeDtoMapper::toDto)
            .orElse(null));
    }

    public static final TransactionTypePageResponseDto toResponseDto(final Page<TransactionType> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(TransactionTypeDtoMapper::toDto)
            .toList());
        return new TransactionTypePageResponseDto().content(page.content()
            .stream()
            .map(TransactionTypeDtoMapper::toDto)
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

    public static final TransactionTypeResponseDto toResponseDto(final TransactionType transaction) {
        return new TransactionTypeResponseDto().content(TransactionTypeDtoMapper.toDto(transaction));
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

    private static final TransactionTypeDto toDto(final TransactionType transactionType) {
        return new TransactionTypeDto().number(transactionType.number())
            .description(transactionType.description())
            .description(transactionType.color());
    }

    private TransactionTypeDtoMapper() {
        super();
    }

}
