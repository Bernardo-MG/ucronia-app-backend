
package com.bernardomg.association.membership.fee.persistence.repository;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.membership.fee.model.request.FeeQuery;
import com.bernardomg.association.membership.fee.persistence.model.PersistentMemberFee;

public final class MemberFeeSpecifications {

    public static Specification<PersistentMemberFee> active(final boolean active) {
        return (root, query, cb) -> cb.equal(root.get("active"), active);
    }

    public static Specification<PersistentMemberFee> after(final YearMonth date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<PersistentMemberFee> before(final YearMonth date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<PersistentMemberFee> between(final YearMonth start, final YearMonth end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    public static Optional<Specification<PersistentMemberFee>> fromQuery(final FeeQuery request) {
        final Optional<Specification<PersistentMemberFee>> spec;

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

    public static Specification<PersistentMemberFee> on(final YearMonth date) {
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    private MemberFeeSpecifications() {
        super();
    }

}
