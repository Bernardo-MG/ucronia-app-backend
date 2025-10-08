
package com.bernardomg.association.fee.adapter.inbound.jpa.specification;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.domain.model.FeeQuery;

public final class FeeSpecifications {

    public static Optional<Specification<FeeEntity>> fromQuery(final FeeQuery query) {
        final Optional<Specification<FeeEntity>> spec;

        if (query.date() != null) {
            spec = Optional.of(on(query.date()));
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
