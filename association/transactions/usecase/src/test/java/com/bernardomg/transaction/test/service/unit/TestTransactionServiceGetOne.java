
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

import com.bernardomg.association.transaction.domain.exception.MissingTransactionException;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionService - getOne")
class TestTransactionServiceGetOne {

    @InjectMocks
    private DefaultTransactionService service;

    @Mock
    private TransactionRepository     transactionRepository;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testFindOne() {
        final Optional<Transaction> read;
        final Transaction           transaction;

        // GIVEN
        transaction = Transactions.positive();
        given(transactionRepository.findOne(TransactionConstants.INDEX)).willReturn(Optional.of(transaction));

        // WHEN
        read = service.getOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThat(read)
            .as("transaction")
            .contains(transaction);
    }

    @Test
    @DisplayName("When there is no data, then an exception is thrown")
    void testFindOne_NoData() {
        final ThrowingCallable action;

        // GIVEN
        given(transactionRepository.findOne(TransactionConstants.INDEX)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(TransactionConstants.INDEX);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is no data, then an exception is thrown")
            .isInstanceOf(MissingTransactionException.class);
    }

}
