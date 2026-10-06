
package com.bernardomg.association.fee.domain.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.bernardomg.event.domain.AbstractEvent;

public final class FeeDeletedEvent extends AbstractEvent {

    public static final String TYPE             = "association.fee.deleted";

    private static final long  serialVersionUID = -4831690995028130952L;

    private final Instant      date;

    private final Long         profileNumber;

    public FeeDeletedEvent(final String source, final Instant date, final Long profileNumber) {
        super(source, TYPE, 1);

        this.date = Objects.requireNonNull(date);
        this.profileNumber = Objects.requireNonNull(profileNumber);
    }

    public FeeDeletedEvent(final UUID id, final String source, final int schemaVersion, final Instant timestamp,
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
        if (!(obj instanceof final FeeDeletedEvent other)) {
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
        return "FeeDeletedEvent [id=" + getId() + ", type=" + TYPE + "]";
    }

}
