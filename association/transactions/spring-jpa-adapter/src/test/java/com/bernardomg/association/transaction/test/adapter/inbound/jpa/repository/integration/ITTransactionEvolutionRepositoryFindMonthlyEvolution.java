/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.time.Instant;
import java.time.Month;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.model.TransactionEvolutionMonth;
import com.bernardomg.association.transaction.domain.repository.TransactionEvolutionRepository;
import com.bernardomg.association.transaction.test.configuration.argument.CurrentAndPreviousMonthProvider;
import com.bernardomg.association.transaction.test.configuration.data.annotation.DecimalsAddZeroTransaction;
import com.bernardomg.association.transaction.test.configuration.data.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.test.configuration.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEvolutionMonths;
import com.bernardomg.association.transaction.test.util.initializer.TransactionInitializer;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;
import com.bernardomg.test.configuration.argument.AroundZeroArgumentsProvider;
import com.bernardomg.test.configuration.argument.DecimalArgumentsProvider;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionEvolutionRepository - find monthly evolution")
class ITTransactionEvolutionRepositoryFindMonthlyEvolution {

    @Autowired
    private TransactionEvolutionRepository repository;

    @Autowired
    private TransactionInitializer       transactionInitializer;

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(AroundZeroArgumentsProvider.class)
    @DisplayName("With values around zero it returns the correct amounts")
    void testFindEvolution_AroundZero(final Float amount) {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;

        // GIVEN
        transactionInitializer.registerCurrentMonth(amount);

        sorting = Sorting.unsorted();

        // WHEN
        evolutions = repository.findEvolution(null, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .containsExactly(TransactionEvolutionMonths.currentMonth(amount));
    }

    @Test
    @DisplayName("Returns evolution for the end of the current month")
    void testFindEvolution_CurrentMonthEnd() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;

        // GIVEN
        transactionInitializer.registerCurrentMonthEnd(1F);

        sorting = Sorting.unsorted();

        // WHEN
        evolutions = repository.findEvolution(null, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .containsExactly(TransactionEvolutionMonths.currentMonth(1));
    }

    @Test
    @DisplayName("Returns evolution for the start of the current month")
    void testFindEvolution_CurrentMonthStart() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;

        // GIVEN
        transactionInitializer.registerCurrentMonthStart(1F);

        sorting = Sorting.unsorted();

        // WHEN
        evolutions = repository.findEvolution(null, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .containsExactly(TransactionEvolutionMonths.currentMonth(1));
    }

    @ParameterizedTest(name = "Date: {0}")
    @ArgumentsSource(CurrentAndPreviousMonthProvider.class)
    @DisplayName("Returns evolution for the current month and adjacents")
    void testFindEvolution_Dates(final Instant date) {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;

        // GIVEN
        transactionInitializer.registerAt(date);

        sorting = Sorting.unsorted();

        // WHEN
        evolutions = repository.findEvolution(null, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .containsExactly(TransactionEvolutionMonths.forAmount(date, 1F));
    }

    @ParameterizedTest(name = "Amount: {0}")
    @ArgumentsSource(DecimalArgumentsProvider.class)
    @DisplayName("With decimal values it returns the correct amounts")
    void testFindEvolution_Decimal(final Float amount) {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;

        // GIVEN
        transactionInitializer.registerCurrentMonth(amount);

        sorting = Sorting.unsorted();

        // WHEN
        evolutions = repository.findEvolution(null, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .containsExactly(TransactionEvolutionMonths.currentMonth(amount));
    }

    @Test
    @DisplayName("With decimal values which sum zero the returned evolution is zero")
    @DecimalsAddZeroTransaction
    void testFindEvolution_DecimalsAddUpToZero() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolutions = repository.findEvolution(null, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .containsExactly(TransactionEvolutionMonths.forAmount(0F));
    }

    @Test
    @DisplayName("With a full year it returns twelve months")
    @FullTransactionYear
    void testFindEvolution_FullYear() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolutions = repository.findEvolution(null, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .containsExactly(TransactionEvolutionMonths.forAmount(Month.JANUARY, 1, 1),
                TransactionEvolutionMonths.forAmount(Month.FEBRUARY, 1, 2),
                TransactionEvolutionMonths.forAmount(Month.MARCH, 1, 3),
                TransactionEvolutionMonths.forAmount(Month.APRIL, 1, 4),
                TransactionEvolutionMonths.forAmount(Month.MAY, 1, 5),
                TransactionEvolutionMonths.forAmount(Month.JUNE, 1, 6),
                TransactionEvolutionMonths.forAmount(Month.JULY, 1, 7),
                TransactionEvolutionMonths.forAmount(Month.AUGUST, 1, 8),
                TransactionEvolutionMonths.forAmount(Month.SEPTEMBER, 1, 9),
                TransactionEvolutionMonths.forAmount(Month.OCTOBER, 1, 10),
                TransactionEvolutionMonths.forAmount(Month.NOVEMBER, 1, 11),
                TransactionEvolutionMonths.forAmount(Month.DECEMBER, 1, 12));
    }

    @Test
    @DisplayName("Returns evolution for the current month")
    void testFindEvolution_MonthStart() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;

        // GIVEN
        transactionInitializer.registerCurrentMonth(1F);

        sorting = Sorting.unsorted();

        // WHEN
        evolutions = repository.findEvolution(null, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .containsExactly(TransactionEvolutionMonths.currentMonth(1));
    }

    @Test
    @DisplayName("With multiple transactions for a single month it returns a single month")
    @MultipleTransactionsSameMonth
    void testFindEvolution_Multiple() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolutions = repository.findEvolution(null, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .containsExactly(TransactionEvolutionMonths.forAmount(5F));
    }

    @Test
    @DisplayName("Returns no evolution for the next month")
    void testFindEvolution_NextMonth() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;

        // GIVEN
        transactionInitializer.registerNextMonth(1F);

        sorting = Sorting.unsorted();

        // WHEN
        evolutions = repository.findEvolution(null, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .isEmpty();
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testFindEvolution_NoData() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        evolutions = repository.findEvolution(null, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .isEmpty();
    }

}
