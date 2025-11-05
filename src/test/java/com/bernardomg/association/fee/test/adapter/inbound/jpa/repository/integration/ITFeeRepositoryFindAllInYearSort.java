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

package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.time.Month;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.AlternativeFeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.AlternativeActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.MultipleActiveMemberAccents;
import com.bernardomg.association.member.test.configuration.factory.MemberCalendarConstants;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all in year - sort")
class ITFeeRepositoryFindAllInYearSort {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    @MultipleActiveMemberAccents
    @MultipleFees
    @Disabled("Database dependant")
    void testFindAllInYear_Accents_Name_Asc() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(MemberCalendarConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(fee -> fee.member()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Contact a Last name 1", "Contact é Last name 2", "Contact i Last name 3",
                "Contact o Last name 4", "Contact u Last name 5");
    }

    @Test
    @DisplayName("With an invalid field ordering throws an exception")
    void testFindAllInYear_NoData() {
        final ThrowingCallable execution;
        final Sorting          sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("abc", Sorting.Direction.ASC)));

        // WHEN
        execution = () -> repository.findAllInYear(MemberCalendarConstants.YEAR, sorting)
            .iterator();

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    @ActiveMember
    @AlternativeActiveMember
    @FeeFullYear
    @AlternativeFeeFullYear
    void testFindAllInYear_TwoMembers_Name_Asc() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(MemberCalendarConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidForMonth(Month.JANUARY.getValue()), Fees.paidForMonth(Month.FEBRUARY.getValue()),
                Fees.paidForMonth(Month.MARCH.getValue()), Fees.paidForMonth(Month.APRIL.getValue()),
                Fees.paidForMonth(Month.MAY.getValue()), Fees.paidForMonth(Month.JUNE.getValue()),
                Fees.paidForMonth(Month.JULY.getValue()), Fees.paidForMonth(Month.AUGUST.getValue()),
                Fees.paidForMonth(Month.SEPTEMBER.getValue()), Fees.paidForMonth(Month.OCTOBER.getValue()),
                Fees.paidForMonth(Month.NOVEMBER.getValue()), Fees.paidForMonth(Month.DECEMBER.getValue()),
                Fees.paidForMonthAlternative(Month.JANUARY.getValue()),
                Fees.paidForMonthAlternative(Month.FEBRUARY.getValue()),
                Fees.paidForMonthAlternative(Month.MARCH.getValue()),
                Fees.paidForMonthAlternative(Month.APRIL.getValue()),
                Fees.paidForMonthAlternative(Month.MAY.getValue()), Fees.paidForMonthAlternative(Month.JUNE.getValue()),
                Fees.paidForMonthAlternative(Month.JULY.getValue()),
                Fees.paidForMonthAlternative(Month.AUGUST.getValue()),
                Fees.paidForMonthAlternative(Month.SEPTEMBER.getValue()),
                Fees.paidForMonthAlternative(Month.OCTOBER.getValue()),
                Fees.paidForMonthAlternative(Month.NOVEMBER.getValue()),
                Fees.paidForMonthAlternative(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    @ActiveMember
    @AlternativeActiveMember
    @FeeFullYear
    @AlternativeFeeFullYear
    void testFindAllInYear_TwoMembers_Name_Desc() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.DESC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(MemberCalendarConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidForMonthAlternative(Month.JANUARY.getValue()),
                Fees.paidForMonthAlternative(Month.FEBRUARY.getValue()),
                Fees.paidForMonthAlternative(Month.MARCH.getValue()),
                Fees.paidForMonthAlternative(Month.APRIL.getValue()),
                Fees.paidForMonthAlternative(Month.MAY.getValue()), Fees.paidForMonthAlternative(Month.JUNE.getValue()),
                Fees.paidForMonthAlternative(Month.JULY.getValue()),
                Fees.paidForMonthAlternative(Month.AUGUST.getValue()),
                Fees.paidForMonthAlternative(Month.SEPTEMBER.getValue()),
                Fees.paidForMonthAlternative(Month.OCTOBER.getValue()),
                Fees.paidForMonthAlternative(Month.NOVEMBER.getValue()),
                Fees.paidForMonthAlternative(Month.DECEMBER.getValue()), Fees.paidForMonth(Month.JANUARY.getValue()),
                Fees.paidForMonth(Month.FEBRUARY.getValue()), Fees.paidForMonth(Month.MARCH.getValue()),
                Fees.paidForMonth(Month.APRIL.getValue()), Fees.paidForMonth(Month.MAY.getValue()),
                Fees.paidForMonth(Month.JUNE.getValue()), Fees.paidForMonth(Month.JULY.getValue()),
                Fees.paidForMonth(Month.AUGUST.getValue()), Fees.paidForMonth(Month.SEPTEMBER.getValue()),
                Fees.paidForMonth(Month.OCTOBER.getValue()), Fees.paidForMonth(Month.NOVEMBER.getValue()),
                Fees.paidForMonth(Month.DECEMBER.getValue()));
    }

}
