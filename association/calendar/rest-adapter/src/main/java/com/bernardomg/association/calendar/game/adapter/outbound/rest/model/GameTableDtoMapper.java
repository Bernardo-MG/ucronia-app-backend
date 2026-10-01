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

package com.bernardomg.association.calendar.game.adapter.outbound.rest.model;

import java.util.Optional;

import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.GameTableCreationDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.GameTableDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.GameTablePageResponseDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.GameTableResponseDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.GameTableUpdateDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

public final class GameTableDtoMapper {

    public static final GameTable toDomain(final GameTableCreationDto creation) {
        return new GameTable(-1, creation.getName(), creation.getDescription());
    }

    public static final GameTable toDomain(final Long number, final GameTableUpdateDto change) {
        return new GameTable(number, change.getName(), change.getDescription());
    }

    public static final GameTableResponseDto toResponseDto(final GameTable gameTable) {
        return new GameTableResponseDto().content(GameTableDtoMapper.toDto(gameTable));
    }

    public static final GameTableResponseDto toResponseDto(final Optional<GameTable> gameTable) {
        return new GameTableResponseDto().content(gameTable.map(GameTableDtoMapper::toDto)
            .orElse(null));
    }

    public static final GameTablePageResponseDto toResponseDto(final Page<GameTable> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(GameTableDtoMapper::toDto)
            .toList());
        return new GameTablePageResponseDto().content(page.content()
            .stream()
            .map(GameTableDtoMapper::toDto)
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

    private static final GameTableDto toDto(final GameTable gameTable) {
        return new GameTableDto().number(gameTable.number())
            .name(gameTable.name())
            .description(gameTable.description());
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

    private GameTableDtoMapper() {
        super();
    }

}
