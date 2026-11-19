
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

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionService - create")
class TestTransactionServiceCreate {

    @InjectMocks
    private DefaultTransactionService service;

    @Mock
    private TransactionRepository     transactionRepository;

    @Test
    @DisplayName("When the transaction created in the future is provided, then it throws an exception")
    void testCreate_Future() {
        final ThrowingCallable action;
        final FieldFailure     failure;

        // WHEN
        action = () -> service.create(Transactions.future());

        // THEN
        failure = new FieldFailure("invalid", "date", "date.invalid", TransactionConstants.DATE_FUTURE);

        ValidationAssertions.assertThatFieldFails(action, failure);
    }

    @Test
    @DisplayName("When there is a transaction with padded name, then the member is persisted")
    void testCreate_Padded_PersistedData() {
        final Transaction transaction;

        // GIVEN
        transaction = Transactions.padded();

        // WHEN
        service.create(transaction);

        // THEN
        verify(transactionRepository).save(transaction);
    }

    @Test
    @DisplayName("When there is a valid transaction, then it is persisted")
    void testCreate_PersistedData() {
        final Transaction transaction;

        // GIVEN
        transaction = Transactions.positive();

        // WHEN
        service.create(transaction);

        // THEN
        verify(transactionRepository).save(transaction);
    }

    @Test
    @DisplayName("When there is a valid transaction, then it is returned")
    void testCreate_ReturnedData() {
        final Transaction transaction;
        final Transaction created;

        // GIVEN
        transaction = Transactions.positive();

        given(transactionRepository.save(transaction)).willReturn(transaction);

        // WHEN
        created = service.create(transaction);

        // THEN
        Assertions.assertThat(created)
            .as("transaction")
            .isEqualTo(transaction);
    }

}
