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

package com.bernardomg.association.member.adapter.inbound.jpa.specification;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MonthlyMemberBalanceEntity;

public final class MonthlyMemberBalanceSpecifications {

    public static Optional<Specification<MonthlyMemberBalanceEntity>> inRange(final Instant from, final Instant to) {
        final Optional<Specification<MonthlyMemberBalanceEntity>> spec;

        // TODO: use optionals, not nulls
        if ((from != null) && (to != null)) {
            spec = Optional.of(MonthlyMemberBalanceSpecifications.betweenIncluding(from, to));
        } else if (from != null) {
            spec = Optional.of(MonthlyMemberBalanceSpecifications.onOrAfter(from));
        } else if (to != null) {
            spec = Optional.of(MonthlyMemberBalanceSpecifications.onOrBefore(to));
        } else {
            spec = Optional.empty();
        }

        return spec;
    }

    private static Specification<MonthlyMemberBalanceEntity> betweenIncluding(final Instant start, final Instant end) {
        return (root, query, cb) -> cb.between(root.get("month"), start, end);
    }

    private static Specification<MonthlyMemberBalanceEntity> onOrAfter(final Instant date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("month"), date);
    }

    private static Specification<MonthlyMemberBalanceEntity> onOrBefore(final Instant date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("month"), date);
    }

    private MonthlyMemberBalanceSpecifications() {
        super();
    }

}
