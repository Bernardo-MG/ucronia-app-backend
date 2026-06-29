
package com.bernardomg.association.calendar.game.domain.model;

import java.time.Instant;
import java.util.Objects;

public record GameSessionDate(Instant start, Instant end) {

    public GameSessionDate(final Instant start, final Instant end) {
        this.start = Objects.requireNonNull(start);
        this.end = Objects.requireNonNull(end);
    }

}
