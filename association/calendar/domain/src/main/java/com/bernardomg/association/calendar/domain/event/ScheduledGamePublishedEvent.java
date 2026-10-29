
package com.bernardomg.association.calendar.domain.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.bernardomg.event.domain.AbstractEvent;

public final class ScheduledGamePublishedEvent extends AbstractEvent {

    public static final String TYPE             = "association.scheduled-game.published";

    private static final long  serialVersionUID = -8366783893174211474L;

    private final Long         scheduledGameNumber;

    public ScheduledGamePublishedEvent(final String source, final Long scheduledGameNumber) {
        this(UUID.randomUUID(), source, 1, Instant.now(), scheduledGameNumber);
    }

    public ScheduledGamePublishedEvent(final UUID id, final String source, final int schemaVersion,
            final Instant timestamp, final Long scheduledGameNumber) {
        super(id, source, TYPE, schemaVersion, timestamp);

        this.scheduledGameNumber = Objects.requireNonNull(scheduledGameNumber, "scheduledGameNumber must not be null");
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final ScheduledGamePublishedEvent other)) {
            return false;
        }
        return Objects.equals(getSource(), other.getSource()) && Objects.equals(getId(), other.getId());
    }

    public Long getScheduledGameNumber() {
        return scheduledGameNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getId());
    }

    @Override
    public String toString() {
        return "ScheduledGamePublishedEvent [id=" + getId() + ", type=" + TYPE + "]";
    }

}
