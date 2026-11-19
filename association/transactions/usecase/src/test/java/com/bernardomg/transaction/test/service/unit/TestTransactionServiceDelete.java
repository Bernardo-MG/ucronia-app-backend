
package com.bernardomg.transaction.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.transaction.domain.exception.MissingTransactionException;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionService - delete")
class TestTransactionServiceDelete {

    @InjectMocks
    private DefaultTransactionService service;

    @Mock
    private TransactionRepository     transactionRepository;

    @Test
    @DisplayName("When the transaction doesn't exist, then an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(transactionRepository.findOne(TransactionConstants.INDEX)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the transaction doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingTransactionException.class);
    }

    @Test
    @DisplayName("When deleting, then the repository is called")
    void testDelete_RemovesEntity() {
        // GIVEN
        given(transactionRepository.findOne(TransactionConstants.INDEX))
            .willReturn(Optional.of(Transactions.positive()));

        // WHEN
        service.delete(TransactionConstants.INDEX);

        // THEN
        verify(transactionRepository).delete(TransactionConstants.INDEX);
    }

}
