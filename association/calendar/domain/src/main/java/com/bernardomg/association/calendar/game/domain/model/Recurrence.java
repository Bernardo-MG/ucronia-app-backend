
package com.bernardomg.association.calendar.game.domain.model;

import java.util.Objects;

public record Recurrence(int interval, RecurrenceUnit unit) {

    public Recurrence {
        Objects.requireNonNull(interval, "Interval can't be null");
        Objects.requireNonNull(unit, "Unit can't be null");

        // Apply validations like this
        // if (interval < 0) {
        // throw new IllegalArgumentException("Interval must be at least 0");
        // }
    }

    public enum RecurrenceUnit {
        DAILY, MONTHLY, WEEKLY
    }

}
