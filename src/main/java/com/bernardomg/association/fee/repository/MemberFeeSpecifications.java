
package com.bernardomg.association.fee.repository;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.fee.model.FeeRequest;
import com.bernardomg.association.fee.model.PersistentMemberFee;

public final class MemberFeeSpecifications {

    public static Specification<PersistentMemberFee> after(final Calendar date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<PersistentMemberFee> before(final Calendar date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<PersistentMemberFee> between(final Calendar start, final Calendar end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    public static Optional<Specification<PersistentMemberFee>> fromRequest(final FeeRequest request) {
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

    public static Specification<PersistentMemberFee> on(final Calendar date) {
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    private MemberFeeSpecifications() {
        super();
    }

}
