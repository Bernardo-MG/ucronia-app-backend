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

package com.bernardomg.association.activity.adapter.inbound.jpa.model;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "CalendarDate")
@Table(schema = "calendar", name = "calendar_dates")
public class CalendarDateEntity implements Serializable {

    @Transient
    private static final long serialVersionUID = 370515764532595412L;

    @Column(name = "end_date")
    private Instant           end;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    @Column(name = "number", nullable = false, unique = true)
    private Long              number;

    @Column(name = "start_date", nullable = false)
    private Instant           start;

    public Instant getEnd() {
        return end;
    }

    public Long getId() {
        return id;
    }

    public Long getNumber() {
        return number;
    }

    public Instant getStart() {
        return start;
    }

    public void setEnd(final Instant end) {
        this.end = end;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setNumber(final Long number) {
        this.number = number;
    }

    public void setStart(final Instant start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "CalendarEntity [id=" + id + ", number=" + number + ", start=" + start + ", end=" + end + "]";
    }

}
