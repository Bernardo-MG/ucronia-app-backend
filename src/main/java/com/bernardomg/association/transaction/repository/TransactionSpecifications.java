
package com.bernardomg.association.transaction.repository;

import java.util.Calendar;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.model.PersistentTransaction;

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

    public static Specification<PersistentTransaction> on(final Calendar date) {
        return (root, query, cb) -> cb.equal(root.get("date"), date);
    }

    private TransactionSpecifications() {
        super();
    }

}
