
package com.bernardomg.association.funds.balance.persistence.repository;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.funds.balance.model.request.BalanceQuery;
import com.bernardomg.association.funds.balance.persistence.model.PersistentMonthlyBalance;

public final class MonthlyBalanceSpecifications {

    public static Specification<PersistentMonthlyBalance> after(final YearMonth date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("month"), date.atDay(1));
    }

    public static Specification<PersistentMonthlyBalance> before(final YearMonth date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("month"), date.atDay(1));
    }

    public static Specification<PersistentMonthlyBalance> between(final YearMonth start, final YearMonth end) {
        return (root, query, cb) -> cb.between(root.get("month"), start.atDay(1), end.atDay(1));
    }

    public static Optional<Specification<PersistentMonthlyBalance>> fromRequest(final BalanceQuery request) {
        final Optional<Specification<PersistentMonthlyBalance>> spec;

        if ((request.getStartDate() != null) && (request.getEndDate() != null)) {
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

    private MonthlyBalanceSpecifications() {
        super();
    }

}
