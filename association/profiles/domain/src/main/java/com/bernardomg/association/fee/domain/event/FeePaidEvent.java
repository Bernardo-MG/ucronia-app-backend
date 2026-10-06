
package com.bernardomg.association.fee.domain.event;

import java.time.Instant;
import java.time.YearMonth;
import java.util.Objects;
import java.util.UUID;

import com.bernardomg.event.domain.AbstractEvent;

public final class FeePaidEvent extends AbstractEvent {

    public static final String TYPE             = "association.fee.paid";

    private static final long  serialVersionUID = -1244701467322959610L;

    private final YearMonth    month;

    private final Instant      paymentDate;

    private final Long         profileNumber;

    private final Long         transactionIndex;

    public FeePaidEvent(final String source, final YearMonth month, final Long profileNumber,
            final Long transactionIndex, final Instant paymentDate) {
        this(UUID.randomUUID(), source, 1, Instant.now(), month, profileNumber, transactionIndex, paymentDate);
    }

    /** Restores metadata and payload without generating a new occurrence. */
    public FeePaidEvent(final UUID id, final String source, final int schemaVersion, final Instant timestamp,
            final YearMonth month, final Long profileNumber, final Long transactionIndex, final Instant paymentDate) {
        super(id, source, TYPE, schemaVersion, timestamp);
        if (schemaVersion != 1) {
            throw new IllegalArgumentException("Unsupported schema version: " + schemaVersion);
        }
        if ((transactionIndex == null) != (paymentDate == null)) {
            throw new IllegalArgumentException("Payment reference and date must both be present or absent");
        }
        this.month = Objects.requireNonNull(month, "month must not be null");
        this.profileNumber = Objects.requireNonNull(profileNumber, "profileNumber must not be null");
        this.transactionIndex = transactionIndex;
        this.paymentDate = paymentDate;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final FeePaidEvent other)) {
            return false;
        }
        return Objects.equals(getSource(), other.getSource()) && Objects.equals(getId(), other.getId());
    }

    public YearMonth getMonth() {
        return month;
    }

    /** May be null for fees paid without a transaction. */
    public Instant getPaymentDate() {
        return paymentDate;
    }

    public Long getProfileNumber() {
        return profileNumber;
    }

    /** May be null for fees paid without a transaction. */
    public Long getTransactionIndex() {
        return transactionIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getId());
    }

    @Override
    public String toString() {
        return "FeePaidEvent [id=" + getId() + ", type=" + TYPE + "]";
    }

}
