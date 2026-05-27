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

package com.bernardomg.association.transaction.adapter.inbound.jpa.specification;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.domain.filter.TransactionFilter;

/**
 * Specifications for transactions.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class TransactionSpecifications {

    /**
     * Creates an specification from the request.
     *
     * @param filter
     *            request to create a specification from
     * @return specification for the request
     */
    public static final Optional<Specification<TransactionEntity>> filter(final TransactionFilter filter) {
        final Optional<Specification<TransactionEntity>> descriptionSpec;
        final Optional<Specification<TransactionEntity>> dateSpec;

        if (filter.description()
            .isEmpty()) {
            descriptionSpec = Optional.empty();
        } else {
            descriptionSpec = Optional.of(description(filter.description()
                .get()));
        }

        if (filter.date()
            .isPresent()) {
            dateSpec = Optional.of(on(filter.date()
                .get()));
        } else if ((filter.from()
            .isPresent())
                && (filter.to()
                    .isPresent())) {
            dateSpec = Optional.of(betweenIncluding(filter.from()
                .get(),
                filter.to()
                    .get()));
        } else if (filter.from()
            .isPresent()) {
            dateSpec = Optional.of(onOrAfter(filter.from()
                .get()));
        } else if (filter.to()
            .isPresent()) {
            dateSpec = Optional.of(onOrBefore(filter.to()
                .get()));
        } else {
            dateSpec = Optional.empty();
        }

        return List.of(descriptionSpec, dateSpec)
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .reduce((BinaryOperator<Specification<TransactionEntity>>) Specification::and);
    }

    /**
     * Transactions between both dates, including them.
     *
     * @param start
     *            starting date
     * @param end
     *            final date
     * @return transactions between both dates
     */
    private static final Specification<TransactionEntity> betweenIncluding(final Instant start, final Instant end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    /**
     * Description. Accepting partial matching.
     *
     * @param pattern
     *            pattern to match
     * @return name specification
     */
    private static Specification<TransactionEntity> description(final String pattern) {
        final String likePattern = "%" + pattern + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("description")), likePattern.toLowerCase());
    }

    /**
     * Transactions on the date.
     *
     * @param date
     *            date to search on
     * @return transactions on the date
     */
    private static final Specification<TransactionEntity> on(final Instant date) {
        // TODO: Should remove hour?
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    /**
     * Transactions on or after the date.
     *
     * @param date
     *            date to mark the lower limit
     * @return transactions on or after the date
     */
    private static final Specification<TransactionEntity> onOrAfter(final Instant date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    /**
     * Transactions on or before the date.
     *
     * @param date
     *            date to mark the lower limit
     * @return transactions on or before the date
     */
    private static final Specification<TransactionEntity> onOrBefore(final Instant date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    private TransactionSpecifications() {
        super();
    }

}
