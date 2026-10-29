
package com.bernardomg.association.calendar.domain.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.bernardomg.event.domain.AbstractEvent;

public final class ActivityPublishedEvent extends AbstractEvent {

    public static final String TYPE             = "association.activity.published";

    private static final long  serialVersionUID = -3237004801176513554L;

    private final Long         activityNumber;

    public ActivityPublishedEvent(final String source, final Long activityNumber) {
        this(UUID.randomUUID(), source, 1, Instant.now(), activityNumber);
    }

    public ActivityPublishedEvent(final UUID id, final String source, final int schemaVersion, final Instant timestamp,
            final Long activityNumber) {
        super(id, source, TYPE, schemaVersion, timestamp);

        this.activityNumber = Objects.requireNonNull(activityNumber, "activityNumber must not be null");
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final ActivityPublishedEvent other)) {
            return false;
        }
        return Objects.equals(getSource(), other.getSource()) && Objects.equals(getId(), other.getId());
    }

    public Long getActivityNumber() {
        return activityNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getId());
    }

    @Override
    public String toString() {
        return "ActivityPublishedEvent [id=" + getId() + ", type=" + TYPE + "]";
    }

}
