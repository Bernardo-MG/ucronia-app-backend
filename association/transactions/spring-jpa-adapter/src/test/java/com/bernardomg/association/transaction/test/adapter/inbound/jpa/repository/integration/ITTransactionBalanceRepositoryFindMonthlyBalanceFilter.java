
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
@DisplayName("TransactionEvolutionRepository - findEvolution")
class ITTransactionEvolutionRepositoryFindMonthlyEvolutionFilter {

    @Autowired
    private TransactionEvolutionRepository repository;

    @Test
    @DisplayName("When filtering ending before the year, then no month")
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
    @DisplayName("When filtering ending on December, then all the months")
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
    @DisplayName("When filtering the full year, then all the months")
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
    @DisplayName("When filtering by January, then only that month")
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
    @DisplayName("When filtering by January and February, then only those months")
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
    @DisplayName("When filtering with a range where the end is before the start, then nothing")
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
    @DisplayName("When filtering beginning after the year, then no month")
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
    @DisplayName("When filtering beginning on January, then all the months")
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
