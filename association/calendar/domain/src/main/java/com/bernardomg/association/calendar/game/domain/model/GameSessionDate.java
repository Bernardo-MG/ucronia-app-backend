
package com.bernardomg.association.calendar.game.domain.model;

import java.time.Instant;
import java.util.Objects;

public record GameSessionDate(Instant start, Instant end) {

    public GameSessionDate(final Instant start, final Instant end) {
        Objects.requireNonNull(start, "Start date can't be null");
        Objects.requireNonNull(end, "End date can't be null");

        // TODO: check date order
        this.start = start;
        this.end = end;
    }

}
