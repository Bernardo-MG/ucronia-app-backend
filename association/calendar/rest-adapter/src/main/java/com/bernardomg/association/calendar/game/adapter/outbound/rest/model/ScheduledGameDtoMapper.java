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

import com.bernardomg.association.calendar.domain.model.CalendarStatus;
import com.bernardomg.association.calendar.domain.model.Recurrence;
import com.bernardomg.association.calendar.domain.model.Recurrence.RecurrenceStatus;
import com.bernardomg.association.calendar.domain.model.Recurrence.RecurrenceUnit;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.AuditDetailsDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.AuditUserDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.CalendarStatusDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.GameSessionTypeDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.RecurrenceDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.RecurrenceStatusDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.RecurrenceUnitDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.ScheduledGameCreationDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.ScheduledGameDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.ScheduledGamePageResponseDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.ScheduledGameResponseDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.ScheduledGameUpdateDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.association.calendar.game.domain.model.GameSessionType;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.security.domain.audit.model.AuditDetails.AuditUser;

public final class ScheduledGameDtoMapper {

    public static final ScheduledGame toDomain(final Long number, final ScheduledGameUpdateDto change) {
        final Optional<Recurrence> recurrence;
        final RecurrenceUnit       recurrenceUnit;
        final GameSessionType      gameSessionType;
        final Optional<Long>       table;

        if (change.getRecurrence() == null) {
            recurrence = Optional.empty();
        } else {
            recurrenceUnit = RecurrenceUnit.valueOf(change.getRecurrence()
                .getUnit()
                .toString()
                .toUpperCase());
            if (change.getRecurrence() == null) {
                recurrence = Optional.empty();
            } else {
                recurrence = Optional.of(new Recurrence(change.getRecurrence()
                    .getInterval(), recurrenceUnit, RecurrenceStatus.ACTIVE));
            }
        }
        gameSessionType = toGameSessionType(change.getGameType());
        if (change.getTable() == null) {
            table = Optional.empty();
        } else {
            table = Optional.of(change.getTable());
        }
        return new ScheduledGame(number, change.getTitle(), getNullable(change.getDescription()),
            getNullable(change.getLocation()), table, change.getMaster(), change.getMaxPlayers(),
            getNullable(change.getImage()), change.getStart(), recurrence, CalendarStatus.DRAFT, gameSessionType);
    }

    public static final ScheduledGame toDomain(final ScheduledGameCreationDto creation) {
        final Optional<Recurrence> recurrence;
        final RecurrenceUnit       recurrenceUnit;
        final GameSessionType      gameSessionType;
        final Optional<Long>       table;

        if (creation.getRecurrence() == null) {
            recurrence = Optional.empty();
        } else {
            recurrenceUnit = RecurrenceUnit.valueOf(creation.getRecurrence()
                .getUnit()
                .toString()
                .toUpperCase());
            if (creation.getRecurrence() == null) {
                recurrence = Optional.empty();
            } else {
                recurrence = Optional.of(new Recurrence(creation.getRecurrence()
                    .getInterval(), recurrenceUnit, RecurrenceStatus.ACTIVE));
            }
        }
        gameSessionType = toGameSessionType(creation.getGameType());
        if (creation.getTable() == null) {
            table = Optional.empty();
        } else {
            table = Optional.of(creation.getTable());
        }
        return new ScheduledGame(-1, creation.getTitle(), getNullable(creation.getDescription()),
            getNullable(creation.getLocation()), table, creation.getMaster(), creation.getMaxPlayers(),
            getNullable(creation.getImage()), creation.getStart(), recurrence, CalendarStatus.DRAFT, gameSessionType);
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

    private static final String getNullable(final String value) {
        final String text;

        if (value == null) {
            text = "";
        } else {
            text = value;
        }

        return text;
    }

    private static final AuditDetailsDto toDto(final AuditDetails audit) {
        final AuditDetailsDto dto;

        if (audit == null) {
            dto = null;
        } else {
            dto = new AuditDetailsDto().createdAt(audit.createdAt())
                .createdBy(toDto(audit.createdBy()))
                .updatedAt(audit.updatedAt())
                .updatedBy(toDto(audit.updatedBy()));
        }

        return dto;
    }

    private static final AuditUserDto toDto(final AuditUser user) {
        final AuditUserDto dto;

        if (user == null) {
            dto = null;
        } else {
            dto = new AuditUserDto().email(user.email())
                .username(user.username())
                .name(user.name());
        }
        return dto;
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
        final RecurrenceDto      recurrence;
        final GameSessionTypeDto gameTypeDto;
        final RecurrenceUnitDto  unit;
        final CalendarStatusDto  status;

        if (scheduledGame.recurrence()
            .isEmpty()) {
            recurrence = null;
        } else {
            unit = RecurrenceUnitDto.valueOf(scheduledGame.recurrence()
                .get()
                .unit()
                .toString()
                .toUpperCase());
            recurrence = new RecurrenceDto().interval(scheduledGame.recurrence()
                .get()
                .interval())
                .unit(unit)
                .status(RecurrenceStatusDto.ACTIVE);
        }
        gameTypeDto = toGameSessionTypeDto(scheduledGame.gameSessionType());
        status = CalendarStatusDto.valueOf(scheduledGame.status()
            .toString()
            .toUpperCase());
        return new ScheduledGameDto().number(scheduledGame.number())
            .title(scheduledGame.title())
            .description(scheduledGame.description())
            .location(scheduledGame.location())
            .table(scheduledGame.table()
                .orElse(null))
            .master(scheduledGame.master())
            .maxPlayers(scheduledGame.maxPlayers())
            .image(scheduledGame.image())
            .start(scheduledGame.start())
            .recurrence(recurrence)
            .status(status)
            .gameType(gameTypeDto)
            .audit(toDto(scheduledGame.audit()));
    }

    private static final GameSessionType toGameSessionType(final GameSessionTypeDto dto) {
        return switch (dto) {
            case ONESHOT -> GameSessionType.ONESHOT;
            case CAMPAIGN -> GameSessionType.CAMPAIGN;
        };
    }

    private static final GameSessionTypeDto toGameSessionTypeDto(final GameSessionType gameSessionType) {
        return switch (gameSessionType) {
            case ONESHOT -> GameSessionTypeDto.ONESHOT;
            case CAMPAIGN -> GameSessionTypeDto.CAMPAIGN;
        };
    }

    private ScheduledGameDtoMapper() {
        super();
    }

}
