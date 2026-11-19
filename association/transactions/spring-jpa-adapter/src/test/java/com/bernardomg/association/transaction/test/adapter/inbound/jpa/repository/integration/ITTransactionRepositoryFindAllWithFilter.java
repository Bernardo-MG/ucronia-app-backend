
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.domain.filter.TransactionFilter;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.test.configuration.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionsFilters;
import com.bernardomg.association.transaction.test.factory.TransactionEntities;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.configuration.argument.AroundZeroArgumentsProvider;
import com.bernardomg.test.configuration.argument.DecimalArgumentsProvider;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionRepository - findAll")
class ITTransactionRepositoryFindAllWithFilter {

    @Autowired
    private TransactionRepository       repository;

    @Autowired
    private TransactionSpringRepository springRepository;

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("When a transaction has a value around zero, then it is returned")
    void testFindAll_AroundZero(final Float amount) {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        springRepository.save(TransactionEntities.forAmount(amount));

        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.empty();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("With a transaction with value around zero, it returns it")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Transactions.forAmount(amount));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("When a transaction has a decimal value, then it is returned")
    void testFindAll_Decimal(final Float amount) {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        springRepository.save(TransactionEntities.forAmount(amount));

        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.empty();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("With a decimal transaction, it returns it")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Transactions.forAmount(amount));
    }

    @Test
    @DisplayName("When there is a full year, then it returns all the transactions")
    @FullTransactionYear
    void testFindAll_FullYear() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.empty();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When there is a full year, then it returns all the transactions")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactlyInAnyOrder(Transactions.forIndex(1, Month.JANUARY),
                Transactions.forIndex(2, Month.FEBRUARY), Transactions.forIndex(3, Month.MARCH),
                Transactions.forIndex(4, Month.APRIL), Transactions.forIndex(5, Month.MAY),
                Transactions.forIndex(6, Month.JUNE), Transactions.forIndex(7, Month.JULY),
                Transactions.forIndex(8, Month.AUGUST), Transactions.forIndex(9, Month.SEPTEMBER),
                Transactions.forIndex(10, Month.OCTOBER), Transactions.forIndex(11, Month.NOVEMBER),
                Transactions.forIndex(12, Month.DECEMBER));
    }

    @Test
    @DisplayName("When there are multiple transactions in the month, then it returns all the transactions")
    @MultipleTransactionsSameMonth
    void testFindAll_Multiple() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.empty();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When there are multiple transactions in the month, then it returns all the transactions")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(1, Month.JANUARY),
                Transactions.forIndexAndMonth(2, Month.JANUARY), Transactions.forIndexAndMonth(3, Month.JANUARY),
                Transactions.forIndexAndMonth(4, Month.JANUARY), Transactions.forIndexAndMonth(5, Month.JANUARY));
    }

}
