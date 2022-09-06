
package com.bernardomg.association.transaction.validation;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.validation.rule.TransactionMonthRangeValidationRule;
import com.bernardomg.validation.RuleValidator;
import com.bernardomg.validation.Validator;

@Component
public final class TransactionValidator implements Validator<Transaction> {

    private final Validator<Transaction> validator;

    public TransactionValidator() {
        super();

        validator = new RuleValidator<>(Arrays.asList(new TransactionMonthRangeValidationRule()));
    }

    @Override
    public final void validate(final Transaction period) {
        validator.validate(period);
    }

}
