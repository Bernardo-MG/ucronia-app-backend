
package com.bernardomg.association.calendar.game.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import com.bernardomg.association.calendar.game.domain.model.Recurrence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;

@Embeddable
public class RecurrenceEmbeddable implements Serializable {

    @Transient
    private static final long         serialVersionUID = 1174279038101885709L;

    @Column(name = "recurrence_interval", nullable = false)
    private Integer                   interval;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence_unit", nullable = false)
    private Recurrence.RecurrenceUnit unit;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final RecurrenceEmbeddable other = (RecurrenceEmbeddable) obj;
        return Objects.equals(interval, other.interval) && (unit == other.unit);
    }

    public Integer getInterval() {
        return interval;
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

    public void setUnit(final Recurrence.RecurrenceUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "RecurrenceEmbeddable [unit=" + unit + ", interval=" + interval + "]";
    }

}
