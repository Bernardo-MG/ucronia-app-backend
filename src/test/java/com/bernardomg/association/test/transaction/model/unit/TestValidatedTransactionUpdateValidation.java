
package com.bernardomg.association.test.transaction.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.transaction.util.model.TransactionsCreate;
import com.bernardomg.association.test.transaction.util.model.TransactionsUpdate;
import com.bernardomg.association.transaction.model.request.TransactionCreate;
import com.bernardomg.association.transaction.model.request.TransactionUpdate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@IntegrationTest
@DisplayName("ValidatedTransactionUpdate validation")
public class TestValidatedTransactionUpdateValidation {

    @Autowired
    private Validator validator;

    @Test
    @DisplayName("A DTO with an empty description is invalid")
    public void validate_emptyDescription() {
        final TransactionCreate                           request;
        final Set<ConstraintViolation<TransactionCreate>> errors;
        final ConstraintViolation<TransactionCreate>      error;

        request = TransactionsCreate.emptyDescription();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("description");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo("");
    }

    @Test
    @DisplayName("A DTO missing the amount is invalid")
    public void validate_missingAmount() {
        final TransactionUpdate                           request;
        final Set<ConstraintViolation<TransactionUpdate>> errors;
        final ConstraintViolation<TransactionUpdate>      error;

        request = TransactionsUpdate.missingAmount();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("amount");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A DTO missing the date is invalid")
    public void validate_missingDate() {
        final TransactionUpdate                           request;
        final Set<ConstraintViolation<TransactionUpdate>> errors;
        final ConstraintViolation<TransactionUpdate>      error;

        request = TransactionsUpdate.missingDate();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("date");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A DTO missing the description is invalid")
    public void validate_missingDescription() {
        final TransactionUpdate                           request;
        final Set<ConstraintViolation<TransactionUpdate>> errors;
        final ConstraintViolation<TransactionUpdate>      error;

        request = TransactionsUpdate.missingDescription();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath()
            .toString())
            .isEqualTo("description");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo(null);
    }

    @Test
    @DisplayName("A valid DTO is valid")
    public void validate_valid() {
        final TransactionUpdate                           request;
        final Set<ConstraintViolation<TransactionUpdate>> errors;

        request = TransactionsUpdate.inYear();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
