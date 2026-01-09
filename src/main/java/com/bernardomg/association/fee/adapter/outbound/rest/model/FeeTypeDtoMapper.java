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

package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.FeeTypeChangeDto;
import com.bernardomg.ucronia.openapi.model.FeeTypeCreationDto;
import com.bernardomg.ucronia.openapi.model.FeeTypeDto;
import com.bernardomg.ucronia.openapi.model.FeeTypePageResponseDto;
import com.bernardomg.ucronia.openapi.model.FeeTypeResponseDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class FeeTypeDtoMapper {

    public static final FeeType toDomain(final FeeTypeCreationDto creation) {
        return new FeeType(-1, creation.getName(), creation.getAmount());
    }

    public static final FeeType toDomain(final Long number, final FeeTypeChangeDto change) {
        return new FeeType(number, change.getName(), change.getAmount());
    }

    public static final FeeTypeResponseDto toResponseDto(final FeeType transaction) {
        return new FeeTypeResponseDto().content(FeeTypeDtoMapper.toDto(transaction));
    }

    public static final FeeTypeResponseDto toResponseDto(final Optional<FeeType> transaction) {
        return new FeeTypeResponseDto().content(transaction.map(FeeTypeDtoMapper::toDto)
            .orElse(null));
    }

    public static final FeeTypePageResponseDto toResponseDto(final Page<FeeType> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(FeeTypeDtoMapper::toDto)
            .toList());
        return new FeeTypePageResponseDto().content(page.content()
            .stream()
            .map(FeeTypeDtoMapper::toDto)
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

    private static final FeeTypeDto toDto(final FeeType transaction) {
        return new FeeTypeDto().number(transaction.number())
            .name(transaction.name())
            .amount(transaction.amount());
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

    private FeeTypeDtoMapper() {
        super();
    }

}
