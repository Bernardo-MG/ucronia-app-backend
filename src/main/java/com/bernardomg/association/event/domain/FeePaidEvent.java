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

package com.bernardomg.association.event.domain;

import java.time.YearMonth;
import java.util.Objects;

import com.bernardomg.event.domain.AbstractEvent;

/**
 * Log in attempt event. It is created no matter if the attempt was succesful or not.
 */
public final class FeePaidEvent extends AbstractEvent {

    private static final long serialVersionUID = 7044023838333219109L;

    private final Long        contactNumber;

    private final YearMonth   date;

    public FeePaidEvent(final Object source, final YearMonth d, final Long number) {
        super(source);

        date = Objects.requireNonNull(d);
        contactNumber = Objects.requireNonNull(number);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final FeePaidEvent other = (FeePaidEvent) obj;
        return Objects.equals(date, other.date) && Objects.equals(contactNumber, other.contactNumber);
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public YearMonth getDate() {
        return date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, contactNumber);
    }

}
