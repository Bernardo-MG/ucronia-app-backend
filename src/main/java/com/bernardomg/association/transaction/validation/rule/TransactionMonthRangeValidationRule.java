
package com.bernardomg.association.transaction.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.validation.error.ValidationFailure;
import com.bernardomg.validation.error.ValidationRule;

public final class TransactionMonthRangeValidationRule implements ValidationRule<Transaction> {

    public TransactionMonthRangeValidationRule() {
        super();
    }

    @Override
    public Collection<ValidationFailure> test(final Transaction transaction) {
        final Collection<ValidationFailure> result;
        ValidationFailure                   error;

        result = new ArrayList<>();
        if ((transaction.getMonth() < 1) || (transaction.getMonth() > 12)) {
            // Start month out of range
            error = ValidationFailure.of("error.transaction.invalidMonth");
            result.add(error);
        }

        return result;
    }

    @Override
    public final String toString() {
        return this.getClass()
            .getName();
    }

}
