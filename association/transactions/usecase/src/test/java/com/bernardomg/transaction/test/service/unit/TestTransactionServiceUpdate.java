
package com.bernardomg.transaction.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.transaction.domain.exception.MissingTransactionException;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionService - update")
class TestTransactionServiceUpdate {

    @InjectMocks
    private DefaultTransactionService service;

    @Mock
    private TransactionRepository     transactionRepository;

    @Test
    @DisplayName("When the transaction with a future date is provided, then it throws an exception")
    void testUpdate_Future() {
        final Transaction      transaction;
        final ThrowingCallable action;
        final FieldFailure     failure;

        // GIVEN
        transaction = Transactions.future();

        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        action = () -> service.update(transaction);

        // THEN
        failure = new FieldFailure("invalid", "date", "date.invalid", TransactionConstants.DATE_FUTURE);

        ValidationAssertions.assertThatFieldFails(action, failure);
    }

    @Test
    @DisplayName("When there is a not existing transaction, then an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Transaction      transaction;
        final ThrowingCallable action;

        // GIVEN
        transaction = Transactions.positive();

        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(false);

        // WHEN
        action = () -> service.update(transaction);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing transaction, then an exception is thrown")
            .isInstanceOf(MissingTransactionException.class);
    }

    @Test
    @DisplayName("When a transaction with padded data is updated, then it is normalized before persistence")
    void testUpdate_Padded_PersistedData() {
        final Transaction transaction;

        // GIVEN
        transaction = Transactions.padded();

        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        service.update(transaction);

        // THEN
        verify(transactionRepository).save(Transactions.positive());
    }

    @Test
    @DisplayName("When a valid transaction is updated, then it is persisted")
    void testUpdate_PersistedData() {
        final Transaction transaction;

        // GIVEN
        transaction = Transactions.positive();

        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        service.update(transaction);

        // THEN
        verify(transactionRepository).save(transaction);
    }

    @Test
    @DisplayName("When a valid transaction is updated, then the updated transaction is returned")
    void testUpdate_ReturnedData() {
        final Transaction transaction;
        final Transaction updated;

        // GIVEN
        transaction = Transactions.positive();

        given(transactionRepository.save(transaction)).willReturn(transaction);
        given(transactionRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        updated = service.update(transaction);

        // THEN
        Assertions.assertThat(updated)
            .as("transaction")
            .isEqualTo(transaction);
    }

}
