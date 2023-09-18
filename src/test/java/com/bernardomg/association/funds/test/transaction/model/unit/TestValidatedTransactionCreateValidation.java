
package com.bernardomg.association.funds.test.transaction.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.funds.test.transaction.util.model.TransactionsCreate;
import com.bernardomg.association.funds.transaction.model.request.TransactionCreate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@DisplayName("ValidatedTransactionCreate validation")
class TestValidatedTransactionCreateValidation {

    private final Validator validator = Validation.buildDefaultValidatorFactory()
        .getValidator();

    @Test
    @DisplayName("A DTO with an empty description is invalid")
    void validate_emptyDescription() {
        final TransactionCreate                           request;
        final Set<ConstraintViolation<TransactionCreate>> errors;
        final ConstraintViolation<TransactionCreate>      error;

        request = TransactionsCreate.emptyDescription();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("description");
        Assertions.assertThat(error.getInvalidValue())
            .isEqualTo("");
    }

    @Test
    @DisplayName("A DTO missing the amount is invalid")
    void validate_missingAmount() {
        final TransactionCreate                           request;
        final Set<ConstraintViolation<TransactionCreate>> errors;
        final ConstraintViolation<TransactionCreate>      error;

        request = TransactionsCreate.missingAmount();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("amount");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A DTO missing the date is invalid")
    void validate_missingDate() {
        final TransactionCreate                           request;
        final Set<ConstraintViolation<TransactionCreate>> errors;
        final ConstraintViolation<TransactionCreate>      error;

        request = TransactionsCreate.missingDate();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("date");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A DTO missing the description is invalid")
    void validate_missingDescription() {
        final TransactionCreate                           request;
        final Set<ConstraintViolation<TransactionCreate>> errors;
        final ConstraintViolation<TransactionCreate>      error;

        request = TransactionsCreate.missingDescription();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .hasSize(1);

        error = errors.iterator()
            .next();

        Assertions.assertThat(error.getPropertyPath())
            .hasToString("description");
        Assertions.assertThat(error.getInvalidValue())
            .isNull();
    }

    @Test
    @DisplayName("A valid DTO is valid")
    void validate_valid() {
        final TransactionCreate                           request;
        final Set<ConstraintViolation<TransactionCreate>> errors;

        request = TransactionsCreate.inYear();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
