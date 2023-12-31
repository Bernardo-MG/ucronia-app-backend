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

package com.bernardomg.association.funds.transaction.persistence.repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.funds.transaction.model.TransactionQuery;
import com.bernardomg.association.funds.transaction.persistence.model.TransactionEntity;

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
    public static final Specification<TransactionEntity> betweenIncluding(final LocalDate start, final LocalDate end) {
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

        if (request.getDate() != null) {
            spec = Optional.of(on(request.getDate()));
        } else if ((request.getStartDate() != null) && (request.getEndDate() != null)) {
            spec = Optional.of(betweenIncluding(request.getStartDate(), request.getEndDate()));
        } else if (request.getStartDate() != null) {
            spec = Optional.of(onOrAfter(request.getStartDate()));
        } else if (request.getEndDate() != null) {
            spec = Optional.of(onOrBefore(request.getEndDate()));
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
    public static final Specification<TransactionEntity> on(final LocalDate date) {
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
        final LocalDate startDate;
        final LocalDate endDate;

        // Starts on the first day of the month
        startDate = LocalDate.of(month.getYear(), month.getMonthValue(), 1);
        // Ends on the last day of the month
        endDate = LocalDate.of(month.getYear(), month.getMonthValue(), month.getMonth()
            .length(month.isLeapYear()));

        return betweenIncluding(startDate, endDate);
    }

    /**
     * Transactions on or after the date.
     *
     * @param date
     *            date to mark the lower limit
     * @return transactions on or after the date
     */
    public static final Specification<TransactionEntity> onOrAfter(final LocalDate date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    /**
     * Transactions on or before the date.
     *
     * @param date
     *            date to mark the lower limit
     * @return transactions on or before the date
     */
    public static final Specification<TransactionEntity> onOrBefore(final LocalDate date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    private TransactionSpecifications() {
        super();
    }

}
