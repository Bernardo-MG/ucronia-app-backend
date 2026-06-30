
package com.bernardomg.association.calendar.game.adapter.inbound.jpa.model;

import java.io.Serializable;

import com.bernardomg.association.calendar.game.domain.model.Recurrence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class RecurrenceEmbeddable implements Serializable {

    private static final long         serialVersionUID = 1L;

    @Column(name = "recurrence_interval", nullable = false)
    private Integer                   interval;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence_unit", nullable = false)
    private Recurrence.RecurrenceUnit unit;

    public Integer getInterval() {
        return interval;
    }

    public Recurrence.RecurrenceUnit getUnit() {
        return unit;
    }

    public void setInterval(final Integer interval) {
        this.interval = interval;
    }

    public void setUnit(final Recurrence.RecurrenceUnit unit) {
        this.unit = unit;
    }

}
