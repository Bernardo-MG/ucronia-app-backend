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

package com.bernardomg.association.fee.test.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.model.FeeCalendar;
import com.bernardomg.association.fee.service.FeeCalendarService;
import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.fee.test.config.factory.FeeMonths;
import com.bernardomg.association.fee.test.util.assertion.MemberFeeCalendarAssertions;
import com.bernardomg.association.fee.test.util.initializer.FeeInitializer;
import com.bernardomg.association.member.model.MemberStatus;
import com.bernardomg.association.member.test.config.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.MemberFeeCalendars;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee calendar service - get year - filter by inactive")
class ITFeeCalendarServiceGetYearFilterInactive {

    @Autowired
    private FeeInitializer     feeInitializer;

    @Autowired
    private FeeCalendarService service;

    public ITFeeCalendarServiceGetYearFilterInactive() {
        super();
    }

    @Test
    @DisplayName("With a not paid fee in the current month, it returns nothing")
    @ValidMember
    void testGetYear_CurrentMonth_NotPaid() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.CURRENT_YEAR.getValue(), MemberStatus.INACTIVE, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the current month and searching for the next year, it returns nothing")
    @ValidMember
    void testGetYear_CurrentMonth_NotPaid_SearchNextYear() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.NEXT_YEAR.getValue(), MemberStatus.INACTIVE, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the current month and searching for the previous year, it returns nothing")
    @ValidMember
    void testGetYear_CurrentMonth_NotPaid_SearchPreviousYear() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.PREVIOUS_YEAR.getValue(), MemberStatus.INACTIVE, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month, it returns nothing")
    @ValidMember
    void testGetYear_CurrentMonth_Paid() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.CURRENT_YEAR.getValue(), MemberStatus.INACTIVE, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month and searching for the next year, it returns nothing")
    @ValidMember
    void testGetYear_CurrentMonth_Paid_SearchNextYear() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.NEXT_YEAR.getValue(), MemberStatus.INACTIVE, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month and searching for the previous year, it returns nothing")
    @ValidMember
    void testGetYear_CurrentMonth_Paid_SearchPreviousYear() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.PREVIOUS_YEAR.getValue(), MemberStatus.INACTIVE, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the next year, it returns the calendar")
    @ValidMember
    void testGetYear_NextYear_NotPaid() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeeNextYear(false);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.NEXT_YEAR.getValue(), MemberStatus.INACTIVE, sort);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            final FeeCalendar calendar;

            softly.assertThat(calendars)
                .as("calendars")
                .hasSize(1);

            calendar = calendars.iterator()
                .next();
            MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactiveNextYear());

            softly.assertThat(calendar.getMonths())
                .as("months")
                .containsExactly(FeeMonths.notPaidNextYear());
        });
    }

    @Test
    @DisplayName("With a paid fee in the next year, it returns the calendar")
    @ValidMember
    void testGetYear_NextYear_Paid() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeeNextYear(true);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.NEXT_YEAR.getValue(), MemberStatus.INACTIVE, sort);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            final FeeCalendar calendar;

            softly.assertThat(calendars)
                .as("calendars")
                .hasSize(1);

            calendar = calendars.iterator()
                .next();
            MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactiveNextYear());

            softly.assertThat(calendar.getMonths())
                .as("months")
                .containsExactly(FeeMonths.paidNextYear());
        });
    }

    @Test
    @DisplayName("With no data, it returns nothing")
    void testGetYear_NoData() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.CURRENT_YEAR.getValue(), MemberStatus.INACTIVE, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .isEmpty();
    }

    @Test
    @DisplayName("With no fees, it nothing")
    @ValidMember
    void testGetYear_NoFees() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.CURRENT_YEAR.getValue(), MemberStatus.INACTIVE, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the previous month, it returns the calendar")
    @ValidMember
    void testGetYear_PreviousMonth_NotPaid() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(false);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.PREVIOUS_MONTH.getYear(), MemberStatus.INACTIVE, sort);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            final FeeCalendar calendar;

            softly.assertThat(calendars)
                .as("calendars")
                .hasSize(1);

            calendar = calendars.iterator()
                .next();
            MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactivePreviousMonth());

            softly.assertThat(calendar.getMonths())
                .as("months")
                .containsExactly(FeeMonths.notPaidPreviousMonth());
        });
    }

    @Test
    @DisplayName("With a paid fee in the previous month, it returns the calendar")
    @ValidMember
    void testGetYear_PreviousMonth_Paid() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.PREVIOUS_MONTH.getYear(), MemberStatus.INACTIVE, sort);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            final FeeCalendar calendar;

            softly.assertThat(calendars)
                .as("calendars")
                .hasSize(1);

            calendar = calendars.iterator()
                .next();
            MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactivePreviousMonth());

            softly.assertThat(calendar.getMonths())
                .as("months")
                .containsExactly(FeeMonths.paidPreviousMonth());
        });
    }

    @Test
    @DisplayName("With a not paid fee two months back, it returns the calendar")
    @ValidMember
    void testGetYear_TwoMonthsBack_NotPaid() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.TWO_MONTHS_BACK.getYear(), MemberStatus.INACTIVE, sort);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            final FeeCalendar calendar;

            softly.assertThat(calendars)
                .as("calendars")
                .hasSize(1);

            calendar = calendars.iterator()
                .next();
            MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactivePreviousMonth());

            softly.assertThat(calendar.getMonths())
                .as("months")
                .containsExactly(FeeMonths.notPaidTwoMonthsBack());
        });
    }

    @Test
    @DisplayName("With a paid fee two months back, it returns the calendar")
    @ValidMember
    void testGetYear_TwoMonthsBack_Paid() {
        final Iterable<FeeCalendar> calendars;
        final Sort                  sort;

        // GIVEN
        feeInitializer.registerFeeTwoMonthsBack(true);

        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(FeeConstants.TWO_MONTHS_BACK.getYear(), MemberStatus.INACTIVE, sort);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            final FeeCalendar calendar;

            softly.assertThat(calendars)
                .as("calendars")
                .hasSize(1);

            calendar = calendars.iterator()
                .next();
            MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactivePreviousMonth());

            softly.assertThat(calendar.getMonths())
                .as("months")
                .containsExactly(FeeMonths.paidTwoMonthsBack());
        });
    }
}
