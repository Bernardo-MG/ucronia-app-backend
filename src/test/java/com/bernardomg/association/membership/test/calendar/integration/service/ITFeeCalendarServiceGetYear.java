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

import java.time.Month;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.membership.test.calendar.config.factory.FeeMonths;
import com.bernardomg.association.membership.test.calendar.config.factory.MemberCalendars;
import com.bernardomg.association.membership.test.calendar.config.factory.MemberFeeCalendars;
import com.bernardomg.association.membership.test.calendar.util.assertion.MemberFeeCalendarAssertions;
import com.bernardomg.association.membership.test.fee.config.AlternativeFeeFullYear;
import com.bernardomg.association.membership.test.fee.config.FeeFullYear;
import com.bernardomg.association.membership.test.fee.config.TwoFeeYearsConnected;
import com.bernardomg.association.membership.test.member.config.AlternativeMember;
import com.bernardomg.association.membership.test.member.config.NoSurnameMember;
import com.bernardomg.association.membership.test.member.config.ValidMember;
import com.bernardomg.association.model.fee.MemberFeeCalendar;
import com.bernardomg.association.model.member.MemberStatus;
import com.bernardomg.association.service.member.MemberFeeCalendarService;
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
    @DisplayName("With a full year it returns all data")
    @ValidMember
    @FeeFullYear
    void testGetYear_FullYear() {
        final Iterable<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());

        MemberFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("With a full year it returns all the data")
    @ValidMember
    @AlternativeMember
    @FeeFullYear
    @AlternativeFeeFullYear
    void testGetYear_FullYear_TwoMembers() {
        final Iterable<MemberFeeCalendar> calendars;
        final Iterator<MemberFeeCalendar> calendarsItr;
        final Sort                        sort;
        MemberFeeCalendar                 calendar;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort);

        // THEN
        Assertions.assertThat(calendars)
            .as("calendars")
            .hasSize(2);

        calendarsItr = calendars.iterator();

        // First member
        calendar = calendarsItr.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());

        MemberFeeCalendarAssertions.assertFullYear(calendar);

        // Second member
        calendar = calendarsItr.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactiveAlternative());

        MemberFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("With user without surname it returns all data")
    @NoSurnameMember
    @FeeFullYear
    void testGetYear_NoSurname() {
        final Iterator<MemberFeeCalendar> calendars;
        final Sort                        sort;
        MemberFeeCalendar                 calendar;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort)
            .iterator();

        // THEN
        calendar = calendars.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.noSurname());

        MemberFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @ValidMember
    @TwoFeeYearsConnected
    void testGetYear_TwoConnectedYears_First() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR_PREVIOUS, MemberStatus.ALL, sort);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            final MemberFeeCalendar calendar;

            softly.assertThat(calendars)
                .as("calendars")
                .hasSize(1);

            calendar = calendars.iterator()
                .next();
            MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactivePreviousYear());

            softly.assertThat(calendar.getMonths())
                .as("months")
                .containsExactly(FeeMonths.paid(2019, Month.OCTOBER.getValue()),
                    FeeMonths.paid(2019, Month.NOVEMBER.getValue()), FeeMonths.paid(2019, Month.DECEMBER.getValue()));
        });
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @ValidMember
    @TwoFeeYearsConnected
    void testGetYear_TwoConnectedYears_Second() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        // GIVEN
        sort = Sort.unsorted();

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            final MemberFeeCalendar calendar;

            softly.assertThat(calendars)
                .as("calendars")
                .hasSize(1);

            calendar = calendars.iterator()
                .next();
            MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());

            softly.assertThat(calendar.getMonths())
                .as("months")
                .containsExactly(FeeMonths.paid(2020, Month.JANUARY.getValue()),
                    FeeMonths.paid(2020, Month.FEBRUARY.getValue()), FeeMonths.paid(2020, Month.MARCH.getValue()),
                    FeeMonths.paid(2020, Month.APRIL.getValue()), FeeMonths.paid(2020, Month.MAY.getValue()),
                    FeeMonths.paid(2020, Month.JUNE.getValue()), FeeMonths.paid(2020, Month.JULY.getValue()));
        });
    }

}
