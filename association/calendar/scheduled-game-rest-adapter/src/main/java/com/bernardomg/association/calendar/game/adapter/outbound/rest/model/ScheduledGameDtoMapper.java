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

import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.RecurrenceDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.RecurrenceDto.UnitEnum;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.ScheduledGameCreationDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.ScheduledGameDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.ScheduledGamePageResponseDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.ScheduledGameResponseDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.ScheduledGameUpdateDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.association.calendar.game.domain.model.Recurrence;
import com.bernardomg.association.calendar.game.domain.model.Recurrence.RecurrenceUnit;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGameMember;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGameMember.Name;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

public final class ScheduledGameDtoMapper {

    public static final ScheduledGame toDomain(final Long number, final ScheduledGameUpdateDto change) {
        final ScheduledGameMember master;
        final Recurrence          recurrence;
        final RecurrenceUnit      recurrenceUnit;

        master = new ScheduledGameMember(change.getNumber(), new Name("", ""));
        recurrenceUnit = RecurrenceUnit.valueOf(change.getRecurrence()
            .getUnit()
            .toString()
            .toUpperCase());
        recurrence = new Recurrence(change.getRecurrence()
            .getInterval(), recurrenceUnit);
        return new ScheduledGame(-1, change.getTitle(), change.getDescription(), change.getLocation(), master,
            change.getMaxPlayers(), change.getImage(), change.getStart(), recurrence, false);
    }

    public static final ScheduledGame toDomain(final ScheduledGameCreationDto creation) {
        final ScheduledGameMember master;
        final Recurrence          recurrence;
        final RecurrenceUnit      recurrenceUnit;

        master = new ScheduledGameMember(creation.getNumber(), new Name("", ""));
        recurrenceUnit = RecurrenceUnit.valueOf(creation.getRecurrence()
            .getUnit()
            .toString()
            .toUpperCase());
        recurrence = new Recurrence(creation.getRecurrence()
            .getInterval(), recurrenceUnit);
        return new ScheduledGame(-1, creation.getTitle(), creation.getDescription(), creation.getLocation(), master,
            creation.getMaxPlayers(), creation.getImage(), creation.getStart(), recurrence, false);
    }

    public static final ScheduledGameResponseDto toResponseDto(final Optional<ScheduledGame> scheduledGame) {
        return new ScheduledGameResponseDto().content(scheduledGame.map(ScheduledGameDtoMapper::toDto)
            .orElse(null));
    }

    public static final ScheduledGamePageResponseDto toResponseDto(final Page<ScheduledGame> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(ScheduledGameDtoMapper::toDto)
            .toList());
        return new ScheduledGamePageResponseDto().content(page.content()
            .stream()
            .map(ScheduledGameDtoMapper::toDto)
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

    public static final ScheduledGameResponseDto toResponseDto(final ScheduledGame scheduledGame) {
        return new ScheduledGameResponseDto().content(ScheduledGameDtoMapper.toDto(scheduledGame));
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

    private static final ScheduledGameDto toDto(final ScheduledGame scheduledGame) {
        final RecurrenceDto recurrence;

        recurrence = new RecurrenceDto().interval(scheduledGame.recurrence()
            .interval())
            .unit(UnitEnum.valueOf(scheduledGame.recurrence()
                .unit()
                .toString()
                .toLowerCase()));
        return new ScheduledGameDto().number(scheduledGame.number())
            .title(scheduledGame.title())
            .description(scheduledGame.description())
            .location(scheduledGame.location())
            .master(scheduledGame.master()
                .number())
            .maxPlayers(scheduledGame.maxPlayers())
            .image(scheduledGame.image())
            .start(scheduledGame.start())
            .recurrence(recurrence)
            .published(scheduledGame.published());
    }

    private ScheduledGameDtoMapper() {
        super();
    }

}
