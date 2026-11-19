
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.ValidTransactionType;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionTypeRepository - findAll")
class ITTransactionTypeRepositoryFindAll {

    @Autowired
    private TransactionTypeRepository repository;

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindAll_NoData() {
        final Page<TransactionType> transactionTypes;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        transactionTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(transactionTypes.content())
            .as("When there is no data, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a transaction, then it is returned")
    @ValidTransactionType
    void testFindAll_Transaction() {
        final Page<TransactionType> transactionTypes;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        transactionTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(transactionTypes.content())
            .as("When there is a transaction, then it is returned")
            .containsExactly(TransactionTypes.valid());
    }

}
