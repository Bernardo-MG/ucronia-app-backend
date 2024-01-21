
package com.bernardomg.association.fee.inbound.jpa.specification;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.inbound.jpa.model.MemberFeeEntity;

public final class MemberFeeSpecifications {

    public static Specification<MemberFeeEntity> after(final YearMonth date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<MemberFeeEntity> before(final YearMonth date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<MemberFeeEntity> between(final YearMonth start, final YearMonth end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    public static Optional<Specification<MemberFeeEntity>> fromQuery(final FeeQuery request) {
        final Optional<Specification<MemberFeeEntity>> spec;

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

    public static Specification<MemberFeeEntity> on(final YearMonth date) {
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    private MemberFeeSpecifications() {
        super();
    }

}
