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

package com.bernardomg.association.test.fee.calendar.integration.repository;

import java.util.Iterator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.calendar.model.ImmutableUserFeeCalendar;
import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;
import com.bernardomg.association.fee.calendar.persistence.repository.FeeCalendarRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.calendar.assertion.UserFeeCalendarAssertions;

@IntegrationTest
@DisplayName("Fee calendar repository - find all for year with active member - sorted")
public class ITFeeCalendarRepositoryFindAllForYearWithActiveMemberSort {

    @Autowired
    private FeeCalendarRepository repository;

    public ITFeeCalendarRepositoryFindAllForYearWithActiveMemberSort() {
        super();
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testFindAllForYearWithActiveMember_Name_Asc() {
        final Iterator<UserFeeCalendar> data;
        final Sort                      sort;
        UserFeeCalendar                 result;

        sort = Sort.by(Order.asc("name"));

        data = repository.findAllForYearWithActiveMember(2020, sort)
            .iterator();

        result = data.next();
        UserFeeCalendarAssertions.isEqualTo(result, ImmutableUserFeeCalendar.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(result);
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testFindAllForYearWithActiveMember_Name_Desc() {
        final Iterator<UserFeeCalendar> data;
        final Sort                      sort;
        UserFeeCalendar                 result;

        sort = Sort.by(Order.desc("name"));

        data = repository.findAllForYearWithActiveMember(2020, sort)
            .iterator();

        result = data.next();
        UserFeeCalendarAssertions.isEqualTo(result, ImmutableUserFeeCalendar.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(result);
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql", "/db/queries/fee/full_year.sql",
            "/db/queries/fee/full_year_alternative.sql" })
    public void testFindAllForYearWithActiveMember_TwoMembers_Name_Asc() {
        final Iterator<UserFeeCalendar> data;
        final Sort                      sort;
        UserFeeCalendar                 result;

        sort = Sort.by(Order.asc("name"));

        data = repository.findAllForYearWithActiveMember(2020, sort)
            .iterator();

        result = data.next();
        UserFeeCalendarAssertions.isEqualTo(result, ImmutableUserFeeCalendar.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(result);

        result = data.next();
        UserFeeCalendarAssertions.isEqualTo(result, ImmutableUserFeeCalendar.builder()
            .memberId(2L)
            .name("Member 2")
            .surname("Surname 2")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(result);
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql", "/db/queries/fee/full_year.sql",
            "/db/queries/fee/full_year_alternative.sql" })
    public void testFindAllForYearWithActiveMember_TwoMembers_Name_Desc() {
        final Iterator<UserFeeCalendar> data;
        final Sort                      sort;
        UserFeeCalendar                 result;

        sort = Sort.by(Order.desc("name"));

        data = repository.findAllForYearWithActiveMember(2020, sort)
            .iterator();

        result = data.next();
        UserFeeCalendarAssertions.isEqualTo(result, ImmutableUserFeeCalendar.builder()
            .memberId(2L)
            .name("Member 2")
            .surname("Surname 2")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(result);

        result = data.next();
        UserFeeCalendarAssertions.isEqualTo(result, ImmutableUserFeeCalendar.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(result);
    }

    @Test
    @DisplayName("With ascending order by surname it returns the ordered data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql", "/db/queries/fee/full_year.sql",
            "/db/queries/fee/full_year_alternative.sql" })
    public void testFindAllForYearWithActiveMember_TwoMembers_Surname_Asc() {
        final Iterator<UserFeeCalendar> data;
        UserFeeCalendar                 result;
        final Sort                      sort;

        sort = Sort.by(Order.asc("surname"));

        data = repository.findAllForYearWithActiveMember(2020, sort)
            .iterator();

        result = data.next();
        UserFeeCalendarAssertions.isEqualTo(result, ImmutableUserFeeCalendar.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(result);

        result = data.next();
        UserFeeCalendarAssertions.isEqualTo(result, ImmutableUserFeeCalendar.builder()
            .memberId(2L)
            .name("Member 2")
            .surname("Surname 2")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(result);
    }

    @Test
    @DisplayName("With descending order by surname it returns the ordered data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql", "/db/queries/fee/full_year.sql",
            "/db/queries/fee/full_year_alternative.sql" })
    public void testFindAllForYearWithActiveMember_TwoMembers_Surname_Desc() {
        final Iterator<UserFeeCalendar> data;
        UserFeeCalendar                 result;
        final Sort                      sort;

        sort = Sort.by(Order.desc("surname"));

        data = repository.findAllForYearWithActiveMember(2020, sort)
            .iterator();

        result = data.next();
        UserFeeCalendarAssertions.isEqualTo(result, ImmutableUserFeeCalendar.builder()
            .memberId(2L)
            .name("Member 2")
            .surname("Surname 2")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(result);

        result = data.next();
        UserFeeCalendarAssertions.isEqualTo(result, ImmutableUserFeeCalendar.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .year(2020)
            .active(true)
            .build());

        UserFeeCalendarAssertions.assertFullYear(result);
    }

}
