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
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.MonthlyEvolutionEntity;

/**
 * Specifications for monthly evolutions.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class MonthlyEvolutionSpecifications {

    /**
     * Monthly evolutions before the month.
     *
     * @param month
     *            month to mark the lower limit
     * @return monthly evolutions before the month
     */
    public static Specification<MonthlyEvolutionEntity> before(final Instant month) {
        return (root, query, cb) -> cb.lessThan(root.get("month"), month);
    }

    /**
     * Monthly evolutions between both months, including them.
     *
     * @param start
     *            starting month
     * @param end
     *            final month
     * @return monthly evolutions between both months
     */
    public static Specification<MonthlyEvolutionEntity> betweenIncluding(final Instant start, final Instant end) {
        return (root, query, cb) -> cb.between(root.get("month"), start, end);
    }

    /**
     * Creates an specification from the dates.
     *
     * @param from
     *            starting date
     * @param to
     *            end date
     * @return specification for the request
     */
    public static Optional<Specification<MonthlyEvolutionEntity>> fromQuery(final Instant from, final Instant to) {
        final Optional<Specification<MonthlyEvolutionEntity>> spec;

        if ((from != null) && (to != null)) {
            spec = Optional.of(betweenIncluding(from, to));
        } else if (from != null) {
            spec = Optional.of(onOrAfter(from));
        } else if (to != null) {
            spec = Optional.of(onOrBefore(to));
        } else {
            spec = Optional.empty();
        }

        return spec;
    }

    /**
     * Monthly evolutions on or after the month.
     *
     * @param month
     *            month to mark the lower limit
     * @return monthly evolutions on or after the month
     */
    public static Specification<MonthlyEvolutionEntity> onOrAfter(final Instant month) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("month"), month);
    }

    /**
     * Monthly evolutions on or before the month.
     *
     * @param month
     *            month to mark the lower limit
     * @return monthly evolutions on or before the month
     */
    public static Specification<MonthlyEvolutionEntity> onOrBefore(final Instant month) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("month"), month);
    }

    private MonthlyEvolutionSpecifications() {
        super();
    }

}
