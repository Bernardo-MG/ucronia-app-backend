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

package com.bernardomg.association.fee.adapter.inbound.jpa.specification;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.domain.model.FeeQuery;

public final class FeeSpecifications {

    public static Optional<Specification<FeeEntity>> fromQuery(final FeeQuery query) {
        final Optional<Specification<FeeEntity>> spec;

        if (query.month() != null) {
            spec = Optional.of(on(query.month()));
        } else if ((query.from() != null) && (query.to() != null)) {
            spec = Optional.of(between(query.from(), query.to()));
        } else if (query.from() != null) {
            spec = Optional.of(after(query.from()));
        } else if (query.to() != null) {
            spec = Optional.of(before(query.to()));
        } else {
            spec = Optional.empty();
        }

        return spec;
    }

    private static Specification<FeeEntity> after(final Instant date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    private static Specification<FeeEntity> before(final Instant date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    private static Specification<FeeEntity> between(final Instant start, final Instant end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    private static Specification<FeeEntity> on(final Instant date) {
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    private FeeSpecifications() {
        super();
    }

}
