
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;

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
import com.bernardomg.association.transaction.test.configuration.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.test.configuration.factory.Transactions;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionsFilters;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.pagination.AbstractPaginationIT;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionRepository - findAll")
@MultipleTransactionsSameMonth
class ITTransactionRepositoryFindAllWithFilterPagination extends AbstractPaginationIT<Transaction> {

    @Autowired
    private TransactionRepository repository;

    public ITTransactionRepositoryFindAllWithFilterPagination() {
        super(5);
    }

    @Override
    protected final Page<Transaction> read(final Pagination pagination, final Sorting sorting) {
        return repository.findAll(TransactionsFilters.empty(), pagination, sorting);
    }

    @Test
    @DisplayName("When requesting pagination for the first page, then it returns the first page")
    void testFindAll_Page1() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(1, 1);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.empty();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When requesting pagination for the first page, then it returns the first page")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndex(1, Month.JANUARY));
    }

    @Test
    @DisplayName("When requesting pagination for the second page, then it returns the second page")
    void testFindAll_Page2() {
        final Page<Transaction> transactions;
        final TransactionFilter transactionFilter;
        final Pagination        pagination;
        final Sorting           sorting;

        // GIVEN
        pagination = new Pagination(2, 1);
        sorting = Sorting.unsorted();

        transactionFilter = TransactionsFilters.empty();

        // WHEN
        transactions = repository.findAll(transactionFilter, pagination, sorting);

        // THEN
        Assertions.assertThat(transactions)
            .as("When requesting pagination for the second page, then it returns the second page")
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Transactions.forIndexAndMonth(2, Month.JANUARY));
    }

}
