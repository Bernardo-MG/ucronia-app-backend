
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mapping.PropertyReferenceException;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.MultipleTransactionTypes;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionTypes;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionTypeRepository - findAll")
@MultipleTransactionTypes
class ITTransactionTypeRepositoryFindAllSort {

    @Autowired
    private TransactionTypeRepository repository;

    @Test
    @DisplayName("When sorting in ascending order by description, then it returns the ordered data")
    void testFindAll_Description_Asc() {
        final Page<TransactionType> transactionTypes;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("description", Sorting.Direction.ASC)));

        // WHEN
        transactionTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(transactionTypes)
            .as("When sorting in ascending order by description, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(TransactionTypes.forNumber(1), TransactionTypes.forNumber(2),
                TransactionTypes.forNumber(3), TransactionTypes.forNumber(4), TransactionTypes.forNumber(5));
    }

    @Test
    @DisplayName("When sorting in descending order by description, then it returns the ordered data")
    void testFindAll_Description_Desc() {
        final Page<TransactionType> transactionTypes;
        final Pagination            pagination;
        final Sorting               sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("description", Sorting.Direction.DESC)));

        // WHEN
        transactionTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(transactionTypes)
            .as("When sorting in descending order by description, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(TransactionTypes.forNumber(5), TransactionTypes.forNumber(4),
                TransactionTypes.forNumber(3), TransactionTypes.forNumber(2), TransactionTypes.forNumber(1));
    }

    @Test
    @DisplayName("When ordering by a nonexistent field, then an error is generated")
    void testGetAll_NotExisting() {
        final Pagination       pagination;
        final Sorting          sorting;
        final ThrowingCallable action;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("abc", Sorting.Direction.ASC)));

        // WHEN
        action = () -> repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("Ordering by a not existing field generates an error")
            .isInstanceOf(PropertyReferenceException.class);
    }

}
