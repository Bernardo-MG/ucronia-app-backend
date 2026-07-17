
package com.bernardomg.association.calendar.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.bernardomg.association.calendar.domain.model.Recurrence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Embeddable
public class CalendarInfoRecurrence implements Serializable {

    @Transient
    private static final long         serialVersionUID = 1174279038101885709L;

    @Column(name = "recurrence_interval")
    private Integer                   interval;

    @Column(name = "recurrence_last_evaluated_at")
    private Instant                   lastEvaluatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurrence_status_id", foreignKey = @ForeignKey(name = "fk_calendar_info_recurrence_status"))
    private RecurrenceStatusEntity    status;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence_unit")
    private Recurrence.RecurrenceUnit unit;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final CalendarInfoRecurrence other = (CalendarInfoRecurrence) obj;
        return Objects.equals(interval, other.interval) && (unit == other.unit);
    }

    public Integer getInterval() {
        return interval;
    }

    public Instant getLastEvaluatedAt() {
        return lastEvaluatedAt;
    }

    public RecurrenceStatusEntity getStatus() {
        return status;
    }

    public Recurrence.RecurrenceUnit getUnit() {
        return unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(interval, unit);
    }

    public void setInterval(final Integer interval) {
        this.interval = interval;
    }

    public void setLastEvaluatedAt(final Instant lastEvaluatedAt) {
        this.lastEvaluatedAt = lastEvaluatedAt;
    }

    public void setStatus(final RecurrenceStatusEntity status) {
        this.status = status;
    }

    public void setUnit(final Recurrence.RecurrenceUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "RecurrenceEmbeddable [unit=" + unit + ", interval=" + interval + ", lastEvaluatedAt=" + lastEvaluatedAt
                + ", status=" + status + "]";
    }

}
