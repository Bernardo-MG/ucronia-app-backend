
package com.bernardomg.association.transaction.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.transaction.TestApplication;
import com.bernardomg.association.transaction.domain.model.TransactionMonthsRange;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.association.transaction.test.configuration.data.annotation.FullConsecutiveTransactionYears;
import com.bernardomg.association.transaction.test.configuration.data.annotation.FullNotConsecutiveTransactionYears;
import com.bernardomg.association.transaction.test.configuration.data.annotation.FullTransactionYear;
import com.bernardomg.association.transaction.test.configuration.data.annotation.MultipleTransactionsSameDay;
import com.bernardomg.association.transaction.test.configuration.data.annotation.MultipleTransactionsSameMonth;
import com.bernardomg.association.transaction.test.configuration.data.annotation.PositiveTransaction;
import com.bernardomg.association.transaction.test.configuration.factory.TransactionCalendarMonthsRanges;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("TransactionRepository - findRange")
class ITTransactionRepositoryFindDates {

    @Autowired
    private TransactionRepository repository;

    @Test
    @DisplayName("When there is a single transaction, then a single month is returned")
    @PositiveTransaction
    void testFindDates() {
        final TransactionMonthsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.months())
            .as("months")
            .containsExactly(YearMonth.of(2020, Month.FEBRUARY));
    }

    @Test
    @DisplayName("When there is two full consecutive years, then a range for them is returned")
    @FullConsecutiveTransactionYears
    void testFindDates_ConsecutiveFullYear() {
        final TransactionMonthsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.months())
            .as("months")
            .hasSize(24)
            .containsExactlyElementsOf(TransactionCalendarMonthsRanges.CONSECUTIVE_FULL_YEAR_MONTHS);
    }

    @Test
    @DisplayName("When there is a full year, then a range for the full year is returned")
    @FullTransactionYear
    void testFindDates_FullYear() {
        final TransactionMonthsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.months())
            .as("months")
            .hasSize(12)
            .containsExactlyElementsOf(TransactionCalendarMonthsRanges.FULL_YEAR_MONTHS);
    }

    @Test
    @DisplayName("When there are multiple transactions the same day, then a single month is returned")
    @MultipleTransactionsSameDay
    void testFindDates_MultipleSameDay() {
        final TransactionMonthsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.months())
            .as("months")
            .hasSize(1)
            .containsExactly(YearMonth.of(2020, Month.JANUARY));
    }

    @Test
    @DisplayName("When there are multiple transactions the same month, then a single month is returned")
    @MultipleTransactionsSameMonth
    void testFindDates_MultipleSameMonth() {
        final TransactionMonthsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.months())
            .as("months")
            .containsExactly(YearMonth.of(2020, Month.JANUARY));
    }

    @Test
    @DisplayName("When there is no data, then an empty range is returned")
    void testFindDates_NoData() {
        final TransactionMonthsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.months())
            .as("months")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is two full not consecutive years, then a range for them is returned")
    @FullNotConsecutiveTransactionYears
    void testFindDates_NotConsecutiveFullYear() {
        final TransactionMonthsRange range;

        // WHEN
        range = repository.findRange();

        // THEN
        Assertions.assertThat(range.months())
            .as("months")
            .hasSize(24)
            .containsExactlyElementsOf(TransactionCalendarMonthsRanges.NOT_CONSECUTIVE_FULL_YEAR_MONTHS);
    }

}
