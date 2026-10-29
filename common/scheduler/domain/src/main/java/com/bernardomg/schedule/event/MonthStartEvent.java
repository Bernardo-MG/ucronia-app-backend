
package com.bernardomg.schedule.event;

import java.time.Instant;
import java.time.YearMonth;
import java.util.Objects;
import java.util.UUID;

import com.bernardomg.event.domain.AbstractEvent;

public final class MonthStartEvent extends AbstractEvent {

    public static final String TYPE             = "schedule.month.started";

    private static final long  serialVersionUID = 1614635848287374294L;

    private final YearMonth    month;

    public MonthStartEvent(final String source, final YearMonth month) {
        this(UUID.randomUUID(), source, 1, Instant.now(), month);
    }

    public MonthStartEvent(final UUID id, final String source, final int schemaVersion, final Instant timestamp,
            final YearMonth month) {
        super(id, source, TYPE, schemaVersion, timestamp);

        this.month = Objects.requireNonNull(month, "month must not be null");
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final MonthStartEvent other)) {
            return false;
        }
        return Objects.equals(getSource(), other.getSource()) && Objects.equals(getId(), other.getId());
    }

    public YearMonth getMonth() {
        return month;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getId());
    }

    @Override
    public String toString() {
        return "MonthStartEvent [id=" + getId() + ", type=" + TYPE + "]";
    }

}
