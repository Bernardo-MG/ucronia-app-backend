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

package com.bernardomg.association.membership.test.calendar.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;
import com.bernardomg.association.membership.calendar.service.MemberFeeCalendarService;
import com.bernardomg.association.membership.member.model.MemberStatus;
import com.bernardomg.association.membership.test.calendar.util.assertion.MemberFeeCalendarAssertions;
import com.bernardomg.association.membership.test.calendar.util.model.MemberCalendars;
import com.bernardomg.association.membership.test.calendar.util.model.MemberFeeCalendars;
import com.bernardomg.association.membership.test.fee.configuration.FeeFullYear;
import com.bernardomg.association.membership.test.fee.util.initializer.FeeInitializer;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee calendar service - get year - filter by inactive")
class ITFeeCalendarServiceGetYearFilterInactive {

    @Autowired
    private FeeInitializer           feeInitializer;

    @Autowired
    private MemberFeeCalendarService service;

    public ITFeeCalendarServiceGetYearFilterInactive() {
        super();
    }

    @Test
    @DisplayName("With a not paid fee in the current month it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_CurrentMonth_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        feeInitializer.registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the current month it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_CurrentMonth_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        feeInitializer.registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the last three months it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_LastThreeMonths_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .isEmpty();
    }

    @Test
    @DisplayName("With a paid fee in the last three months it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_LastThreeMonths_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        feeInitializer.registerFeeCurrentMonth(false);
        feeInitializer.registerFeePreviousMonth(false);
        feeInitializer.registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .isEmpty();
    }

    @Test
    @DisplayName("With a not paid fee in the next month it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_NextMonth_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;
        final MemberFeeCalendar           calendar;

        feeInitializer.registerFeeNextMonth(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .as("calendars")
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .as("calendars")
            .hasSize(12);

        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());
    }

    @Test
    @DisplayName("With a paid fee in the next month it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_NextMonth_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;
        final MemberFeeCalendar           calendar;

        feeInitializer.registerFeeNextMonth(true);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .as("calendars")
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .as("calendars")
            .hasSize(12);

        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());
    }

    @Test
    @DisplayName("With a not paid fee in the previous month it returns the the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_PreviousMonth_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;
        final MemberFeeCalendar           calendar;

        feeInitializer.registerFeePreviousMonth(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .as("calendars")
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .as("calendars")
            .hasSize(12);

        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());
    }

    @Test
    @DisplayName("With a paid fee in the previous month it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_PreviousMonth_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        feeInitializer.registerFeePreviousMonth(true);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .as("calendars")
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .as("calendars")
            .hasSize(12);

        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());
    }

    @Test
    @DisplayName("With a not paid fee two months back it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_TwoMonthsBack_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        feeInitializer.registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .as("calendars")
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .as("calendars")
            .hasSize(12);

        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());
    }

    @Test
    @DisplayName("With a paid fee two months back it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_TwoMonthsBack_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        feeInitializer.registerFeeTwoMonthsBack(true);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .as("calendars")
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .as("calendars")
            .hasSize(12);

        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());
    }

}
