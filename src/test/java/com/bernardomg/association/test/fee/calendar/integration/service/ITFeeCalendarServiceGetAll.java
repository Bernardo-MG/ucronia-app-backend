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

package com.bernardomg.association.test.fee.calendar.integration.service;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.calendar.model.FeeMonth;
import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;
import com.bernardomg.association.fee.calendar.service.FeeCalendarService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.calendar.assertion.UserFeeCalendarAssertions;

@IntegrationTest
@DisplayName("Fee calendar service - get all")
public class ITFeeCalendarServiceGetAll {

    @Autowired
    private FeeCalendarService service;

    public ITFeeCalendarServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("With a full year it returns all the entities")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testGetAll_FullYear_Count() {
        final Iterable<UserFeeCalendar> result;
        final Sort                      sort;

        sort = Sort.unsorted();

        result = service.getAll(2020, false, sort);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
        Assertions.assertThat(IterableUtils.size(result.iterator()
            .next()
            .getMonths()))
            .isEqualTo(12);
    }

    @Test
    @DisplayName("With a full year it returns all data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testGetAll_FullYear_Data() {
        final Iterator<UserFeeCalendar> data;
        final UserFeeCalendar           result;
        final Sort                      sort;

        sort = Sort.unsorted();

        data = service.getAll(2020, false, sort)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(result.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(result.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(result.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(result.getActive())
            .isTrue();

        UserFeeCalendarAssertions.assertFullYear(result);
    }

    @Test
    @DisplayName("With an inactive user and a full year it returns all the data")
    @Sql({ "/db/queries/member/inactive.sql", "/db/queries/fee/full_year.sql" })
    public void testGetAll_Inactive_Count() {
        final Iterable<UserFeeCalendar> result;
        final Sort                      sort;

        sort = Sort.unsorted();

        result = service.getAll(2020, false, sort);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
        Assertions.assertThat(IterableUtils.size(result.iterator()
            .next()
            .getMonths()))
            .isEqualTo(12);
    }

    @Test
    @DisplayName("With an inactive user and a full year it returns all data")
    @Sql({ "/db/queries/member/inactive.sql", "/db/queries/fee/full_year.sql" })
    public void testGetAll_Inactive_Data() {
        final Iterator<UserFeeCalendar> data;
        UserFeeCalendar                 result;
        final Sort                      sort;

        sort = Sort.unsorted();

        data = service.getAll(2020, false, sort)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(result.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(result.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(result.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(result.getActive())
            .isFalse();

        UserFeeCalendarAssertions.assertFullYear(result);
    }

    @Test
    @DisplayName("When there is no data it returns nothing")
    @Sql({ "/db/queries/member/single.sql" })
    public void testGetAll_NoData_Count() {
        final Iterable<UserFeeCalendar> result;
        final Sort                      sort;

        sort = Sort.unsorted();

        result = service.getAll(2020, false, sort);

        Assertions.assertThat(IterableUtils.size(result))
            .isZero();
    }

    @Test
    @DisplayName("With two connected years it returns all the entities")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    public void testGetAll_TwoConnectedYears_Count() {
        final Iterable<UserFeeCalendar> result;
        final Sort                      sort;

        sort = Sort.unsorted();

        result = service.getAll(2020, false, sort);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
        Assertions.assertThat(IterableUtils.size(result.iterator()
            .next()
            .getMonths()))
            .isEqualTo(7);
    }

    @Test
    @DisplayName("With two connected years it returns all data for the queried year")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    public void testGetAll_TwoConnectedYears_Data() {
        final Iterator<UserFeeCalendar> data;
        UserFeeCalendar                 result;
        Iterator<FeeMonth>              months;
        FeeMonth                        month;
        final Sort                      sort;

        sort = Sort.unsorted();

        data = service.getAll(2020, false, sort)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(result.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(result.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(result.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(result.getActive())
            .isTrue();

        months = result.getMonths()
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
