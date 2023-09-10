/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.calendar.test.fee.integration.service;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.calendar.fee.model.FeeMonth;
import com.bernardomg.association.calendar.fee.model.ImmutableUserFeeCalendar;
import com.bernardomg.association.calendar.fee.model.UserFeeCalendar;
import com.bernardomg.association.calendar.fee.service.FeeCalendarService;
import com.bernardomg.association.calendar.test.fee.util.assertion.UserFeeCalendarAssertions;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Fee calendar service - get all - filter by only active status")
class ITFeeCalendarServiceGetYearOnlyActive {

    @Autowired
    private FeeCalendarService service;

    public ITFeeCalendarServiceGetYearOnlyActive() {
        super();
    }

    @Test
    @DisplayName("With a full year it returns all the entities")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    void testGetYear_FullYear_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort);

        Assertions.assertThat(IterableUtils.size(calendars))
            .isEqualTo(1);
        Assertions.assertThat(IterableUtils.size(calendars.iterator()
            .next()
            .getMonths()))
            .isEqualTo(12);
    }

    @Test
    @DisplayName("With a full year it returns all data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    void testGetYear_FullYear_Data() {
        final Iterator<UserFeeCalendar> calendars;
        final UserFeeCalendar           calendar;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort)
            .iterator();

        calendar = calendars.next();
        Assertions.assertThat(calendar.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(calendar.getMemberName())
            .isEqualTo("Member 1 Surname 1");
        Assertions.assertThat(calendar.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(calendar.getActive())
            .isTrue();

        UserFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("With a full year it returns all the entities")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql", "/db/queries/fee/full_year.sql",
            "/db/queries/fee/full_year_alternative.sql" })
    void testGetYear_FullYear_TwoMembers_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Iterator<UserFeeCalendar> calendarsItr;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort);

        Assertions.assertThat(IterableUtils.size(calendars))
            .isEqualTo(2);

        calendarsItr = calendars.iterator();
        Assertions.assertThat(IterableUtils.size(calendarsItr.next()
            .getMonths()))
            .isEqualTo(12);
        Assertions.assertThat(IterableUtils.size(calendarsItr.next()
            .getMonths()))
            .isEqualTo(12);
    }

    @Test
    @DisplayName("With a full year it returns all the data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql", "/db/queries/fee/full_year.sql",
            "/db/queries/fee/full_year_alternative.sql" })
    void testGetYear_FullYear_TwoMembers_Data() {
        final Iterator<UserFeeCalendar> calendars;
        final Sort                      sort;
        UserFeeCalendar                 calendar;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort)
            .iterator();

        // First member
        calendar = calendars.next();
        UserFeeCalendarAssertions.isEqualTo(calendar, ImmutableUserFeeCalendar.builder()
            .memberId(1L)
            .memberName("Member 1 Surname 1")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(calendar);

        // Second member
        calendar = calendars.next();
        UserFeeCalendarAssertions.isEqualTo(calendar, ImmutableUserFeeCalendar.builder()
            .memberId(2L)
            .memberName("Member 2 Surname 2")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("With an inactive user and a full year it returns all the data")
    @Sql({ "/db/queries/member/inactive.sql", "/db/queries/fee/full_year.sql" })
    void testGetYear_Inactive_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort);

        Assertions.assertThat(IterableUtils.size(calendars))
            .isZero();
    }

    @Test
    @DisplayName("When there is no data it returns nothing")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetYear_NoData_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort);

        Assertions.assertThat(IterableUtils.size(calendars))
            .isZero();
    }

    @Test
    @DisplayName("With user without surname it returns all data")
    @Sql({ "/db/queries/member/no_surname.sql", "/db/queries/fee/full_year.sql" })
    void testGetYear_NoSurname_Data() {
        final Iterator<UserFeeCalendar> calendars;
        final Sort                      sort;
        UserFeeCalendar                 calendar;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort)
            .iterator();

        calendar = calendars.next();
        Assertions.assertThat(calendar.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(calendar.getMemberName())
            .isEqualTo("Member 1");
        Assertions.assertThat(calendar.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(calendar.getActive())
            .isTrue();

        UserFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("With a single month it returns all the entities")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/first_month.sql" })
    void testGetYear_SingleMonth_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort);

        Assertions.assertThat(IterableUtils.size(calendars))
            .isEqualTo(1);
        Assertions.assertThat(IterableUtils.size(calendars.iterator()
            .next()
            .getMonths()))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a single month it returns all data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/first_month.sql" })
    void testGetYear_SingleMonth_Data() {
        final Iterator<UserFeeCalendar> calendars;
        final UserFeeCalendar           calendar;
        final Iterator<FeeMonth>        months;
        final Sort                      sort;
        final FeeMonth                  month;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort)
            .iterator();

        calendar = calendars.next();
        Assertions.assertThat(calendar.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(calendar.getMemberName())
            .isEqualTo("Member 1 Surname 1");
        Assertions.assertThat(calendar.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(calendar.getActive())
            .isTrue();

        months = calendar.getMonths()
            .iterator();

        month = months.next();
        Assertions.assertThat(month.getFeeId())
            .isEqualTo(1);
        Assertions.assertThat(month.getMonth())
            .isEqualTo(1);
        Assertions.assertThat(month.getPaid())
            .isTrue();
    }

    @Test
    @DisplayName("With a single unpaid month it returns all the entities")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/first_month_unpaid.sql" })
    void testGetYear_SingleMonth_Unpaid_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort);

        Assertions.assertThat(IterableUtils.size(calendars))
            .isEqualTo(1);
        Assertions.assertThat(IterableUtils.size(calendars.iterator()
            .next()
            .getMonths()))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a single unpaid month it returns all data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/first_month_unpaid.sql" })
    void testGetYear_SingleMonth_Unpaid_Data() {
        final Iterator<UserFeeCalendar> calendars;
        final UserFeeCalendar           calendar;
        final Iterator<FeeMonth>        months;
        final Sort                      sort;
        final FeeMonth                  month;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort)
            .iterator();

        calendar = calendars.next();
        Assertions.assertThat(calendar.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(calendar.getMemberName())
            .isEqualTo("Member 1 Surname 1");
        Assertions.assertThat(calendar.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(calendar.getActive())
            .isTrue();

        months = calendar.getMonths()
            .iterator();

        month = months.next();
        Assertions.assertThat(month.getFeeId())
            .isEqualTo(1);
        Assertions.assertThat(month.getMonth())
            .isEqualTo(1);
        Assertions.assertThat(month.getPaid())
            .isFalse();
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all the entities")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    void testGetYear_TwoConnectedYears_First_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2019, true, sort);

        Assertions.assertThat(calendars)
            .hasSize(1);
        Assertions.assertThat(calendars.iterator()
            .next()
            .getMonths())
            .hasSize(3);
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    void testGetYear_TwoConnectedYears_First_Data() {
        final Iterator<UserFeeCalendar> calendars;
        final Sort                      sort;
        UserFeeCalendar                 calendar;
        Iterator<FeeMonth>              months;
        FeeMonth                        month;

        sort = Sort.unsorted();

        calendars = service.getYear(2019, true, sort)
            .iterator();

        calendar = calendars.next();
        Assertions.assertThat(calendar.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(calendar.getMemberName())
            .isEqualTo("Member 1 Surname 1");
        Assertions.assertThat(calendar.getYear())
            .isEqualTo(2019);
        Assertions.assertThat(calendar.getActive())
            .isTrue();

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
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    void testGetYear_TwoConnectedYears_Second_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort);

        Assertions.assertThat(IterableUtils.size(calendars))
            .isEqualTo(1);
        Assertions.assertThat(IterableUtils.size(calendars.iterator()
            .next()
            .getMonths()))
            .isEqualTo(7);
    }

    @Test
    @DisplayName("With two connected years when reading the second it returns all data for the queried year")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    void testGetYear_TwoConnectedYears_Second_Data() {
        final Iterator<UserFeeCalendar> calendars;
        final Sort                      sort;
        UserFeeCalendar                 calendar;
        Iterator<FeeMonth>              months;
        FeeMonth                        month;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, true, sort)
            .iterator();

        calendar = calendars.next();
        Assertions.assertThat(calendar.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(calendar.getMemberName())
            .isEqualTo("Member 1 Surname 1");
        Assertions.assertThat(calendar.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(calendar.getActive())
            .isTrue();

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
