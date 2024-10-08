
package com.bernardomg.association.transaction.test.domain.model.unit;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.transaction.adapter.outbound.rest.model.TransactionChange;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionChanges;

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
        final TransactionChange                           request;
        final Set<ConstraintViolation<TransactionChange>> errors;
        final ConstraintViolation<TransactionChange>      error;

        request = TransactionChanges.emptyDescription();

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
        final TransactionChange                           request;
        final Set<ConstraintViolation<TransactionChange>> errors;
        final ConstraintViolation<TransactionChange>      error;

        request = TransactionChanges.missingAmount();

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
        final TransactionChange                           request;
        final Set<ConstraintViolation<TransactionChange>> errors;
        final ConstraintViolation<TransactionChange>      error;

        request = TransactionChanges.missingDate();

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
        final TransactionChange                           request;
        final Set<ConstraintViolation<TransactionChange>> errors;
        final ConstraintViolation<TransactionChange>      error;

        request = TransactionChanges.missingDescription();

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
        final TransactionChange                           request;
        final Set<ConstraintViolation<TransactionChange>> errors;

        request = TransactionChanges.valid();

        errors = validator.validate(request);

        Assertions.assertThat(errors)
            .isEmpty();
    }

}
