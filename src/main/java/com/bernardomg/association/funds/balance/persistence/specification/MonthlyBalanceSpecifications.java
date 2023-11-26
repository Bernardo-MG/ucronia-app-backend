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

package com.bernardomg.association.funds.balance.persistence.specification;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.funds.balance.model.request.BalanceQuery;
import com.bernardomg.association.funds.balance.persistence.model.MonthlyBalanceEntity;

/**
 * Specifications for monthly balances.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class MonthlyBalanceSpecifications {

    /**
     * Monthly balances before the month.
     *
     * @param month
     *            month to mark the lower limit
     * @return monthly balances before the month
     */
    public static Specification<MonthlyBalanceEntity> before(final YearMonth month) {
        return (root, query, cb) -> cb.lessThan(root.get("month"), month.atDay(1));
    }

    /**
     * Monthly balances between both months, including them.
     *
     * @param start
     *            starting month
     * @param end
     *            final month
     * @return monthly balances between both months
     */
    public static Specification<MonthlyBalanceEntity> betweenIncluding(final YearMonth start, final YearMonth end) {
        return (root, query, cb) -> cb.between(root.get("month"), start.atDay(1), end.atDay(1));
    }

    /**
     * Creates an specification from the request.
     *
     * @param request
     *            request to create a specification from
     * @return specification for the request
     */
    public static Optional<Specification<MonthlyBalanceEntity>> fromQuery(final BalanceQuery request) {
        final Optional<Specification<MonthlyBalanceEntity>> spec;

        if ((request.getStartDate() != null) && (request.getEndDate() != null)) {
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
     * Monthly balances on or after the month.
     *
     * @param month
     *            month to mark the lower limit
     * @return monthly balances on or after the month
     */
    public static Specification<MonthlyBalanceEntity> onOrAfter(final YearMonth month) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("month"), month.atDay(1));
    }

    /**
     * Monthly balances on or before the month.
     *
     * @param month
     *            month to mark the lower limit
     * @return monthly balances on or before the month
     */
    public static Specification<MonthlyBalanceEntity> onOrBefore(final YearMonth month) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("month"), month.atDay(1));
    }

    private MonthlyBalanceSpecifications() {
        super();
    }

}
