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

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.calendar.model.FeeMonth;
import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;
import com.bernardomg.association.fee.calendar.repository.FeeCalendarRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee calendar repository - find all for year")
public class ITFeeCalendarRepositoryFindAllForYear {

    @Autowired
    private FeeCalendarRepository repository;

    public ITFeeCalendarRepositoryFindAllForYear() {
        super();
    }

    @Test
    @DisplayName("With a full year it returns all the entities")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testFindAllForYear_FullYear_Count() {
        final Iterable<? extends UserFeeCalendar> result;
        final Sort                                sort;

        sort = Sort.unsorted();

        result = repository.findAllForYear(2020, sort);

        Assertions.assertEquals(1, IterableUtils.size(result));
        Assertions.assertEquals(12, IterableUtils.size(result.iterator()
            .next()
            .getMonths()));
    }

    @Test
    @DisplayName("With a full year it returns all the data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testFindAllForYear_FullYear_Data() {
        final Iterator<? extends UserFeeCalendar> data;
        final UserFeeCalendar                     result;
        final Iterator<FeeMonth>                  months;
        final Sort                                sort;
        FeeMonth                                  month;

        sort = Sort.unsorted();

        data = repository.findAllForYear(2020, sort)
            .iterator();

        result = data.next();
        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals("Member 1", result.getName());
        Assertions.assertEquals("Surname 1", result.getSurname());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertEquals(true, result.getActive());

        months = result.getMonths()
            .iterator();

        month = months.next();
        Assertions.assertEquals(1, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(2, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(3, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(4, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(5, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(6, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(7, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(8, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(9, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(10, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(11, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(12, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());
    }

    @Test
    @DisplayName("When there is no data it returns nothing")
    public void testFindAllForYear_NoData_Count() {
        final Iterable<? extends UserFeeCalendar> result;
        final Sort                                sort;

        sort = Sort.unsorted();

        result = repository.findAllForYear(2020, sort);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

    @Test
    @DisplayName("With a single month it returns all the entities")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/first_month.sql" })
    public void testFindAllForYear_SingleMonth_Count() {
        final Iterable<? extends UserFeeCalendar> result;
        final Sort                                sort;

        sort = Sort.unsorted();

        result = repository.findAllForYear(2020, sort);

        Assertions.assertEquals(1, IterableUtils.size(result));
        Assertions.assertEquals(1, IterableUtils.size(result.iterator()
            .next()
            .getMonths()));
    }

    @Test
    @DisplayName("With a single month it returns all the data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/first_month.sql" })
    public void testFindAllForYear_SingleMonth_Data() {
        final Iterator<? extends UserFeeCalendar> data;
        final UserFeeCalendar                     result;
        final Iterator<FeeMonth>                  months;
        final Sort                                sort;
        FeeMonth                                  month;

        sort = Sort.unsorted();

        data = repository.findAllForYear(2020, sort)
            .iterator();

        result = data.next();
        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals("Member 1", result.getName());
        Assertions.assertEquals("Surname 1", result.getSurname());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertEquals(true, result.getActive());

        months = result.getMonths()
            .iterator();

        month = months.next();
        Assertions.assertEquals(1, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());
    }

    @Test
    @DisplayName("With a single month it returns all the entities")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/first_month_unpaid.sql" })
    public void testFindAllForYear_SingleMonthUnpaid_Count() {
        final Iterable<? extends UserFeeCalendar> result;
        final Sort                                sort;

        sort = Sort.unsorted();

        result = repository.findAllForYear(2020, sort);

        Assertions.assertEquals(1, IterableUtils.size(result));
        Assertions.assertEquals(1, IterableUtils.size(result.iterator()
            .next()
            .getMonths()));
    }

    @Test
    @DisplayName("With a single month it returns all the data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/first_month_unpaid.sql" })
    public void testFindAllForYear_SingleMonthUnpaid_Data() {
        final Iterator<? extends UserFeeCalendar> data;
        final UserFeeCalendar                     result;
        final Iterator<FeeMonth>                  months;
        final Sort                                sort;
        FeeMonth                                  month;

        sort = Sort.unsorted();

        data = repository.findAllForYear(2020, sort)
            .iterator();

        result = data.next();
        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals("Member 1", result.getName());
        Assertions.assertEquals("Surname 1", result.getSurname());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertEquals(true, result.getActive());

        months = result.getMonths()
            .iterator();

        month = months.next();
        Assertions.assertEquals(1, month.getMonth());
        Assertions.assertEquals(false, month.getPaid());
    }

    @Test
    @DisplayName("With two connected years it returns all the entities for the first year")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    public void testFindAllForYear_TwoConnectedYears_First_Count() {
        final Iterable<? extends UserFeeCalendar> result;
        final Sort                                sort;

        sort = Sort.unsorted();

        result = repository.findAllForYear(2019, sort);

        Assertions.assertEquals(1, IterableUtils.size(result));
        Assertions.assertEquals(3, IterableUtils.size(result.iterator()
            .next()
            .getMonths()));
    }

    @Test
    @DisplayName("With two connected years it returns all the data")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    public void testFindAllForYear_TwoConnectedYears_First_Data() {
        final UserFeeCalendar result;
        final Sort            sort;
        Iterator<FeeMonth>    months;
        FeeMonth              month;

        sort = Sort.unsorted();

        result = repository.findAllForYear(2019, sort)
            .iterator()
            .next();

        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals("Member 1", result.getName());
        Assertions.assertEquals("Surname 1", result.getSurname());
        Assertions.assertEquals(2019, result.getYear());
        Assertions.assertEquals(true, result.getActive());

        months = result.getMonths()
            .iterator();

        month = months.next();
        Assertions.assertEquals(10, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(11, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(12, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());
    }

    @Test
    @DisplayName("With two connected years it returns all the entities for the second year")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    public void testFindAllForYear_TwoConnectedYears_Second_Count() {
        final Iterable<? extends UserFeeCalendar> result;
        final Sort                                sort;

        sort = Sort.unsorted();

        result = repository.findAllForYear(2020, sort);

        Assertions.assertEquals(1, IterableUtils.size(result));
        Assertions.assertEquals(7, IterableUtils.size(result.iterator()
            .next()
            .getMonths()));
    }

    @Test
    @DisplayName("With two connected years it returns all the data for the second year")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/two_years_connected.sql" })
    public void testFindAllForYear_TwoConnectedYears_Second_Data() {
        final UserFeeCalendar result;
        final Sort            sort;
        Iterator<FeeMonth>    months;
        FeeMonth              month;

        sort = Sort.unsorted();

        result = repository.findAllForYear(2020, sort)
            .iterator()
            .next();

        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals("Member 1", result.getName());
        Assertions.assertEquals("Surname 1", result.getSurname());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertEquals(true, result.getActive());

        months = result.getMonths()
            .iterator();

        month = months.next();
        Assertions.assertEquals(1, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(2, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(3, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(4, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(5, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(6, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());

        month = months.next();
        Assertions.assertEquals(7, month.getMonth());
        Assertions.assertEquals(true, month.getPaid());
    }

}
