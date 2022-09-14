
package com.bernardomg.association.test.transaction.unit.validation;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.transaction.model.DtoTransaction;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.validation.rule.TransactionMonthRangeValidationRule;
import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.validation.ValidationRule;

@DisplayName("Fee range validation rule")
public class TestTransactionMonthRangeValidationRule {

    private final ValidationRule<Transaction> validator = new TransactionMonthRangeValidationRule();

    public TestTransactionMonthRangeValidationRule() {
        super();
    }

    @Test
    @DisplayName("Rejects the end month when it is above limits")
    public final void testValidator_EndMonthAbove() throws Exception {
        final Collection<Failure> failures;
        final DtoTransaction      transaction;
        final FieldFailure        failure;

        transaction = new DtoTransaction();
        transaction.setDescription("Transaction");
        transaction.setQuantity(1l);
        transaction.setDay(2);
        transaction.setMonth(13);
        transaction.setYear(4);

        failures = validator.test(transaction);

        Assertions.assertEquals(1, failures.size());

        failure = (FieldFailure) failures.iterator()
            .next();
        Assertions.assertEquals("error.transaction.invalidMonth", failure.getMessage());
        Assertions.assertEquals("transaction", failure.getObject());
        Assertions.assertEquals("month", failure.getField());
        Assertions.assertEquals(13, failure.getValue());
    }

    @Test
    @DisplayName("Rejects the end month when it is below limits")
    public final void testValidator_EndMonthBelow() throws Exception {
        final Collection<Failure> error;
        final DtoTransaction      transaction;
        final FieldFailure        failure;

        transaction = new DtoTransaction();
        transaction.setDescription("Transaction");
        transaction.setQuantity(1l);
        transaction.setDay(2);
        transaction.setMonth(0);
        transaction.setYear(4);

        error = validator.test(transaction);

        Assertions.assertEquals(1, error.size());

        failure = (FieldFailure) error.iterator()
            .next();
        Assertions.assertEquals("error.transaction.invalidMonth", failure.getMessage());
        Assertions.assertEquals("transaction", failure.getObject());
        Assertions.assertEquals("month", failure.getField());
        Assertions.assertEquals(0, failure.getValue());
    }

    @Test
    @DisplayName("Accepts a valid month")
    public final void testValidator_Valid() throws Exception {
        final Collection<Failure> error;
        final DtoTransaction      transaction;

        transaction = new DtoTransaction();
        transaction.setDescription("Transaction");
        transaction.setQuantity(1l);
        transaction.setDay(2);
        transaction.setMonth(3);
        transaction.setYear(4);

        error = validator.test(transaction);

        Assertions.assertEquals(0, error.size());
    }

}
