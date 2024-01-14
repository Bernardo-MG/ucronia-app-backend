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
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.bernardomg.association.membership.test.calendar.config.factory.MemberCalendars;
import com.bernardomg.association.membership.test.calendar.config.factory.MemberFeeCalendars;
import com.bernardomg.association.membership.test.calendar.util.assertion.MemberFeeCalendarAssertions;
import com.bernardomg.association.membership.test.fee.config.AlternativeFeeFullYear;
import com.bernardomg.association.membership.test.fee.config.FeeFullYear;
import com.bernardomg.association.membership.test.member.config.AlternativeMember;
import com.bernardomg.association.membership.test.member.config.ValidMember;
import com.bernardomg.association.model.fee.MemberFeeCalendar;
import com.bernardomg.association.model.member.MemberStatus;
import com.bernardomg.association.service.member.MemberFeeCalendarService;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee calendar service - get year - sorted")
@ValidMember
@FeeFullYear
class ITFeeCalendarServiceGetYearSort {

    @Autowired
    private MemberFeeCalendarService service;

    public ITFeeCalendarServiceGetYearSort() {
        super();
    }

    @Test
    @DisplayName("With an invalid field ordering throws an exception")
    void testGetYear_NotExisting() {
        final Sort             sort;
        final ThrowingCallable execution;

        // GIVEN
        sort = Sort.by(Direction.ASC, "abc");

        // WHEN
        execution = () -> service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort)
            .iterator();

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    @ValidMember
    @AlternativeMember
    @FeeFullYear
    @AlternativeFeeFullYear
    void testGetYear_TwoMembers_Name_Asc() {
        final Iterator<MemberFeeCalendar> calendars;
        final Sort                        sort;
        MemberFeeCalendar                 calendar;

        // GIVEN
        sort = Sort.by(Order.asc("fullName"));

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort)
            .iterator();

        // THEN
        calendar = calendars.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());

        MemberFeeCalendarAssertions.assertFullYear(calendar);

        calendar = calendars.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactiveAlternative());

        MemberFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    @ValidMember
    @AlternativeMember
    @FeeFullYear
    @AlternativeFeeFullYear
    void testGetYear_TwoMembers_Name_Desc() {
        final Iterator<MemberFeeCalendar> calendars;
        final Sort                        sort;
        MemberFeeCalendar                 calendar;

        // GIVEN
        sort = Sort.by(Order.desc("fullName"));

        // WHEN
        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ALL, sort)
            .iterator();

        // THEN
        calendar = calendars.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactiveAlternative());

        MemberFeeCalendarAssertions.assertFullYear(calendar);

        calendar = calendars.next();
        MemberFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());

        MemberFeeCalendarAssertions.assertFullYear(calendar);
    }

}
