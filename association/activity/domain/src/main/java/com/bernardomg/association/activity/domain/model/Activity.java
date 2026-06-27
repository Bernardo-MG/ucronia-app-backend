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

package com.bernardomg.association.activity.domain.model;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public record Activity(long number, String title, String description, String location, String image,
        Collection<ActivityDate> dates) {

    public Activity(final long number, final String title, final String description, final String location,
            final String image, final Collection<ActivityDate> dates) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(description);
        Objects.requireNonNull(location);
        Objects.requireNonNull(image);
        Objects.requireNonNull(dates);

        this.number = Objects.requireNonNull(number);
        this.title = StringUtils.trim(title);
        this.description = StringUtils.trim(description);
        this.location = StringUtils.trim(location);
        this.image = StringUtils.trim(image);
        this.dates = Collections.unmodifiableSet(new LinkedHashSet<>(dates));
    }

    public record ActivityDate(Instant start, Instant end) {

        public ActivityDate(final Instant start, final Instant end) {
            this.start = Objects.requireNonNull(start);
            this.end = Objects.requireNonNull(end);
        }

    }

}
