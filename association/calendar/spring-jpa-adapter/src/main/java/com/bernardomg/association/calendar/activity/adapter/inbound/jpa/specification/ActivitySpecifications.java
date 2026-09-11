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

package com.bernardomg.association.calendar.activity.adapter.inbound.jpa.specification;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.calendar.activity.domain.filter.ActivityFilter;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarDateEntity;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoEntity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public final class ActivitySpecifications {

    public static final Optional<Specification<CalendarInfoEntity>> filter(final ActivityFilter filter) {
        final Optional<Specification<CalendarInfoEntity>> fromSpec;
        final Optional<Specification<CalendarInfoEntity>> toSpec;

        fromSpec = filter.from()
            .map(ActivitySpecifications::endingOnOrAfter);
        toSpec = filter.to()
            .map(ActivitySpecifications::entirelyEndedBefore);

        return List.of(fromSpec, toSpec)
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .reduce((BinaryOperator<Specification<CalendarInfoEntity>>) Specification::and);
    }

    private static final Specification<CalendarInfoEntity> endingOnOrAfter(final Instant date) {
        return (root, query, cb) -> {
            final Join<CalendarInfoEntity, CalendarDateEntity> dates;
            final Root<CalendarInfoEntity>                     correlated;
            final Subquery<Long>                               subquery;

            subquery = query.subquery(Long.class);
            correlated = subquery.correlate(root);
            dates = correlated.join("calendarDates");
            subquery.select(cb.literal(1L))
                .where(cb.greaterThanOrEqualTo(dates.get("end"), date));

            return cb.exists(subquery);
        };
    }

    private static final Specification<CalendarInfoEntity> entirelyEndedBefore(final Instant date) {
        return (root, query, cb) -> {
            final Join<CalendarInfoEntity, CalendarDateEntity> dates;
            final Root<CalendarInfoEntity>                     correlated;
            final Subquery<Long>                               subquery;

            subquery = query.subquery(Long.class);
            correlated = subquery.correlate(root);
            dates = correlated.join("calendarDates");
            subquery.select(cb.literal(1L))
                .where(cb.greaterThanOrEqualTo(dates.get("end"), date));

            return cb.not(cb.exists(subquery));
        };
    }

    private ActivitySpecifications() {
        super();
    }

}
