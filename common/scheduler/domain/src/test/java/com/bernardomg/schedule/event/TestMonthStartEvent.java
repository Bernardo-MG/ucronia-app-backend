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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class TestMonthStartEvent {

    private MonthStartEvent event;

    private MonthStartEvent getNewEvent() {
        return ScheduleEventFactory.monthStarted(Instant.EPOCH);
    }

    private MonthStartEvent getRestoredEvent(final UUID id, final Instant timestamp) {
        return new MonthStartEvent(id, "com.bernardomg.ucronia", 1, timestamp, Instant.EPOCH);
    }

    @Test
    void testIdentity() {
        final MonthStartEvent other;

        // GIVEN
        other = getNewEvent();

        // WHEN
        event = getNewEvent();

        // THEN
        assertNotEquals(other, event);
    }

    @Test
    void testRestoredId() {
        final UUID id;

        // GIVEN
        id = UUID.randomUUID();

        // WHEN
        event = getRestoredEvent(id, Instant.EPOCH);

        // THEN
        assertEquals(id, event.getId());
    }

    @Test
    void testRestoredTimestamp() {
        final Instant timestamp;

        // GIVEN
        timestamp = Instant.parse("2026-01-01T00:00:00Z");

        // WHEN
        event = getRestoredEvent(UUID.randomUUID(), timestamp);

        // THEN
        assertEquals(timestamp, event.getTimestamp());
    }

    @Test
    void testType() {

        // GIVEN
        // Event type is defined by the concrete class.

        // WHEN
        event = getNewEvent();

        // THEN
        assertEquals(MonthStartEvent.TYPE, event.getType());
    }

    @Test
    void testSource() {

        // GIVEN
        // Source is owned by the factory.

        // WHEN
        event = getNewEvent();

        // THEN
        assertEquals("com.bernardomg.ucronia", event.getSource());
    }

    @Test
    void testSchemaVersion() {

        // GIVEN
        // New occurrences use the current schema.

        // WHEN
        event = getNewEvent();

        // THEN
        assertEquals(MonthStartEvent.SCHEMA_VERSION, event.getSchemaVersion());
    }

    @Test
    void testRestoredIdentity() {
        final MonthStartEvent original;

        // GIVEN
        original = getNewEvent();

        // WHEN
        event = getRestoredEvent(original.getId(), original.getTimestamp());

        // THEN
        assertEquals(original, event);
    }

    @Test
    void testRestoredHashCode() {
        final MonthStartEvent original;

        // GIVEN
        original = getNewEvent();

        // WHEN
        event = getRestoredEvent(original.getId(), original.getTimestamp());

        // THEN
        assertEquals(original.hashCode(), event.hashCode());
    }

}
