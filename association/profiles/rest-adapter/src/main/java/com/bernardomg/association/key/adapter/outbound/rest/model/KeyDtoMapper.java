/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
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

package com.bernardomg.association.key.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyCreationDto;
import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyDto;
import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyPageResponseDto;
import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyResponseDto;
import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyUpdateDto;
import com.bernardomg.association.key.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.association.key.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.association.key.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.association.key.domain.model.Key;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

public final class KeyDtoMapper {

    public static Key toDomain(final KeyCreationDto creation) {
        final Boolean available;
        final String  description;

        if (creation.getAvailable() == null) {
            available = false;
        } else {
            available = creation.getAvailable();
        }
        if (creation.getDescription() == null) {
            description = "";
        } else {
            description = creation.getDescription();
        }

        return new Key(-1L, available, description);
    }

    public static Key toDomain(final long number, final KeyUpdateDto change) {
        return new Key(number, change.getAvailable(), change.getDescription());
    }

    public static KeyResponseDto toResponseDto(final Key key) {
        return new KeyResponseDto().content(toDto(key));
    }

    public static KeyResponseDto toResponseDto(final Optional<Key> key) {
        return new KeyResponseDto().content(key.map(KeyDtoMapper::toDto)
            .orElse(null));
    }

    public static final KeyPageResponseDto toResponseDto(final Page<Key> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(KeyDtoMapper::toDto)
            .toList());
        return new KeyPageResponseDto().content(page.content()
            .stream()
            .map(KeyDtoMapper::toDto)
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

    private static KeyDto toDto(final Key key) {
        return new KeyDto().number(key.number())
            .available(key.available())
            .description(key.description());
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

    private KeyDtoMapper() {
        super();
    }

}
