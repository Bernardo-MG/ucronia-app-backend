
package com.bernardomg.association.transaction.persistence.repository;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.model.request.TransactionQuery;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;

public final class TransactionSpecifications {

    public static Specification<PersistentTransaction> after(final Calendar date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<PersistentTransaction> before(final Calendar date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), date);
    }

    public static Specification<PersistentTransaction> between(final Calendar start, final Calendar end) {
        return (root, query, cb) -> cb.between(root.get("date"), start, end);
    }

    public static Optional<Specification<PersistentTransaction>> fromRequest(final TransactionQuery request) {
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

    public static Specification<PersistentTransaction> on(final Calendar date) {
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    private TransactionSpecifications() {
        super();
    }

}
