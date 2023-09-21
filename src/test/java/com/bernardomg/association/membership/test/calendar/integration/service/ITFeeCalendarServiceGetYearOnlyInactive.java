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

package com.bernardomg.association.membership.test.calendar.integration.service;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.membership.calendar.model.UserFeeCalendar;
import com.bernardomg.association.membership.calendar.service.FeeCalendarService;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Fee calendar service - get all - filter by only inactive status")
class ITFeeCalendarServiceGetYearOnlyInactive {

    @Autowired
    private FeeCalendarService service;

    public ITFeeCalendarServiceGetYearOnlyInactive() {
        super();
    }

    @Test
    @DisplayName("With a full year it returns all the entities")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    void testGetYear_FullYear_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, false, sort);

        Assertions.assertThat(calendars)
            .isEmpty();
    }

    @Test
    @DisplayName("With a single month for an active member it returns nothing")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/first_month.sql" })
    void testGetYear_SingleMonth_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, false, sort);

        Assertions.assertThat(calendars)
            .isEmpty();
    }

    @Test
    @DisplayName("With a single month for an inactive member it returns all the data")
    @Sql({ "/db/queries/member/inactive.sql", "/db/queries/fee/first_month.sql" })
    void testGetYear_SingleMonth_Inactive_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, false, sort);

        Assertions.assertThat(IterableUtils.size(calendars))
            .isEqualTo(1);
        Assertions.assertThat(IterableUtils.size(calendars.iterator()
            .next()
            .getMonths()))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("With a single unpaid month for an active member it returns nothing")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/first_month_unpaid.sql" })
    void testGetYear_SingleMonth_Unpaid_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = service.getYear(2020, false, sort);

        Assertions.assertThat(calendars)
            .isEmpty();
    }

}
