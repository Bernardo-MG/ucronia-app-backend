
package com.bernardomg.association.calendar.game.domain.model;

import java.util.Objects;

public record Recurrence(RecurrenceUnit unit, int interval) {

    public Recurrence {
        Objects.requireNonNull(unit);

        if (interval < 1) {
            throw new IllegalArgumentException("Interval must be at least 1");
        }
    }

    public enum RecurrenceUnit {
        DAILY, MONTHLY, WEEKLY, YEARLY
    }

}
