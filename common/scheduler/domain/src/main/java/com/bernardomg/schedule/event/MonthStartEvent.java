/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023-2025 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.schedule.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.bernardomg.event.domain.AbstractEvent;

/** schedule.month.started. Identity is determined by source and event ID. */
public final class MonthStartEvent extends AbstractEvent {

    private static final long serialVersionUID = 2L;

    public static final String TYPE = "schedule.month.started";

    public static final int SCHEMA_VERSION = 1;

    private final Instant month;

    public MonthStartEvent(final String source, final Instant month) {
        super(source, TYPE, SCHEMA_VERSION);
        this.month = Objects.requireNonNull(month);
    }

    /** Restores original metadata without generating a new occurrence. */
    public MonthStartEvent(final UUID id, final String source, final int schemaVersion,
            final Instant timestamp, final Instant month) {
        super(id, source, TYPE, schemaVersion, timestamp);
        if (schemaVersion < 1) {
            throw new IllegalArgumentException("schemaVersion must be positive");
        }
        this.month = Objects.requireNonNull(month);
    }

    public final Instant getMonth() {
        return month;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final MonthStartEvent other)) {
            return false;
        }
        return Objects.equals(getSource(), other.getSource()) && Objects.equals(getId(), other.getId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getSource(), getId());
    }

    @Override
    public final String toString() {
        return "MonthStartEvent [id=" + getId() + ", type=" + TYPE + "]";
    }
}
