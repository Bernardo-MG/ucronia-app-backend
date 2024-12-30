
package com.bernardomg.association.fee.adapter.inbound.jpa.specification;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.domain.model.FeeQuery;

public final class FeeSpecifications {

    public static Specification<FeeEntity> after(final YearMonth date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<FeeEntity> before(final YearMonth date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<FeeEntity> between(final YearMonth start, final YearMonth end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    public static Optional<Specification<FeeEntity>> fromQuery(final FeeQuery request) {
        final Optional<Specification<FeeEntity>> spec;

        if (request.getDate() != null) {
            spec = Optional.of(on(request.getDate()));
        } else if ((request.getStartDate() != null) && (request.getEndDate() != null)) {
            spec = Optional.of(between(request.getStartDate(), request.getEndDate()));
        } else if (request.getStartDate() != null) {
            spec = Optional.of(after(request.getStartDate()));
        } else if (request.getEndDate() != null) {
            spec = Optional.of(before(request.getEndDate()));
        } else {
            spec = Optional.empty();
        }

        return spec;
    }

    public static Specification<FeeEntity> on(final YearMonth date) {
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    private FeeSpecifications() {
        super();
    }

}
