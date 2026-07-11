/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.association.calendar.domain.event;

import java.io.Serializable;
import java.util.Objects;

import com.bernardomg.event.domain.AbstractEvent;

/**
 * Calendar info published event.
 */
public final class CalendarInfoPublishedEvent extends AbstractEvent {

    private static final long serialVersionUID = 1341648481450018830L;

    private final Long        calendarNumber;

    public CalendarInfoPublishedEvent(final Serializable source, final Long number) {
        super(source);

        calendarNumber = Objects.requireNonNull(number);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof final CalendarInfoPublishedEvent other)) {
            return false;
        }
        return Objects.equals(getSource(), other.getSource()) && Objects.equals(calendarNumber, other.calendarNumber);
    }

    public Long getCalendarNumber() {
        return calendarNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), calendarNumber);
    }

    @Override
    public String toString() {
        return "FeeDeletedEvent [calendarNumber=" + calendarNumber + "]";
    }

}
