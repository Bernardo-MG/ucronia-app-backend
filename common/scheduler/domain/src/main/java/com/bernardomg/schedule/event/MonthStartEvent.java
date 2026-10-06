
package com.bernardomg.schedule.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.bernardomg.event.domain.AbstractEvent;

public final class MonthStartEvent extends AbstractEvent {

    public static final String TYPE             = "schedule.month.started";

    private static final long  serialVersionUID = 2L;

    private final Instant      month;

    public MonthStartEvent(final String source, final Instant month) {
        super(source, TYPE, 1);

        this.month = Objects.requireNonNull(month);
    }

    public MonthStartEvent(final UUID id, final String source, final int schemaVersion, final Instant timestamp,
            final Instant month) {
        super(id, source, TYPE, schemaVersion, timestamp);

        this.month = Objects.requireNonNull(month);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final MonthStartEvent other)) {
            return false;
        }
        return Objects.equals(getSource(), other.getSource()) && Objects.equals(getId(), other.getId());
    }

    public final Instant getMonth() {
        return month;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getSource(), getId());
    }

    @Override
    public final String toString() {
        return "MonthStartEvent [id=" + getId() + ", type=" + TYPE + "]";
    }

}
