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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;
import com.bernardomg.association.fee.calendar.persistence.repository.FeeCalendarRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.calendar.assertion.CalendarAssertions;

@IntegrationTest
@DisplayName("Fee calendar repository - find all for year - two members")
@Sql({ "/db/queries/member/single.sql", "/db/queries/member/alternative.sql", "/db/queries/fee/full_year.sql",
        "/db/queries/fee/full_year_alternative.sql" })
public class ITFeeCalendarRepositoryFindAllForYearSort {

    @Autowired
    private FeeCalendarRepository repository;

    public ITFeeCalendarRepositoryFindAllForYearSort() {
        super();
    }

    @Test
    @DisplayName("Returns all data in ascending order by name")
    public void testFindAllForYear_Name_Asc() {
        final Iterator<UserFeeCalendar> data;
        final Sort                      sort;
        UserFeeCalendar                 result;

        sort = Sort.by(Order.asc("name"));

        data = repository.findAllForYear(2020, sort)
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

        CalendarAssertions.assertFullYear(result);

        result = data.next();
        Assertions.assertThat(result.getMemberId())
            .isEqualTo(2);
        Assertions.assertThat(result.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(result.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(result.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(result.getActive())
            .isTrue();

        CalendarAssertions.assertFullYear(result);
    }

    @Test
    @DisplayName("Returns all data in descending order by name")
    public void testFindAllForYear_Name_Desc() {
        final Iterator<UserFeeCalendar> data;
        final Sort                      sort;
        UserFeeCalendar                 result;

        sort = Sort.by(Order.desc("name"));

        data = repository.findAllForYear(2020, sort)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getMemberId())
            .isEqualTo(2);
        Assertions.assertThat(result.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(result.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(result.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(result.getActive())
            .isTrue();

        CalendarAssertions.assertFullYear(result);

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

        CalendarAssertions.assertFullYear(result);
    }

    @Test
    @DisplayName("Returns all data in ascending order by surname")
    public void testFindAllForYear_Surname_Asc() {
        final Iterator<UserFeeCalendar> data;
        UserFeeCalendar                 result;
        final Sort                      sort;

        sort = Sort.by(Order.asc("surname"));

        data = repository.findAllForYear(2020, sort)
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

        CalendarAssertions.assertFullYear(result);

        result = data.next();
        Assertions.assertThat(result.getMemberId())
            .isEqualTo(2);
        Assertions.assertThat(result.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(result.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(result.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(result.getActive())
            .isTrue();

        CalendarAssertions.assertFullYear(result);
    }

    @Test
    @DisplayName("Returns all data in descending order by surname")
    public void testFindAllForYear_Surname_Desc() {
        final Iterator<UserFeeCalendar> data;
        UserFeeCalendar                 result;
        final Sort                      sort;

        sort = Sort.by(Order.desc("surname"));

        data = repository.findAllForYear(2020, sort)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getMemberId())
            .isEqualTo(2);
        Assertions.assertThat(result.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(result.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(result.getYear())
            .isEqualTo(2020);
        Assertions.assertThat(result.getActive())
            .isTrue();

        CalendarAssertions.assertFullYear(result);

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

        CalendarAssertions.assertFullYear(result);
    }

}
