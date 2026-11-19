
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

import com.bernardomg.association.transaction.domain.exception.MissingTransactionTypeException;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionTypeService - delete")
class TestTransactionTypeServiceDelete {

    @InjectMocks
    private DefaultTransactionTypeService service;

    @Mock
    private TransactionTypeRepository     transactionTypeRepository;

    @Test
    @DisplayName("When the transaction doesn't exist, then an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(transactionTypeRepository.findOne(TransactionConstants.INDEX)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the transaction doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingTransactionTypeException.class);
    }

    @Test
    @DisplayName("When deleting, then the repository is called")
    void testDelete_RemovesEntity() {
        // GIVEN
        given(transactionTypeRepository.findOne(TransactionConstants.INDEX))
            .willReturn(Optional.of(TransactionTypes.valid()));

        // WHEN
        service.delete(TransactionConstants.INDEX);

        // THEN
        verify(transactionTypeRepository).delete(TransactionConstants.INDEX);
    }

}
