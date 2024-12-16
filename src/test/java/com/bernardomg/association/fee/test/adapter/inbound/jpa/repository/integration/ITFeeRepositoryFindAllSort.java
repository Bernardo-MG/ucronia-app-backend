/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.test.configuration.factory.FeesQuery;
import com.bernardomg.association.person.test.configuration.data.annotation.AccentInactiveMembershipPerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MultipleInactiveMembershipPerson;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all - sort")
class ITFeeRepositoryFindAllSort {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With ascending order by name with accents it returns the ordered data")
    @AccentInactiveMembershipPerson
    @MultipleFees
    @Disabled("Database dependant")
    void testFindAll_Accents_Name_Asc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(fee -> fee.person()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Person a Last name 1", "Person é Last name 2", "Person i Last name 3",
                "Person o Last name 4", "Person u Last name 5");
    }

    @Test
    @DisplayName("With ascending order by date it returns the ordered data")
    @MultipleInactiveMembershipPerson
    @MultipleFees
    void testFindAll_Date_Asc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Fee::date)
            .as("fee dates")
            .containsExactly(YearMonth.of(FeeConstants.YEAR_VALUE, Month.FEBRUARY),
                YearMonth.of(FeeConstants.YEAR_VALUE, Month.MARCH), YearMonth.of(FeeConstants.YEAR_VALUE, Month.APRIL),
                YearMonth.of(FeeConstants.YEAR_VALUE, Month.MAY), YearMonth.of(FeeConstants.YEAR_VALUE, Month.JUNE));
    }

    @Test
    @DisplayName("With descending order by date it returns the ordered data")
    @MultipleInactiveMembershipPerson
    @MultipleFees
    void testFindAll_Date_Desc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = new Sorting(List.of(new Sorting.Property("date", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(Fee::date)
            .as("fee dates")
            .containsExactly(YearMonth.of(FeeConstants.YEAR_VALUE, Month.JUNE),
                YearMonth.of(FeeConstants.YEAR_VALUE, Month.MAY), YearMonth.of(FeeConstants.YEAR_VALUE, Month.APRIL),
                YearMonth.of(FeeConstants.YEAR_VALUE, Month.MARCH),
                YearMonth.of(FeeConstants.YEAR_VALUE, Month.FEBRUARY));
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    @MultipleInactiveMembershipPerson
    @MultipleFees
    void testFindAll_Name_Asc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(fee -> fee.person()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Person 1 Last name 1", "Person 2 Last name 2", "Person 3 Last name 3",
                "Person 4 Last name 4", "Person 5 Last name 5");
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    @MultipleInactiveMembershipPerson
    @MultipleFees
    void testFindAll_Name_Desc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(fee -> fee.person()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Person 5 Last name 5", "Person 4 Last name 4", "Person 3 Last name 3",
                "Person 2 Last name 2", "Person 1 Last name 1");
    }

    @Test
    @DisplayName("With an invalid field ordering throws an exception")
    @Disabled
    @MultipleInactiveMembershipPerson
    @MultipleFees
    void testFindAll_NotExisting() {
        final FeeQuery         feeQuery;
        final ThrowingCallable executable;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = new Sorting(List.of(new Sorting.Property("abc", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        executable = () -> repository.findAll(feeQuery, pagination, sorting)
            .iterator();

        // THEN
        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(BadSqlGrammarException.class);
    }

    @Test
    @DisplayName("With ascending order by paid flag it returns the ordered data")
    @MultipleInactiveMembershipPerson
    @MultipleFees
    void testFindAll_Paid_Asc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = new Sorting(List.of(new Sorting.Property("paid", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidAt(5, Month.JUNE), Fees.paidAt(1, Month.FEBRUARY), Fees.paidAt(2, Month.MARCH),
                Fees.paidAt(3, Month.APRIL), Fees.paidAt(4, Month.MAY));
    }

    @Test
    @DisplayName("With descending order by paid flag it returns the ordered data")
    @MultipleInactiveMembershipPerson
    @MultipleFees
    void testFindAll_Paid_Desc() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(0, 10);
        sorting = new Sorting(List.of(new Sorting.Property("paid", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        feeQuery = FeesQuery.empty();

        // WHEN
        fees = repository.findAll(feeQuery, pagination, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(1, Month.FEBRUARY), Fees.paidAt(2, Month.MARCH), Fees.paidAt(3, Month.APRIL),
                Fees.paidAt(4, Month.MAY), Fees.notPaidAt(5, Month.JUNE));
    }

}
