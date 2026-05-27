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
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.model.TransactionEvolutionMonth;
import com.bernardomg.association.transaction.domain.repository.TransactionEvolutionRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionEvolutionMonths;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionEvolutionRepository - find monthly evolution - filter")
class ITTransactionEvolutionRepositoryFindMonthlyEvolutionFilter {

    @Autowired
    private TransactionEvolutionRepository repository;

    @Test
    @DisplayName("Filtering ending before the year returns no month")
    @FullTransactionYear
    void testFindEvolution_EndBeforeStart() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;
        final Instant                               to;

        // GIVEN
        sorting = Sorting.unsorted();

        to = YearMonth.of(2019, Month.DECEMBER)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        // WHEN
        evolutions = repository.findEvolution(null, to, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .isEmpty();
    }

    @Test
    @DisplayName("Filtering ending on December returns all the months")
    @FullTransactionYear
    void testFindEvolution_EndDecember() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;
        final Instant                               to;

        // GIVEN
        sorting = Sorting.unsorted();

        to = YearMonth.of(2020, Month.DECEMBER)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        // WHEN
        evolutions = repository.findEvolution(null, to, sorting);

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
    @DisplayName("Filtering the full year returns all the months")
    @FullTransactionYear
    void testFindEvolution_FullYear() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;
        final Instant                               from;
        final Instant                               to;

        // GIVEN
        sorting = Sorting.unsorted();

        from = YearMonth.of(2020, Month.JANUARY)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        to = YearMonth.of(2020, Month.DECEMBER)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        // WHEN
        evolutions = repository.findEvolution(from, to, sorting);

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
    @DisplayName("Filtering by January returns only that month")
    @FullTransactionYear
    void testFindEvolution_January() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;
        final Instant                               from;
        final Instant                               to;

        // GIVEN
        sorting = Sorting.unsorted();

        from = YearMonth.of(2020, Month.JANUARY)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        to = YearMonth.of(2020, Month.JANUARY)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        // WHEN
        evolutions = repository.findEvolution(from, to, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .containsExactly(TransactionEvolutionMonths.forAmount(Month.JANUARY, 1, 1));
    }

    @Test
    @DisplayName("Filtering by January and February returns only those months")
    @FullTransactionYear
    void testFindEvolution_JanuaryToFebruary() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;
        final Instant                               from;
        final Instant                               to;

        // GIVEN
        sorting = Sorting.unsorted();

        from = YearMonth.of(2020, Month.JANUARY)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        to = YearMonth.of(2020, Month.FEBRUARY)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        // WHEN
        evolutions = repository.findEvolution(from, to, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .containsExactly(TransactionEvolutionMonths.forAmount(Month.JANUARY, 1, 1),
                TransactionEvolutionMonths.forAmount(Month.FEBRUARY, 1, 2));
    }

    @Test
    @DisplayName("Filtering with a range where the end is before the start returns nothing")
    @FullTransactionYear
    void testFindEvolution_RangeEndBeforeStart() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;
        final Instant                               from;
        final Instant                               to;

        // GIVEN
        sorting = Sorting.unsorted();

        from = YearMonth.of(2020, Month.DECEMBER)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        to = YearMonth.of(2020, Month.JANUARY)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        // WHEN
        evolutions = repository.findEvolution(from, to, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .isEmpty();
    }

    @Test
    @DisplayName("Filtering beginning after the year returns no month")
    @FullTransactionYear
    void testFindEvolution_StartAfterEnd() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;
        final Instant                               from;

        // GIVEN
        sorting = Sorting.unsorted();

        from = YearMonth.of(2021, Month.JANUARY)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        // WHEN
        evolutions = repository.findEvolution(from, null, sorting);

        // THEN
        Assertions.assertThat(evolutions)
            .as("evolutions")
            .isEmpty();
    }

    @Test
    @DisplayName("Filtering beginning on January returns all the months")
    @FullTransactionYear
    void testFindEvolution_StartInJanuary() {
        final Collection<TransactionEvolutionMonth> evolutions;
        final Sorting                               sorting;
        final Instant                               from;

        // GIVEN
        sorting = Sorting.unsorted();

        from = YearMonth.of(2020, Month.JANUARY)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        // WHEN
        evolutions = repository.findEvolution(from, null, sorting);

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

}
