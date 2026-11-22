
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.filter.TransactionFilter;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.test.configuration.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionsFilters;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionRepository - findAll")
class ITTransactionRepositoryFindAllWithFilterFilter {

    @Autowired
    private TransactionRepository repository;

    @Test
    @DisplayName("When there is a filter applied to the start date, then the returned data is filtered")
    @MultipleTransactionsSameMonth
    void testFindAll_AfterDate() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.from(TransactionConstants.START_DATE.plus(1L, ChronoUnit.DAYS));

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When there is a filter applied to the start date, then the returned data is filtered")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(2, Month.JANUARY),
                Transactions.forIndexAndMonth(3, Month.JANUARY), Transactions.forIndexAndMonth(4, Month.JANUARY),
                Transactions.forIndexAndMonth(5, Month.JANUARY));
    }

    @Test
    @DisplayName("When there is a filter applied to the end date, then the returned data is filtered")
    @MultipleTransactionsSameMonth
    void testFindAll_BeforeDate() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.to(TransactionConstants.START_DATE.plus(1L, ChronoUnit.DAYS));

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When there is a filter applied to the end date, then the returned data is filtered")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(1, Month.JANUARY),
                Transactions.forIndexAndMonth(2, Month.JANUARY));
    }

    @Test
    @DisplayName("When there is a filter applied to the description, then the returned data is filtered")
    @MultipleTransactionsSameMonth
    void testFindAll_Description() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.description();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When there is a filter applied to the description, then the returned data is filtered")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(1, Month.JANUARY));
    }

    @Test
    @DisplayName("When there is a filter applied to the date, then the returned data is filtered")
    @MultipleTransactionsSameMonth
    void testFindAll_InDate() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.date(TransactionConstants.START_DATE.plus(1L, ChronoUnit.DAYS));

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When there is a filter applied to the date, then the returned data is filtered")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(2, Month.JANUARY));
    }

    @Test
    @DisplayName("When there is a filter applied to the date for the first day of the year, then the returned data is filtered")
    @FullTransactionYear
    void testFindAll_InDate_FirstDay() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.date(LocalDate.of(2020, Month.JANUARY, 1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When there is a filter applied to the date for the first day of the year, then the returned data is filtered")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(1, Month.JANUARY));
    }

    @Test
    @DisplayName("When there is a filter applied to the date for the last day of the year, then the returned data is filtered")
    @FullTransactionYear
    void testFindAll_InDate_LastDay() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // TODO: This is not the last day of the year
        transactionFilter = TransactionsFilters.date(LocalDate.of(2020, Month.DECEMBER, 1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When there is a filter applied to the date for the last day of the year, then the returned data is filtered")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndex(12, Month.DECEMBER));
    }

    @Test
    @DisplayName("When there is a filter applied to a partial description, then the returned data is filtered")
    @MultipleTransactionsSameMonth
    void testFindAll_PartialDescription() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.description();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When there is a filter applied to a partial description, then the returned data is filtered")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(1, Month.JANUARY));
    }

}
