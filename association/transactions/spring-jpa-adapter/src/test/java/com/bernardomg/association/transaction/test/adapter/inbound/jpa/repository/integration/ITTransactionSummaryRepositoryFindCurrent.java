
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.model.TransactionSummary;
import com.bernardomg.association.transaction.domain.repository.TransactionSummaryRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionSummaries;
import com.bernardomg.association.transaction.test.util.initializer.TransactionInitializer;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.configuration.argument.AroundZeroArgumentsProvider;
import com.bernardomg.test.configuration.argument.DecimalArgumentsProvider;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionSummaryRepository - findSummary")
class ITTransactionSummaryRepositoryFindCurrent {

    @Autowired
    private TransactionSummaryRepository repository;

    @Autowired
    private TransactionInitializer       transactionInitializer;

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("When values are around zero, then the correct amounts are returned")
    void testFindCurrent_AroundZero(final float amount) {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerCurrentMonth(amount);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("With values around zero it returns the correct amounts")
            .contains(TransactionSummaries.amount(amount));
    }

    @Test
    @DisplayName("When data for the current month is provided, then it returns the evolution")
    void testFindCurrent_CurrentMonth() {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerCurrentMonth(1F);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("When data for the current month is provided, then it returns the evolution")
            .contains(TransactionSummaries.amount(1));
    }

    @Test
    @DisplayName("When data for the current month and previous months is provided, then it returns the evolution")
    void testFindCurrent_CurrentMonthAndPrevious() {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerMonthsBack(1F, 0, 1L);
        transactionInitializer.registerMonthsBack(2F, 1, 2L);
        transactionInitializer.registerMonthsBack(3F, 2, 3L);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("When data for the current month and previous months is provided, then it returns the evolution")
            .contains(TransactionSummaries.amount(1, 6));
    }

    @Test
    @DisplayName("When data for the current month at the last day is provided, then it returns the evolution")
    void testFindCurrent_CurrentMonthEnd() {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerCurrentMonthEnd(1F);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("When data for the current month at the last day is provided, then it returns the evolution")
            .contains(TransactionSummaries.amount(1));
    }

    @Test
    @DisplayName("When data for the current month at the first day is provided, then it returns the evolution")
    void testFindCurrent_CurrentMonthStart() {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerCurrentMonthStart(1F);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("When data for the current month at the first day is provided, then it returns the evolution")
            .contains(TransactionSummaries.amount(1));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("When decimal values are used, then the correct amounts are returned")
    void testFindCurrent_Decimal(final Float amount) {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerCurrentMonth(amount);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("With decimal values it returns the correct amounts")
            .contains(TransactionSummaries.amount(amount));
    }

    @Test
    @DisplayName("When decimal values sum to zero, then the evolution is zero")
    void testFindCurrent_DecimalsAddUpToZero() {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerCurrentMonth(-40.8F, 1L);
        transactionInitializer.registerCurrentMonth(13.6F, 2L);
        transactionInitializer.registerCurrentMonth(13.6F, 3L);
        transactionInitializer.registerCurrentMonth(13.6F, 4L);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("With decimal values which sum zero the returned evolution is zero")
            .contains(TransactionSummaries.amount(0));
    }

    @Test
    @DisplayName("When there is a full year, then it returns the correct data")
    @FullTransactionYear
    void testFindCurrent_FullYear() {
        final Optional<TransactionSummary> evolution;

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("When there is a full year, then it returns the correct data")
            .contains(TransactionSummaries.amount(0, 12));
    }

    @Test
    @DisplayName("When there are multiple transactions for a single month, then it returns the correct data")
    void testFindCurrent_Multiple() {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerCurrentMonth(1F, 1L);
        transactionInitializer.registerCurrentMonth(1F, 2L);
        transactionInitializer.registerCurrentMonth(1F, 3L);
        transactionInitializer.registerCurrentMonth(1F, 4L);
        transactionInitializer.registerCurrentMonth(1F, 5L);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("When there are multiple transactions for a single month, then it returns the correct data")
            .contains(TransactionSummaries.amount(5));
    }

    @Test
    @DisplayName("When data for the next month is provided, then it returns no evolution")
    void testFindCurrent_NextMonth() {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerNextMonth(1F);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("When data for the next month is provided, then it returns no evolution")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is no data, then it returns nothing")
    void testFindCurrent_NoData() {
        final Optional<TransactionSummary> evolution;

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("When there is no data, then it returns nothing")
            .isEmpty();
    }

    @Test
    @DisplayName("When data for the previous month is provided, then it returns the evolution but no results")
    void testFindCurrent_PreviousMonth() {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerPreviousMonth(1F, 1L);
        transactionInitializer.registerPreviousMonth(2F, 2L);
        transactionInitializer.registerPreviousMonth(3F, 3L);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("When data for the previous month is provided, then it returns the evolution but no results")
            .contains(TransactionSummaries.amount(0, 6));
    }

    @Test
    @DisplayName("When data for the previous months is provided, then it returns the evolution but no results")
    void testFindCurrent_PreviousMonths() {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerMonthsBack(1F, 1, 1L);
        transactionInitializer.registerMonthsBack(2F, 2, 2L);
        transactionInitializer.registerMonthsBack(3F, 3, 3L);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("When data for the previous months is provided, then it returns the evolution but no results")
            .contains(TransactionSummaries.amount(0, 6));
    }

    @Test
    @DisplayName("When data for the previous months, including gaps is provided, then it returns the evolution but no results")
    void testFindCurrent_PreviousMonths_Gaps() {
        final Optional<TransactionSummary> evolution;

        // GIVEN
        transactionInitializer.registerMonthsBack(1F, 1, 1L);
        transactionInitializer.registerMonthsBack(2F, 2, 2L);
        transactionInitializer.registerMonthsBack(3F, 5, 3L);

        // WHEN
        evolution = repository.findSummary();

        // THEN
        Assertions.assertThat(evolution)
            .as("When data for the previous months, including gaps is provided, then it returns the evolution but no results")
            .contains(TransactionSummaries.amount(0, 6));
    }

}
