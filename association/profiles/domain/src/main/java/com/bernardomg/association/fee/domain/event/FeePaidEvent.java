
package com.bernardomg.association.fee.domain.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.bernardomg.event.domain.AbstractEvent;

public final class FeePaidEvent extends AbstractEvent {

    public static final String TYPE             = "association.fee.paid";

    private static final long  serialVersionUID = 1961853090434720390L;

    private final Instant      date;

    private final Long         profileNumber;

    public FeePaidEvent(final String source, final Instant date, final Long profileNumber) {
        super(source, TYPE, 1);

        this.date = Objects.requireNonNull(date);
        this.profileNumber = Objects.requireNonNull(profileNumber);
    }

    /** Restores original metadata without generating a new occurrence. */
    public FeePaidEvent(final UUID id, final String source, final int schemaVersion, final Instant timestamp,
            final Instant date, final Long profileNumber) {
        super(id, source, TYPE, schemaVersion, timestamp);

        this.date = Objects.requireNonNull(date);
        this.profileNumber = Objects.requireNonNull(profileNumber);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final FeePaidEvent other)) {
            return false;
        }
        return Objects.equals(getSource(), other.getSource()) && Objects.equals(getId(), other.getId());
    }

    public final Instant getDate() {
        return date;
    }

    public final Long getProfileNumber() {
        return profileNumber;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getSource(), getId());
    }

    @Override
    public final String toString() {
        return "FeePaidEvent [id=" + getId() + ", type=" + TYPE + "]";
    }

}
