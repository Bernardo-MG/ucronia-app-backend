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

import java.time.YearMonth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;
import com.bernardomg.association.membership.calendar.service.MemberFeeCalendarService;
import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.member.model.MemberStatus;
import com.bernardomg.association.membership.test.calendar.util.assertion.UserFeeCalendarAssertions;
import com.bernardomg.association.membership.test.calendar.util.model.MemberCalendars;
import com.bernardomg.association.membership.test.calendar.util.model.MemberFeeCalendars;
import com.bernardomg.association.membership.test.fee.configuration.FeeFullYear;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Fee calendar service - get year - filter by active status")
class ITFeeCalendarServiceGetYearActive {

    private static final YearMonth   CURRENT_MONTH   = YearMonth.now();

    private static final YearMonth   PREVIOUS_MONTH  = YearMonth.now()
        .minusMonths(1);

    private static final YearMonth   TWO_MONTHS_BACK = YearMonth.now()
        .minusMonths(2);

    @Autowired
    private FeeRepository            feeRepository;

    @Autowired
    private MemberFeeCalendarService service;

    public ITFeeCalendarServiceGetYearActive() {
        super();
    }

    private final void registerFeeCurrentMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(CURRENT_MONTH);

        feeRepository.save(fee);
    }

    private final void registerFeePreviousMonth(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(PREVIOUS_MONTH);

        feeRepository.save(fee);
    }

    private final void registerFeeTwoMonthsBack(final Boolean paid) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(1l);
        fee.setPaid(paid);

        fee.setDate(TWO_MONTHS_BACK);

        feeRepository.save(fee);
    }

    @Test
    @DisplayName("With a not paid fee in the current month, and filtering by active, it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterActive_CurrentMonth_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .hasSize(12);

        UserFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.active());
    }

    @Test
    @DisplayName("With a paid fee in the current month, and filtering by active, it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterActive_CurrentMonth_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .hasSize(12);

        UserFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.active());
    }

    @Test
    @DisplayName("With a not paid fee in the last three months, and filtering by active, it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterActive_LastThreeMonths_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        registerFeeCurrentMonth(false);
        registerFeePreviousMonth(false);
        registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .hasSize(12);

        UserFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.active());
    }

    @Test
    @DisplayName("With a paid fee in the last three months, and filtering by active, it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterActive_LastThreeMonths_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        registerFeeCurrentMonth(false);
        registerFeePreviousMonth(false);
        registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .hasSize(12);

        UserFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.active());
    }

    @Test
    @DisplayName("With a not paid fee in the previous month, and filtering by active, it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterActive_PreviousMonth_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        registerFeePreviousMonth(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a paid fee in the previous month, and filtering by active, it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterActive_PreviousMonth_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        registerFeePreviousMonth(true);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a not paid fee two months back, and filtering by active, it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterActive_TwoMonthsBack_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a paid fee two months back, and filtering by active, it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterActive_TwoMonthsBack_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        registerFeeTwoMonthsBack(true);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.ACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a not paid fee in the current month, and filtering by not active, it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterNotActive_CurrentMonth_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        registerFeeCurrentMonth(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a paid fee in the current month, and filtering by not active, it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterNotActive_CurrentMonth_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        registerFeeCurrentMonth(true);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a not paid fee in the last three months, and filtering by not active, it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterNotActive_LastThreeMonths_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        registerFeeCurrentMonth(false);
        registerFeePreviousMonth(false);
        registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a paid fee in the last three months, and filtering by not active, it returns nothing")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterNotActive_LastThreeMonths_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;

        registerFeeCurrentMonth(false);
        registerFeePreviousMonth(false);
        registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(0);
    }

    @Test
    @DisplayName("With a not paid fee in the previous month, and filtering by not active, it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterNotActive_PreviousMonth_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final Sort                        sort;
        final MemberFeeCalendar           calendar;

        registerFeePreviousMonth(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .hasSize(12);

        UserFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());
    }

    @Test
    @DisplayName("With a paid fee in the previous month, and filtering by not active, it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterNotActive_PreviousMonth_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        registerFeePreviousMonth(true);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .hasSize(12);

        UserFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());
    }

    @Test
    @DisplayName("With a not paid fee two months back, and filtering by not active, it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterNotActive_TwoMonthsBack_NotPaid() {
        final Iterable<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        registerFeeTwoMonthsBack(false);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .hasSize(12);

        UserFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());
    }

    @Test
    @DisplayName("With a paid fee two months back, and filtering by not active, it returns the calendar")
    @ValidMember
    @FeeFullYear
    void testGetYear_FilterNotActive_TwoMonthsBack_Paid() {
        final Iterable<MemberFeeCalendar> calendars;
        final MemberFeeCalendar           calendar;
        final Sort                        sort;

        registerFeeTwoMonthsBack(true);

        sort = Sort.unsorted();

        calendars = service.getYear(MemberCalendars.YEAR, MemberStatus.INACTIVE, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);

        calendar = calendars.iterator()
            .next();
        Assertions.assertThat(calendar.getMonths())
            .hasSize(12);

        UserFeeCalendarAssertions.isEqualTo(calendar, MemberFeeCalendars.inactive());
    }

}
