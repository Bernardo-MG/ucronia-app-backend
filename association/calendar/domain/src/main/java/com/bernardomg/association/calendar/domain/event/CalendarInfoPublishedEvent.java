
package com.bernardomg.association.calendar.domain.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.bernardomg.event.domain.AbstractEvent;

public final class CalendarInfoPublishedEvent extends AbstractEvent {

    public static final String TYPE             = "association.calendar-info.published";

    private static final long  serialVersionUID = -4801654861571432672L;

    private final Long         calendarNumber;

    public CalendarInfoPublishedEvent(final String source, final Long calendarNumber) {
        super(source, TYPE, 1);
        
        this.calendarNumber = Objects.requireNonNull(calendarNumber);
    }

    public CalendarInfoPublishedEvent(final UUID id, final String source, final int schemaVersion,
            final Instant timestamp, final Long calendarNumber) {
        super(id, source, TYPE, schemaVersion, timestamp);
        
        this.calendarNumber = Objects.requireNonNull(calendarNumber);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final CalendarInfoPublishedEvent other)) {
            return false;
        }
        return Objects.equals(getSource(), other.getSource()) && Objects.equals(getId(), other.getId());
    }

    public final Long getCalendarNumber() {
        return calendarNumber;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getSource(), getId());
    }

    @Override
    public final String toString() {
        return "CalendarInfoPublishedEvent [id=" + getId() + ", type=" + TYPE + "]";
    }
    
}
