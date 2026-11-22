
package com.bernardomg.transaction.test.service.unit;

import static org.mockito.BDDMockito.given;

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
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionTypeService - getOne")
class TestTransactionTypeServiceGetOne {

    @InjectMocks
    private DefaultTransactionTypeService service;

    @Mock
    private TransactionTypeRepository     transactionTypeRepository;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testFindOne() {
        final Optional<TransactionType> read;
        final TransactionType           transactionType;

        // GIVEN
        transactionType = TransactionTypes.valid();
        given(transactionTypeRepository.findOne(TransactionConstants.INDEX)).willReturn(Optional.of(transactionType));

        // WHEN
        read = service.getOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(read)
            .as("transaction")
            .contains(transactionType);
    }

    @Test
    @DisplayName("When there is no data, then an exception is thrown")
    void testFindOne_NoData() {
        final ThrowingCallable action;

        // GIVEN
        given(transactionTypeRepository.findOne(TransactionConstants.INDEX)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is no data, then an exception is thrown")
            .isInstanceOf(MissingTransactionTypeException.class);
    }

}
