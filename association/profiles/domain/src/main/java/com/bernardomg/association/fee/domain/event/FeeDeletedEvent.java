
package com.bernardomg.association.fee.domain.event;

import java.time.Instant;
import java.time.YearMonth;
import java.util.Objects;
import java.util.UUID;

import com.bernardomg.event.domain.AbstractEvent;

public final class FeeDeletedEvent extends AbstractEvent {

    public static final String TYPE             = "association.fee.deleted";

    private static final long  serialVersionUID = -8691744923922306379L;

    private final YearMonth    month;

    private final boolean      paid;

    private final Long         profileNumber;

    public FeeDeletedEvent(final String source, final YearMonth month, final Long profileNumber, final boolean paid) {
        this(UUID.randomUUID(), source, 1, Instant.now(), month, profileNumber, paid);
    }

    public FeeDeletedEvent(final UUID id, final String source, final int schemaVersion, final Instant timestamp,
            final YearMonth month, final Long profileNumber, final boolean paid) {
        super(id, source, TYPE, schemaVersion, timestamp);

        this.month = Objects.requireNonNull(month, "month must not be null");
        this.profileNumber = Objects.requireNonNull(profileNumber, "profileNumber must not be null");
        this.paid = paid;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final FeeDeletedEvent other)) {
            return false;
        }
        return Objects.equals(getSource(), other.getSource()) && Objects.equals(getId(), other.getId());
    }

    public YearMonth getMonth() {
        return month;
    }

    public Long getProfileNumber() {
        return profileNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getId());
    }

    public boolean isPaid() {
        return paid;
    }

    @Override
    public String toString() {
        return "FeeDeletedEvent [id=" + getId() + ", type=" + TYPE + "]";
    }

}
