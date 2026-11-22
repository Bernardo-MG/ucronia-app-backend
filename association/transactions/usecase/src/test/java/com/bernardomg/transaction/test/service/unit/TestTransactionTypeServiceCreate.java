
package com.bernardomg.transaction.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionTypeService - create")
class TestTransactionTypeServiceCreate {

    @InjectMocks
    private DefaultTransactionTypeService service;

    @Mock
    private TransactionTypeRepository     transactionTypeRepository;

    @Test
    @DisplayName("When there is a valid transaction, then it is persisted")
    void testCreate_PersistedData() {
        final TransactionType transactionType;

        // GIVEN
        transactionType = TransactionTypes.valid();

        // WHEN
        service.create(transactionType);

        // THEN
        verify(transactionTypeRepository).save(transactionType);
    }

    @Test
    @DisplayName("When there is a valid transaction, then it is returned")
    void testCreate_ReturnedData() {
        final TransactionType transactionType;
        final TransactionType created;

        // GIVEN
        transactionType = TransactionTypes.valid();

        given(transactionTypeRepository.save(transactionType)).willReturn(transactionType);

        // WHEN
        created = service.create(transactionType);

        // THEN
        Assertions.assertThat(created)
            .as("transaction")
            .isEqualTo(transactionType);
    }

}
