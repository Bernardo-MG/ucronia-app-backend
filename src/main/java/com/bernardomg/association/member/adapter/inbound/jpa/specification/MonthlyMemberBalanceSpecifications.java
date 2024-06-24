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

package com.bernardomg.association.member.adapter.inbound.jpa.specification;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MonthlyMemberBalanceEntity;

public final class MonthlyMemberBalanceSpecifications {

    public static Specification<MonthlyMemberBalanceEntity> betweenIncluding(final YearMonth start,
            final YearMonth end) {
        return (root, query, cb) -> cb.between(root.get("month"), start.atDay(1), end.atDay(1));
    }

    public static Optional<Specification<MonthlyMemberBalanceEntity>> inRange(final YearMonth startDate,
            final YearMonth endDate) {
        final Optional<Specification<MonthlyMemberBalanceEntity>> spec;

        // TODO: use optionals, not nulls
        if ((startDate != null) && (endDate != null)) {
            spec = Optional.of(MonthlyMemberBalanceSpecifications.betweenIncluding(startDate, endDate));
        } else if (startDate != null) {
            spec = Optional.of(MonthlyMemberBalanceSpecifications.onOrAfter(startDate));
        } else if (endDate != null) {
            spec = Optional.of(MonthlyMemberBalanceSpecifications.onOrBefore(endDate));
        } else {
            spec = Optional.empty();
        }

        return spec;
    }

    public static Specification<MonthlyMemberBalanceEntity> onOrAfter(final YearMonth date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("month"), date.atDay(1));
    }

    public static Specification<MonthlyMemberBalanceEntity> onOrBefore(final YearMonth date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("month"), date.atEndOfMonth());
    }

    private MonthlyMemberBalanceSpecifications() {
        super();
    }

}
