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

package com.bernardomg.association.calendar.game.adapter.inbound.jpa.model;

import java.util.Optional;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoRecurrence;
import com.bernardomg.association.calendar.domain.model.Recurrence;
import com.bernardomg.association.calendar.domain.model.Recurrence.RecurrenceStatus;
import com.bernardomg.association.calendar.game.domain.model.GameSessionType;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditMetadata;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditUserEntity;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.security.domain.audit.model.AuditDetails.AuditUser;

/**
 * Scheduled game repository mapper.
 */
public final class ScheduledGameEntityMapper {

    public static final ScheduledGame toDomain(final ScheduledGameEntity entity) {
        final Optional<Recurrence> recurrence;
        final GameSessionType      gameSessionType;
        final AuditDetails         audit;
        final Optional<GameTable>  table;

        if (entity.getRecurrence() == null) {
            recurrence = Optional.empty();
        } else {
            recurrence = Optional.of(new Recurrence(entity.getRecurrence()
                .getInterval(),
                entity.getRecurrence()
                    .getUnit(),
                RecurrenceStatus.ACTIVE));
        }

        gameSessionType = (entity.getTypes() != null) && entity.getTypes()
            .stream()
            .anyMatch(t -> t.getNumber() == ScheduledGameEntityConstants.CAMPAIGN_TYPE) ? GameSessionType.CAMPAIGN
                    : GameSessionType.ONESHOT;

        audit = toDomain(entity.getAudit());

        if (entity.getTable() == null) {
            table = Optional.empty();
        } else {
            table = Optional.of(toDomain(entity.getTable()));
        }

        return new ScheduledGame(entity.getNumber(), entity.getTitle(), entity.getDescription(), entity.getLocation(),
            table, entity.getMaster()
                .getNumber(),
            entity.getMaxPlayers(), entity.getImage(), entity.getStart(), recurrence, entity.getStatus()
                .getName(),
            gameSessionType, audit);
    }

    public static final ScheduledGameEntity toEntity(final ScheduledGame scheduledGame) {
        final ScheduledGameEntity    entity;
        final CalendarInfoRecurrence recurrence;

        entity = new ScheduledGameEntity();
        entity.setNumber(scheduledGame.number());
        entity.setTitle(scheduledGame.title());
        entity.setDescription(scheduledGame.description());
        entity.setLocation(scheduledGame.location());
        entity.setMaxPlayers(scheduledGame.maxPlayers());
        entity.setImage(scheduledGame.image());
        entity.setStart(scheduledGame.start());

        if (scheduledGame.recurrence()
            .isPresent()) {
            recurrence = new CalendarInfoRecurrence();
            recurrence.setInterval(scheduledGame.recurrence()
                .get()
                .interval());
            recurrence.setUnit(scheduledGame.recurrence()
                .get()
                .unit());
            entity.setRecurrence(recurrence);
        }

        return entity;
    }

    private static final AuditUser toAuditDomain(final AuditUserEntity user) {
        final AuditUser auditUser;

        if (user == null) {
            auditUser = null;
        } else {
            auditUser = new AuditUser(user.getEmail(), user.getUsername(), user.getName());
        }

        return auditUser;
    }

    private static final AuditDetails toDomain(final AuditMetadata audit) {
        final AuditDetails auditDetails;

        if (audit == null) {
            auditDetails = new AuditDetails();
        } else {
            auditDetails = new AuditDetails(audit.getCreatedAt(), toAuditDomain(audit.getCreatedBy()),
                audit.getUpdatedAt(), toAuditDomain(audit.getUpdatedBy()));
        }

        return auditDetails;
    }

    private static final GameTable toDomain(final GameTableEntity entity) {
        return new GameTable(entity.getNumber(), entity.getName(), entity.getDescription());
    }

    private ScheduledGameEntityMapper() {
        super();
    }

}
