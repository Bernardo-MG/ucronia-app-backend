
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
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
import com.bernardomg.association.transaction.domain.filter.TransactionFilter;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionsFilters;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionRepository - findAll")
@MultipleTransactionsSameMonth
class ITTransactionRepositoryFindAllWithFilterSort {

    @Autowired
    private TransactionRepository repository;

    @Test
    @DisplayName("When sorting in ascending order by date, then it returns the ordered data")
    void testFindAll_Date_Asc() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        transactionFilter = TransactionsFilters.empty();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When sorting in ascending order by date, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(1, Month.JANUARY),
                Transactions.forIndexAndMonth(2, Month.JANUARY), Transactions.forIndexAndMonth(3, Month.JANUARY),
                Transactions.forIndexAndMonth(4, Month.JANUARY), Transactions.forIndexAndMonth(5, Month.JANUARY));
    }

    @Test
    @DisplayName("When sorting in descending order by date, then it returns the ordered data")
    void testFindAll_Date_Desc() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.DESC)));

        transactionFilter = TransactionsFilters.empty();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When sorting in descending order by date, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(5, Month.JANUARY),
                Transactions.forIndexAndMonth(4, Month.JANUARY), Transactions.forIndexAndMonth(3, Month.JANUARY),
                Transactions.forIndexAndMonth(2, Month.JANUARY), Transactions.forIndexAndMonth(1, Month.JANUARY));
    }

    @Test
    @DisplayName("When sorting in ascending order by description, then it returns the ordered data")
    void testFindAll_Description_Asc() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("description", Sorting.Direction.ASC)));

        transactionFilter = TransactionsFilters.empty();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When sorting in ascending order by description, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(1, Month.JANUARY),
                Transactions.forIndexAndMonth(2, Month.JANUARY), Transactions.forIndexAndMonth(3, Month.JANUARY),
                Transactions.forIndexAndMonth(4, Month.JANUARY), Transactions.forIndexAndMonth(5, Month.JANUARY));
    }

    @Test
    @DisplayName("When sorting in descending order by description, then it returns the ordered data")
    void testFindAll_Description_Desc() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("description", Sorting.Direction.DESC)));

        transactionFilter = TransactionsFilters.empty();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When sorting in descending order by description, then it returns the ordered data")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(5, Month.JANUARY),
                Transactions.forIndexAndMonth(4, Month.JANUARY), Transactions.forIndexAndMonth(3, Month.JANUARY),
                Transactions.forIndexAndMonth(2, Month.JANUARY), Transactions.forIndexAndMonth(1, Month.JANUARY));
    }

    @Test
    @DisplayName("When ordering by a nonexistent field, then an error is generated")
    void testGetAll_NotExisting() {
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;
        final ThrowingCallable  action;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("abc", Sorting.Direction.ASC)));

        transactionFilter = TransactionsFilters.empty();

        // WHEN
        action = () -> repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("Ordering by a not existing field generates an error")
            .isInstanceOf(PropertyReferenceException.class);
    }

}
