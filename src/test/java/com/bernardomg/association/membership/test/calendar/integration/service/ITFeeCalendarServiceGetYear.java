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

import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.membership.calendar.model.FeeMonth;
import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;
import com.bernardomg.association.membership.calendar.service.MemberFeeCalendarService;
import com.bernardomg.association.membership.member.model.MemberStatus;
import com.bernardomg.association.membership.test.calendar.util.assertion.MemberFeeCalendarAssertions;
import com.bernardomg.association.membership.test.calendar.util.model.MemberCalendars;
import com.bernardomg.association.membership.test.calendar.util.model.MemberFeeCalendars;
import com.bernardomg.association.membership.test.fee.configuration.FeeFullYear;
import com.bernardomg.association.membership.test.fee.configuration.FeeFullYearAlternative;
import com.bernardomg.association.membership.test.fee.configuration.TwoFeeYearsConnected;
import com.bernardomg.association.membership.test.member.configuration.AlternativeMember;
import com.bernardomg.association.membership.test.member.configuration.NoSurnameMember;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee calendar service - get year")
class ITFeeCalendarServiceGetYear {

    @Autowired
    private MemberFeeCalendarService service;

    public ITFeeCalendarServiceGetYear() {
        super();
    }

    @Test
    @DisplayName("With a full year it returns all the entities")
    @ValidMember
    @FeeFullYear
    void testGetYear_FullYear_Count() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);
        Assertions.assertThat(calendars.iterator()
            .next()
            .getMonths())
            .hasSize(12);
    }

    @Test
    @DisplayName("With a full year it returns all data")
    @ValidMember
    @FeeFullYear
    void testGetYear_FullYear_Data() {
        final Iterator<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort)
            .iterator();

        calendar = calendars.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());

        MemberFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("With a full year it returns all the entities")
    @ValidMember
    @AlternativeMember
    @FeeFullYear
    @FeeFullYearAlternative
    void testGetYear_FullYear_TwoMembers_Count() {
        final Iterable<MemberFeeCalendar> calendars;
        final Iterator<MemberFeeCalendar> calendarsItr;
        final Sort                        sort;

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort);

        Assertions.assertThat(calendars)
            .hasSize(2);

        calendarsItr = calendars.iterator();
        Assertions.assertThat(calendarsItr.next()
            .getMonths())
            .hasSize(12);
        Assertions.assertThat(calendarsItr.next()
            .getMonths())
            .hasSize(12);
    }

    @Test
    @DisplayName("With a full year it returns all the data")
    @ValidMember
    @AlternativeMember
    @FeeFullYear
    @FeeFullYearAlternative
    void testGetYear_FullYear_TwoMembers_Data() {
        final Iterator<MemberFeeCalendar> calendars;
        final Sort                        sort;
        MemberFeeCalendar                 calendar;

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort)
            .iterator();

        // First member
        calendar = calendars.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());

        MemberFeeCalendarAssertions.assertFullYear(calendar);

        // Second member
        calendar = calendars.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactiveAlternative());

        MemberFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("When there is no data it returns nothing")
    @ValidMember
    void testGetYear_NoData_Count() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort);

        Assertions.assertThat(calendars)
            .isEmpty();
    }

    @Test
    @DisplayName("With user without surname it returns all data")
    @NoSurnameMember
    @FeeFullYear
    void testGetYear_NoSurname_Data() {
        final Iterator<MemberFeeCalendar> calendars;
        final Sort                        sort;
        MemberFeeCalendar                 calendar;

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort)
            .iterator();

        calendar = calendars.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.noSurname());

        MemberFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all the entities")
    @ValidMember
    @TwoFeeYearsConnected
    void testGetYear_TwoConnectedYears_First_Count() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR_PREVIOUS, MemberStatus.ALL, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);
        Assertions.assertThat(calendars.iterator()
            .next()
            .getMonths())
            .hasSize(3);
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @ValidMember
    @TwoFeeYearsConnected
    void testGetYear_TwoConnectedYears_First_Data() {
        final Iterator<MemberFeeCalendar> calendars;
        final Sort                        sort;
        MemberFeeCalendar                 calendar;
        Iterator<FeeMonth>                months;
        FeeMonth                          month;

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR_PREVIOUS, MemberStatus.ALL, sort)
            .iterator();

        calendar = calendars.next();
        Assertions.assertThat(calendar.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(calendar.getMemberName())
            .isEqualTo(MemberCalendars.FULL_NAME);
        Assertions.assertThat(calendar.getYear())
            .isEqualTo(MemberCalendars.YEAR_PREVIOUS);
        Assertions.assertThat(calendar.isActive())
            .isFalse();

        months = calendar.getMonths()
            .iterator();

        month = months.next();
        Assertions.assertThat(month.getFeeId())
            .isNotNull();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(10);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getFeeId())
            .isNotNull();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(11);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getFeeId())
            .isNotNull();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(12);
        Assertions.assertThat(month.getPaid())
            .isTrue();
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all the entities")
    @ValidMember
    @TwoFeeYearsConnected
    void testGetYear_TwoConnectedYears_Second_Count() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);
        Assertions.assertThat(calendars.iterator()
            .next()
            .getMonths())
            .hasSize(7);
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @ValidMember
    @TwoFeeYearsConnected
    void testGetYear_TwoConnectedYears_Second_Data() {
        final Iterator<MemberFeeCalendar> calendars;
        final Sort                        sort;
        MemberFeeCalendar                 calendar;
        Iterator<FeeMonth>                months;
        FeeMonth                          month;

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort)
            .iterator();

        calendar = calendars.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());

        months = calendar.getMonths()
            .iterator();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(1);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(2);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(3);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(4);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(5);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(6);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(7);
        Assertions.assertThat(month.getPaid())
            .isTrue();
    }

}
