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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.AlternativeFeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidAndNotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.data.annotation.TwoFeeYearsConnected;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.AlternativeActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.NoLastNameActiveMember;
import com.bernardomg.association.member.test.configuration.factory.MemberCalendarConstants;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all in year")
class ITFeeRepositoryFindAllInYear {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With a full year it returns all data")
    @PositiveFeeType
    @ActiveMember
    @FeeFullYear
    void testFindAllInYear_Active_FullYear() {
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
                Fees.paidForMonth(Month.NOVEMBER.getValue()), Fees.paidForMonth(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("With a full year it returns all the data")
    @PositiveFeeType
    @ActiveMember
    @AlternativeActiveMember
    @FeeFullYear
    @AlternativeFeeFullYear
    void testFindAllInYear_Active_FullYear_TwoMembers() {
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
    @DisplayName("With no fees, it nothing")
    @PositiveFeeType
    @ActiveMember
    void testFindAllInYear_Active_NoFees() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With user without last name it returns all data")
    @PositiveFeeType
    @NoLastNameActiveMember
    @FeeFullYear
    void testFindAllInYear_Active_NoLastName() {
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
            .containsExactly(Fees.paidForMonthNoLastName(Month.JANUARY.getValue()),
                Fees.paidForMonthNoLastName(Month.FEBRUARY.getValue()),
                Fees.paidForMonthNoLastName(Month.MARCH.getValue()),
                Fees.paidForMonthNoLastName(Month.APRIL.getValue()), Fees.paidForMonthNoLastName(Month.MAY.getValue()),
                Fees.paidForMonthNoLastName(Month.JUNE.getValue()), Fees.paidForMonthNoLastName(Month.JULY.getValue()),
                Fees.paidForMonthNoLastName(Month.AUGUST.getValue()),
                Fees.paidForMonthNoLastName(Month.SEPTEMBER.getValue()),
                Fees.paidForMonthNoLastName(Month.OCTOBER.getValue()),
                Fees.paidForMonthNoLastName(Month.NOVEMBER.getValue()),
                Fees.paidForMonthNoLastName(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("With a not paid fee, it returns them")
    @PositiveFeeType
    @ActiveMember
    @NotPaidFee
    void testFindAllInYear_Active_NotPaid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidForMonth(1, Month.FEBRUARY));
    }

    @Test
    @DisplayName("With a not paid fee and searching for the next year, it returns nothing")
    @PositiveFeeType
    @ActiveMember
    @NotPaidFee
    void testFindAllInYear_Active_NotPaid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(FeeConstants.NEXT_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee and searching for the previous year, it returns nothing")
    @PositiveFeeType
    @ActiveMember
    @NotPaidFee
    void testFindAllInYear_Active_NotPaid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(FeeConstants.PREVIOUS_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month, it returns them")
    @PositiveFeeType
    @ActiveMember
    @PaidFee
    void testFindAllInYear_Active_Paid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidForMonth(1, Month.FEBRUARY));
    }

    @Test
    @DisplayName("With a paid fee and searching for the next year, it returns nothing")
    @PositiveFeeType
    @ActiveMember
    @PaidFee
    void testFindAllInYear_Active_Paid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(FeeConstants.NEXT_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee and searching for the previous year, it returns nothing")
    @PositiveFeeType
    @ActiveMember
    @PaidFee
    void testFindAllInYear_Active_Paid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(FeeConstants.PREVIOUS_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With both a paid and not paid fees, for an active member, it returns the calendar")
    @PositiveFeeType
    @ActiveMember
    @PaidAndNotPaidFee
    void testFindAllInYear_Active_PaidAndNotPaid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidForMonth(1, Month.FEBRUARY), Fees.notPaidForMonth(2, 10, Month.MARCH));
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @PositiveFeeType
    @ActiveMember
    @TwoFeeYearsConnected
    void testFindAllInYear_Active_TwoConnectedYears_First() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(MemberCalendarConstants.PREVIOUS_YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidForMonthPreviousYear(Month.OCTOBER.getValue(), 1),
                Fees.paidForMonthPreviousYear(Month.NOVEMBER.getValue(), 2),
                Fees.paidForMonthPreviousYear(Month.DECEMBER.getValue(), 3));
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @PositiveFeeType
    @ActiveMember
    @TwoFeeYearsConnected
    void testFindAllInYear_Active_TwoConnectedYears_Second() {
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
            .containsExactly(Fees.paidForMonth(Month.JANUARY.getValue(), 4),
                Fees.paidForMonth(Month.FEBRUARY.getValue(), 5), Fees.paidForMonth(Month.MARCH.getValue(), 6),
                Fees.paidForMonth(Month.APRIL.getValue(), 7), Fees.paidForMonth(Month.MAY.getValue(), 8),
                Fees.paidForMonth(Month.JUNE.getValue(), 9), Fees.paidForMonth(Month.JULY.getValue(), 10));
    }

    @Test
    @DisplayName("With a full year, for an inactive member, it returns all data")
    @PositiveFeeType
    @InactiveMember
    @FeeFullYear
    void testFindAllInYear_Inactive_FullYear() {
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
                Fees.paidForMonth(Month.NOVEMBER.getValue()), Fees.paidForMonth(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("With both a paid and not paid fees, for an inactive member, it returns the calendar")
    @PositiveFeeType
    @InactiveMember
    @PaidAndNotPaidFee
    void testFindAllInYear_Inactive_PaidAndNotPaid() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidForMonth(1, Month.FEBRUARY), Fees.notPaidForMonth(2, 10, Month.MARCH));
    }

    @Test
    @DisplayName("With no data, it returns nothing")
    void testFindAllInYear_NoData() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

}
