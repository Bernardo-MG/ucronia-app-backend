
package com.bernardomg.association.fee.adapter.inbound.jpa.specification;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.domain.model.FeeQuery;

public final class FeeSpecifications {

    public static Optional<Specification<FeeEntity>> fromQuery(final FeeQuery query) {
        final Optional<Specification<FeeEntity>> spec;

        if (query.getDate() != null) {
            spec = Optional.of(on(query.getDate()));
        } else if ((query.getStartDate() != null) && (query.getEndDate() != null)) {
            spec = Optional.of(between(query.getStartDate(), query.getEndDate()));
        } else if (query.getStartDate() != null) {
            spec = Optional.of(after(query.getStartDate()));
        } else if (query.getEndDate() != null) {
            spec = Optional.of(before(query.getEndDate()));
        } else {
            spec = Optional.empty();
        }

        return spec;
    }

    private static Specification<FeeEntity> after(final YearMonth date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    private static Specification<FeeEntity> before(final YearMonth date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    private static Specification<FeeEntity> between(final YearMonth start, final YearMonth end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    private static Specification<FeeEntity> on(final YearMonth date) {
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    private FeeSpecifications() {
        super();
    }

}
