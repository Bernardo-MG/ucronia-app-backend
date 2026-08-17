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

package com.bernardomg.association.calendar.activity.domain.model;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.security.domain.audit.model.AuditDetails;

public record Activity(long number, String title, String description, String location, String image,
        Collection<ActivityDate> dates, AuditDetails audit) {

    public Activity(final long number, final String title, final String description, final String location,
            final String image, final Collection<ActivityDate> dates, final AuditDetails audit) {
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(title, "Title can't be null");
        Objects.requireNonNull(description, "Description can't be null");
        Objects.requireNonNull(location, "Location can't be null");
        Objects.requireNonNull(image, "Image can't be null");
        Objects.requireNonNull(dates, "Dates can't be null");

        this.number = number;
        this.title = StringUtils.trim(title);
        this.description = StringUtils.trim(description);
        this.location = StringUtils.trim(location);
        this.image = StringUtils.trim(image);
        this.dates = Collections.unmodifiableSet(new LinkedHashSet<>(dates));
        this.audit = audit;
    }

    public Activity(final long number, final String title, final String description, final String location,
            final String image, final Collection<ActivityDate> dates) {
        this(number, title, description, location, image, dates, new AuditDetails());
    }

    public record ActivityDate(Instant start, Instant end) {

        public ActivityDate(final Instant start, final Instant end) {
            Objects.requireNonNull(start, "Start date can't be null");
            Objects.requireNonNull(end, "End date can't be null");

            // TODO: check date order
            this.start = start;
            this.end = end;
        }

    }

}
