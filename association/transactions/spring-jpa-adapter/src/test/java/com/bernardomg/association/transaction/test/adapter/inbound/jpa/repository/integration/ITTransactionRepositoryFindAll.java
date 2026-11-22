
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionRepository - findAll")
class ITTransactionRepositoryFindAll {

    @Autowired
    private TransactionRepository repository;

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindAll_NoData() {
        final Iterable<Transaction> transactions;
        final Sorting               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        transactions = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When there is no data, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a transaction, then it is returned")
    @PositiveTransaction
    void testFindAll_Transaction() {
        final Iterable<Transaction> transactions;
        final Sorting               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        transactions = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When there is a transaction, then it is returned")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.positive());
    }

}
