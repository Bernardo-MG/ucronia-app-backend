
package com.bernardomg.association.membership.balance.persistence.repository;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.membership.balance.model.request.MemberBalanceQuery;
import com.bernardomg.association.membership.balance.persistence.model.PersistentMonthlyMemberBalance;

public final class MonthlyMemberBalanceSpecifications {

    public static Specification<PersistentMonthlyMemberBalance> afterOrEqual(final YearMonth date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("month"), date.atDay(1));
    }

    public static Specification<PersistentMonthlyMemberBalance> before(final YearMonth date) {
        return (root, query, cb) -> cb.lessThan(root.get("month"), date.atDay(1));
    }

    public static Specification<PersistentMonthlyMemberBalance> beforeOrEqual(final YearMonth date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("month"), date.atDay(1));
    }

    public static Specification<PersistentMonthlyMemberBalance> betweenIncluding(final YearMonth start,
            final YearMonth end) {
        return (root, query, cb) -> cb.between(root.get("month"), start.atDay(1), end.atDay(1));
    }

    public static Optional<Specification<PersistentMonthlyMemberBalance>>
            fromRequest(final MemberBalanceQuery request) {
        final Optional<Specification<PersistentMonthlyMemberBalance>> spec;

        if ((request.getStartDate() != null) && (request.getEndDate() != null)) {
            spec = Optional.of(betweenIncluding(request.getStartDate(), request.getEndDate()));
        } else if (request.getStartDate() != null) {
            spec = Optional.of(afterOrEqual(request.getStartDate()));
        } else if (request.getEndDate() != null) {
            spec = Optional.of(beforeOrEqual(request.getEndDate()));
        } else {
            spec = Optional.empty();
        }

        return spec;
    }

    private MonthlyMemberBalanceSpecifications() {
        super();
    }

}
