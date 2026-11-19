
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.MultipleTransactionTypes;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.pagination.AbstractPaginationIT;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionTypeRepository - findAll")
@MultipleTransactionTypes
class ITTransactionTypeRepositoryFindAllPagination extends AbstractPaginationIT<TransactionType> {

    @Autowired
    private TransactionTypeRepository repository;

    public ITTransactionTypeRepositoryFindAllPagination() {
        super(5);
    }

    @Override
    protected final Page<TransactionType> read(final Pagination pagination, final Sorting sorting) {
        return repository.findAll(pagination, sorting);
    }

    @Test
    @DisplayName("When requesting pagination for the first page, then it returns the first page")
    void testFindAll_Page1() {
        final Page<TransactionType> transactions;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(1, 1);
        sorting = new Sorting(List.of(new Sorting.Property("description", Sorting.Direction.ASC)));

        // WHEN
        transactions = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When requesting pagination for the first page, then it returns the first page")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(TransactionTypes.forNumber(1));
    }

    @Test
    @DisplayName("When requesting pagination for the second page, then it returns the second page")
    void testFindAll_Page2() {
        final Page<TransactionType> transactions;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(2, 1);
        sorting = new Sorting(List.of(new Sorting.Property("description", Sorting.Direction.ASC)));

        // WHEN
        transactions = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When requesting pagination for the second page, then it returns the second page")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(TransactionTypes.forNumber(2));
    }

}
