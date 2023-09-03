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

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.calendar.fee.model.UserFeeCalendar;
import com.bernardomg.association.calendar.fee.service.FeeCalendarService;
import com.bernardomg.association.calendar.test.fee.util.assertion.UserFeeCalendarAssertions;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Fee calendar service - get all - sorted")
@Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
class ITFeeCalendarServiceGetYearSort {

    @Autowired
    private FeeCalendarService service;

    public ITFeeCalendarServiceGetYearSort() {
        super();
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    void testGetYear_Name_Asc() {
        final Sort                      sort;
        final Iterator<UserFeeCalendar> calendars;
        final UserFeeCalendar           calendar;

        sort = Sort.by(Order.asc("name"));

        calendars = service.getYear(2020, false, sort)
            .iterator();

        calendar = calendars.next();
        Assertions.assertThat(calendar.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(calendar.getName())
            .isEqualTo("Member 1 Surname 1");
        Assertions.assertThat(calendar.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(calendar.getActive())
            .isTrue();

        UserFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    void testGetYear_Name_Desc() {
        final Sort                      sort;
        final Iterator<UserFeeCalendar> calendars;
        final UserFeeCalendar           calendar;

        sort = Sort.by(Order.asc("name"));

        calendars = service.getYear(2020, false, sort)
            .iterator();

        calendar = calendars.next();
        Assertions.assertThat(calendar.getMemberId())
            .isEqualTo(1);
        Assertions.assertThat(calendar.getName())
            .isEqualTo("Member 1 Surname 1");
        Assertions.assertThat(calendar.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(calendar.getActive())
            .isTrue();

        UserFeeCalendarAssertions.assertFullYear(calendar);
    }

    @Test
    @DisplayName("With an invalid field ordering throws an exception")
    void testGetYear_NotExisting() {
        final Sort             sort;
        final ThrowingCallable execution;

        sort = Sort.by(Direction.ASC, "abc");

        execution = () -> service.getYear(2020, false, sort)
            .iterator();

        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(BadSqlGrammarException.class);
    }

}