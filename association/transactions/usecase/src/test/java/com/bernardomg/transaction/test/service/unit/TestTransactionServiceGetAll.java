
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

import com.bernardomg.association.transaction.domain.filter.TransactionFilter;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionsFilters;
import com.bernardomg.association.transaction.usecase.service.DefaultTransactionService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultTransactionService - getAll")
class TestTransactionServiceGetAll {

    @InjectMocks
    private DefaultTransactionService service;

    @Mock
    private TransactionRepository     transactionRepository;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetAll() {
        final Page<Transaction> transactions;
        final Page<Transaction> existing;
        final Transaction       transaction;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.empty();

        transaction = Transactions.positive();
        existing = new Page<>(List.of(transaction), 0, 0, 0, 0, 0, false, false, sorting);
        given(transactionRepository.findAll(transactionFilter, pagination, sorting)).willReturn(existing);

        // WHEN
        transactions = service.getAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("transactions")
            .containsExactly(transaction);
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testGetAll_NoData() {
        final Page<Transaction> transactions;
        final Page<Transaction> existing;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.empty();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(transactionRepository.findAll(transactionFilter, pagination, sorting)).willReturn(existing);

        // WHEN
        transactions = service.getAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("transactions")
            .isEmpty();
    }

}
