
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
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionTypeService - update")
class TestTransactionTypeServiceUpdate {

    @InjectMocks
    private DefaultTransactionTypeService service;

    @Mock
    private TransactionTypeRepository     transactionTypeRepository;

    @Test
    @DisplayName("When there is a not existing transaction type, then an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final TransactionType  transactionType;
        final ThrowingCallable action;

        // GIVEN
        transactionType = TransactionTypes.valid();

        given(transactionTypeRepository.exists(TransactionConstants.INDEX)).willReturn(false);

        // WHEN
        action = () -> service.update(transactionType);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing transaction type, then an exception is thrown")
            .isInstanceOf(MissingTransactionException.class);
    }

    @Test
    @DisplayName("When a valid transaction type is updated, then it is persisted")
    void testUpdate_PersistedData() {
        final TransactionType transactionType;

        // GIVEN
        transactionType = TransactionTypes.valid();

        given(transactionTypeRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        service.update(transactionType);

        // THEN
        verify(transactionTypeRepository).save(transactionType);
    }

    @Test
    @DisplayName("When a valid transaction type is updated, then the updated type is returned")
    void testUpdate_ReturnedData() {
        final TransactionType transactionType;
        final TransactionType updated;

        // GIVEN
        transactionType = TransactionTypes.valid();

        given(transactionTypeRepository.save(transactionType)).willReturn(transactionType);
        given(transactionTypeRepository.exists(TransactionConstants.INDEX)).willReturn(true);

        // WHEN
        updated = service.update(transactionType);

        // THEN
        Assertions.assertThat(updated)
            .as("transaction")
            .isEqualTo(transactionType);
    }

}
