
package com.bernardomg.transaction.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
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
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionTypeService - getAll")
class TestTransactionTypeServiceGetAll {

    @InjectMocks
    private DefaultTransactionTypeService service;

    @Mock
    private TransactionTypeRepository     transactionTypeRepository;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetAll() {
        final Page<TransactionType> transactionTypes;
        final Page<TransactionType> existing;
        final TransactionType       transactionType;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        transactionType = TransactionTypes.valid();
        existing = new Page<>(List.of(transactionType), 0, 0, 0, 0, 0, false, false, sorting);
        given(transactionTypeRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        transactionTypes = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(transactionTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("transactions")
            .containsExactly(transactionType);
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testGetAll_NoData() {
        final Page<TransactionType> transactionTypes;
        final Page<TransactionType> existing;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(transactionTypeRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        transactionTypes = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(transactionTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("transactions")
            .isEmpty();
    }

}
