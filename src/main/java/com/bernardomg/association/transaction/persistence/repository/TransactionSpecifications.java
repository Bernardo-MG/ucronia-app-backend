
package com.bernardomg.association.transaction.persistence.repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.model.request.TransactionQuery;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;

public final class TransactionSpecifications {

    public static final Specification<PersistentTransaction> after(final LocalDate date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    public static final Specification<PersistentTransaction> before(final LocalDate date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    public static final Specification<PersistentTransaction> between(final LocalDate start, final LocalDate end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    public static final Specification<PersistentTransaction> fromDate(final YearMonth date) {
        final LocalDate startDate;
        final LocalDate endDate;

        startDate = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
        endDate = LocalDate.of(date.getYear(), date.getMonthValue(), date.getMonth()
            .length(date.isLeapYear()));

        return between(startDate, endDate);
    }

    public static final Optional<Specification<PersistentTransaction>> fromRequest(final TransactionQuery request) {
        final Optional<Specification<PersistentTransaction>> spec;

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

    public static final Specification<PersistentTransaction> on(final LocalDate date) {
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    private TransactionSpecifications() {
        super();
    }

}
