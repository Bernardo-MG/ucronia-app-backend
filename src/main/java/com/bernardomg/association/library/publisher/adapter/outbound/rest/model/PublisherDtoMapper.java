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

package com.bernardomg.association.library.publisher.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Sorting.Direction;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.ucronia.openapi.model.PropertyDto;
import com.bernardomg.ucronia.openapi.model.PropertyDto.DirectionEnum;
import com.bernardomg.ucronia.openapi.model.PublisherDto;
import com.bernardomg.ucronia.openapi.model.PublisherPageResponseDto;
import com.bernardomg.ucronia.openapi.model.PublisherResponseDto;
import com.bernardomg.ucronia.openapi.model.SortingDto;

public final class PublisherDtoMapper {

    public static final PublisherResponseDto toResponseDto(final Optional<Publisher> gameSystem) {
        return new PublisherResponseDto().content(gameSystem.map(PublisherDtoMapper::toDto)
            .orElse(null));
    }

    public static final PublisherPageResponseDto toResponseDto(final Page<Publisher> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(PublisherDtoMapper::toDto)
            .toList());
        return new PublisherPageResponseDto().content(page.content()
            .stream()
            .map(PublisherDtoMapper::toDto)
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

    public static final PublisherResponseDto toResponseDto(final Publisher gameSystem) {
        return new PublisherResponseDto().content(toDto(gameSystem));
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

    private static final PublisherDto toDto(final Publisher gameSystem) {
        return new PublisherDto().number(gameSystem.number())
            .name(gameSystem.name());
    }

    private PublisherDtoMapper() {
        super();
    }

}
