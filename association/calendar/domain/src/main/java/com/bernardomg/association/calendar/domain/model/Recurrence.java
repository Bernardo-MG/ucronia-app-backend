
package com.bernardomg.association.calendar.domain.model;

import java.util.Objects;

public record Recurrence(int interval, RecurrenceUnit unit, RecurrenceStatus status) {

    public Recurrence {
        Objects.requireNonNull(interval, "Interval can't be null");
        Objects.requireNonNull(unit, "Unit can't be null");
        Objects.requireNonNull(status, "Status can't be null");

        // Apply validations like this
        // if (interval < 0) {
        // throw new IllegalArgumentException("Interval must be at least 0");
        // }
    }

    public enum RecurrenceUnit {
        DAILY, MONTHLY, WEEKLY
    }

    public enum RecurrenceStatus {
        ACTIVE, CANCELLED, COMPLETED
    }

}
