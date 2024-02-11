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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.member.test.config.data.annotation.AlternativeMember;
import com.bernardomg.association.member.test.config.data.annotation.NoSurnameMember;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.MemberCalendars;
import com.bernardomg.association.test.data.fee.annotation.AlternativeFeeFullYear;
import com.bernardomg.association.test.data.fee.annotation.FeeFullYear;
import com.bernardomg.association.test.data.fee.annotation.TwoFeeYearsConnected;
import com.bernardomg.association.test.data.fee.initializer.FeeInitializer;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all in year")
class ITFeeRepositoryFindAllInYear {

    @Autowired
    private FeeInitializer feeInitializer;

    @Autowired
    private FeeRepository  repository;

    @Test
    @DisplayName("With a not paid fee in the current month, it returns the calendar")
    @ValidMember
    void testGetYear_CurrentMonth_NotPaid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.CURRENT_YEAR.getValue(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidCurrentMonth());
    }

    @Test
    @DisplayName("With a not paid fee in the current month and searching for the next year, it returns nothing")
    @ValidMember
    void testGetYear_CurrentMonth_NotPaid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.NEXT_YEAR.getValue(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the current month and searching for the previous year, it returns nothing")
    @ValidMember
    void testGetYear_CurrentMonth_NotPaid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.PREVIOUS_YEAR.getValue(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month, it returns the calendar")
    @ValidMember
    void testGetYear_CurrentMonth_Paid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.CURRENT_YEAR.getValue(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidCurrentMonth(1));
    }

    @Test
    @DisplayName("With a paid fee in the current month and searching for the next year, it returns nothing")
    @ValidMember
    void testGetYear_CurrentMonth_Paid_SearchNextYear() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.NEXT_YEAR.getValue(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month and searching for the previous year, it returns nothing")
    @ValidMember
    void testGetYear_CurrentMonth_Paid_SearchPreviousYear() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.PREVIOUS_YEAR.getValue(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With a full year it returns all data")
    @ValidMember
    @FeeFullYear
    void testGetYear_FullYear() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(MemberCalendars.YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(Month.JANUARY.getValue()), Fees.paidAt(Month.FEBRUARY.getValue()),
                Fees.paidAt(Month.MARCH.getValue()), Fees.paidAt(Month.APRIL.getValue()),
                Fees.paidAt(Month.MAY.getValue()), Fees.paidAt(Month.JUNE.getValue()),
                Fees.paidAt(Month.JULY.getValue()), Fees.paidAt(Month.AUGUST.getValue()),
                Fees.paidAt(Month.SEPTEMBER.getValue()), Fees.paidAt(Month.OCTOBER.getValue()),
                Fees.paidAt(Month.NOVEMBER.getValue()), Fees.paidAt(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("With a full year it returns all the data")
    @ValidMember
    @AlternativeMember
    @FeeFullYear
    @AlternativeFeeFullYear
    void testGetYear_FullYear_TwoMembers() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(MemberCalendars.YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(Month.JANUARY.getValue()), Fees.paidAt(Month.FEBRUARY.getValue()),
                Fees.paidAt(Month.MARCH.getValue()), Fees.paidAt(Month.APRIL.getValue()),
                Fees.paidAt(Month.MAY.getValue()), Fees.paidAt(Month.JUNE.getValue()),
                Fees.paidAt(Month.JULY.getValue()), Fees.paidAt(Month.AUGUST.getValue()),
                Fees.paidAt(Month.SEPTEMBER.getValue()), Fees.paidAt(Month.OCTOBER.getValue()),
                Fees.paidAt(Month.NOVEMBER.getValue()), Fees.paidAt(Month.DECEMBER.getValue()),
                Fees.paidAtAlternative(Month.JANUARY.getValue()), Fees.paidAtAlternative(Month.FEBRUARY.getValue()),
                Fees.paidAtAlternative(Month.MARCH.getValue()), Fees.paidAtAlternative(Month.APRIL.getValue()),
                Fees.paidAtAlternative(Month.MAY.getValue()), Fees.paidAtAlternative(Month.JUNE.getValue()),
                Fees.paidAtAlternative(Month.JULY.getValue()), Fees.paidAtAlternative(Month.AUGUST.getValue()),
                Fees.paidAtAlternative(Month.SEPTEMBER.getValue()), Fees.paidAtAlternative(Month.OCTOBER.getValue()),
                Fees.paidAtAlternative(Month.NOVEMBER.getValue()), Fees.paidAtAlternative(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("With a not paid fee in the next year, it returns the calendar")
    @ValidMember
    void testGetYear_NextYear_NotPaid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeNextYear(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.NEXT_YEAR.getValue(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidNextYear());
    }

    @Test
    @DisplayName("With a paid fee in the next year, it returns the calendar")
    @ValidMember
    void testGetYear_NextYear_Paid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeNextYear(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.NEXT_YEAR.getValue(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidNextYear(1));
    }

    @Test
    @DisplayName("With no data, it returns nothing")
    void testGetYear_NoData() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.CURRENT_YEAR.getValue(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With no fees, it nothing")
    @ValidMember
    void testGetYear_NoFees() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.CURRENT_YEAR.getValue(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With user without surname it returns all data")
    @NoSurnameMember
    @FeeFullYear
    void testGetYear_NoSurname() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(MemberCalendars.YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAtNoSurname(Month.JANUARY.getValue()),
                Fees.paidAtNoSurname(Month.FEBRUARY.getValue()), Fees.paidAtNoSurname(Month.MARCH.getValue()),
                Fees.paidAtNoSurname(Month.APRIL.getValue()), Fees.paidAtNoSurname(Month.MAY.getValue()),
                Fees.paidAtNoSurname(Month.JUNE.getValue()), Fees.paidAtNoSurname(Month.JULY.getValue()),
                Fees.paidAtNoSurname(Month.AUGUST.getValue()), Fees.paidAtNoSurname(Month.SEPTEMBER.getValue()),
                Fees.paidAtNoSurname(Month.OCTOBER.getValue()), Fees.paidAtNoSurname(Month.NOVEMBER.getValue()),
                Fees.paidAtNoSurname(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("With a not paid fee in the previous month, it returns the calendar")
    @ValidMember
    void testGetYear_PreviousMonth_NotPaid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.PREVIOUS_MONTH.getYear(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidPreviousMonth());
    }

    @Test
    @DisplayName("With a paid fee in the previous month, it returns the calendar")
    @ValidMember
    void testGetYear_PreviousMonth_Paid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.PREVIOUS_MONTH.getYear(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidPreviousMonth(1));
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @ValidMember
    @TwoFeeYearsConnected
    void testGetYear_TwoConnectedYears_First() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(MemberCalendars.YEAR_PREVIOUS, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAtPreviousYear(Month.OCTOBER.getValue(), 1),
                Fees.paidAtPreviousYear(Month.NOVEMBER.getValue(), 2),
                Fees.paidAtPreviousYear(Month.DECEMBER.getValue(), 3));
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @ValidMember
    @TwoFeeYearsConnected
    void testGetYear_TwoConnectedYears_Second() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(MemberCalendars.YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(Month.JANUARY.getValue(), 4), Fees.paidAt(Month.FEBRUARY.getValue(), 5),
                Fees.paidAt(Month.MARCH.getValue(), 6), Fees.paidAt(Month.APRIL.getValue(), 7),
                Fees.paidAt(Month.MAY.getValue(), 8), Fees.paidAt(Month.JUNE.getValue(), 9),
                Fees.paidAt(Month.JULY.getValue(), 10));
    }

    @Test
    @DisplayName("With a not paid fee two months back, it returns the calendar")
    @ValidMember
    void testGetYear_TwoMonthsBack_NotPaid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.TWO_MONTHS_BACK.getYear(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.notPaidTwoMonthsBack());
    }

    @Test
    @DisplayName("With a paid fee two months back, it returns the calendar")
    @ValidMember
    void testGetYear_TwoMonthsBack_Paid() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(true);

        sort = Sort.unsorted();

        // WHEN
        fees = repository.findAllInYear(FeeConstants.TWO_MONTHS_BACK.getYear(), sort);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidTwoMonthsBack(1));
    }

}
