
package com.bernardomg.association.transaction.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.validation.ValidationRule;

public final class TransactionMonthRangeValidationRule implements ValidationRule<Transaction> {

    public TransactionMonthRangeValidationRule() {
        super();
    }

    @Override
    public Collection<Failure> test(final Transaction transaction) {
        final Collection<Failure> result;
        Failure                   error;

        result = new ArrayList<>();
        if ((transaction.getMonth() < 1) || (transaction.getMonth() > 12)) {
            // Start month out of range
            error = Failure.of("error.transaction.invalidMonth");
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
