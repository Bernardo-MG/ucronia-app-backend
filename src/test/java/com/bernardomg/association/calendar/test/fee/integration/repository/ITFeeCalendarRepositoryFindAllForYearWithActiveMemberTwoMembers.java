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

package com.bernardomg.association.calendar.test.fee.integration.repository;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.calendar.fee.model.ImmutableUserFeeCalendar;
import com.bernardomg.association.calendar.fee.model.UserFeeCalendar;
import com.bernardomg.association.calendar.fee.persistence.repository.FeeCalendarRepository;
import com.bernardomg.association.calendar.test.fee.util.assertion.UserFeeCalendarAssertions;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee calendar repository - find all for year with active member - two members")
@Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql", "/db/queries/fee/full_year.sql",
        "/db/queries/fee/full_year_alternative.sql" })
class ITFeeCalendarRepositoryFindAllForYearWithActiveMemberTwoMembers {

    @Autowired
    private FeeCalendarRepository repository;

    public ITFeeCalendarRepositoryFindAllForYearWithActiveMemberTwoMembers() {
        super();
    }

    @Test
    @DisplayName("With a full year it returns all the entities")
    void testFindAllForYearWithActiveMember_FullYear_TwoMembers_Count() {
        final Iterable<UserFeeCalendar> calendars;
        final Iterator<UserFeeCalendar> calendarsItr;
        final Sort                      sort;

        sort = Sort.unsorted();

        calendars = repository.findAllForYearWithActiveMember(2020, sort);

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
    void testFindAllForYearWithActiveMember_FullYear_TwoMembers_Data() {
        final Iterator<UserFeeCalendar> calendars;
        final Sort                      sort;
        UserFeeCalendar                 calendar;

        sort = Sort.unsorted();

        calendars = repository.findAllForYearWithActiveMember(2020, sort)
            .iterator();

        // First member
        calendar = calendars.next();
        UserFeeCalendarAssertions.isEqualTo(calendar, ImmutableUserFeeCalendar.builder()
            .memberId(1L)
            .name("Member 1 Surname 1")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(calendar);

        // Second member
        calendar = calendars.next();
        UserFeeCalendarAssertions.isEqualTo(calendar, ImmutableUserFeeCalendar.builder()
            .memberId(2L)
            .name("Member 2 Surname 2")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(calendar);
    }

}
