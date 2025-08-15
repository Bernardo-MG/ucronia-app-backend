/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.transaction.adapter.inbound.jpa.specification;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;

/**
 * Specifications for transactions.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class TransactionSpecifications {

    /**
     * Transactions between both dates, including them.
     *
     * @param start
     *            starting date
     * @param end
     *            final date
     * @return transactions between both dates
     */
    public static final Specification<TransactionEntity> betweenIncluding(final Instant start, final Instant end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    /**
     * Creates an specification from the request.
     *
     * @param request
     *            request to create a specification from
     * @return specification for the request
     */
    public static final Optional<Specification<TransactionEntity>> fromQuery(final TransactionQuery request) {
        final Optional<Specification<TransactionEntity>> spec;

        if (request.date() != null) {
            spec = Optional.of(on(request.date()));
        } else if ((request.startDate() != null) && (request.endDate() != null)) {
            spec = Optional.of(betweenIncluding(request.startDate(), request.endDate()));
        } else if (request.startDate() != null) {
            spec = Optional.of(onOrAfter(request.startDate()));
        } else if (request.endDate() != null) {
            spec = Optional.of(onOrBefore(request.endDate()));
        } else {
            spec = Optional.empty();
        }

        return spec;
    }

    /**
     * Transactions on the date.
     *
     * @param date
     *            date to search on
     * @return transactions on the date
     */
    public static final Specification<TransactionEntity> on(final Instant date) {
        // TODO: Should remove hour?
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    /**
     * Transactions on the month.
     *
     * @param month
     *            month to search on
     * @return transactions on the date
     */
    public static final Specification<TransactionEntity> on(final YearMonth month) {
        final Instant startDate;
        final Instant endDate;

        // Starts on the first day of the month
        startDate = LocalDate.of(month.getYear(), month.getMonthValue(), 1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        // Ends on the last day of the month
        endDate = LocalDate.of(month.getYear(), month.getMonthValue(), month.getMonth()
            .length(month.isLeapYear()))
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        return betweenIncluding(startDate, endDate);
    }

    /**
     * Transactions on or after the date.
     *
     * @param date
     *            date to mark the lower limit
     * @return transactions on or after the date
     */
    public static final Specification<TransactionEntity> onOrAfter(final Instant date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    /**
     * Transactions on or before the date.
     *
     * @param date
     *            date to mark the lower limit
     * @return transactions on or before the date
     */
    public static final Specification<TransactionEntity> onOrBefore(final Instant date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    private TransactionSpecifications() {
        super();
    }

}
