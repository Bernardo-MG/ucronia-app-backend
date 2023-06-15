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

package com.bernardomg.association.test.fee.integration.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.ImmutableMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.DtoFeeQueryRequest;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.assertion.FeeAssertions;

@IntegrationTest
@DisplayName("Fee service - get all - filter")
public class ITFeeServiceGetAllFilter {

    @Autowired
    private FeeService service;

    public ITFeeServiceGetAllFilter() {
        super();
    }

    @Test
    @DisplayName("Filters by end date and returns all the entities")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_EndDate_Count() {
        final Iterable<MemberFee> result;
        final DtoFeeQueryRequest  sample;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        date = new GregorianCalendar(2020, 1, 1);
        sample.setEndDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Filters by end date and returns all the data")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_EndDate_Data() {
        final Iterator<MemberFee> result;
        final DtoFeeQueryRequest  sample;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        date = new GregorianCalendar(2020, 1, 1);
        sample.setEndDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        FeeAssertions.isEqualTo(result.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("When filtering by a end date which includes no fee nothing is returned")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_EndDate_NotInRange() {
        final Iterable<MemberFee> result;
        final DtoFeeQueryRequest  sample;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        date = new GregorianCalendar(2020, 0, 1);
        sample.setEndDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(0);
    }

    @Test
    @DisplayName("Filters by date and returns all the entities")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_InDate_Count() {
        final Iterable<MemberFee> result;
        final DtoFeeQueryRequest  sample;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        date = new GregorianCalendar(2020, 2, 1);
        sample.setDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Filters by date and returns all the data")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_InDate_Data() {
        final Iterator<MemberFee> result;
        final DtoFeeQueryRequest  sample;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        date = new GregorianCalendar(2020, 2, 1);
        sample.setDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        FeeAssertions.isEqualTo(result.next(), ImmutableMemberFee.builder()
            .memberId(2L)
            .name("Member 2")
            .surname("Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("When filtering by the first day of the year only that day is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testGetAll_InDate_FirstDay_Data() {
        final Iterable<MemberFee> read;
        final Iterator<MemberFee> result;
        final DtoFeeQueryRequest  sample;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        date = new GregorianCalendar(2020, 0, 1);
        sample.setDate(date);

        read = service.getAll(sample, pageable);
        result = read.iterator();

        Assertions.assertThat(IterableUtils.size(read))
            .isEqualTo(1);

        FeeAssertions.isEqualTo(result.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 0, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("When filtering by the last day of the year only that day is returned")
    @Sql({ "/db/queries/member/single.sql", "/db/queries/fee/full_year.sql" })
    public void testGetAll_InDate_LastDay_Data() {
        final Iterable<MemberFee> read;
        final Iterator<MemberFee> result;
        final DtoFeeQueryRequest  sample;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        date = new GregorianCalendar(2020, 11, 1);
        sample.setDate(date);

        read = service.getAll(sample, pageable);
        result = read.iterator();

        Assertions.assertThat(IterableUtils.size(read))
            .isEqualTo(1);

        FeeAssertions.isEqualTo(result.next(), ImmutableMemberFee.builder()
            .memberId(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 11, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("When filtering by a not existing date nothing is returned")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_InDate_NotExisting() {
        final Iterable<MemberFee> result;
        final DtoFeeQueryRequest  sample;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        date = new GregorianCalendar(2020, 10, 1);
        sample.setDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(0);
    }

    @Test
    @DisplayName("Filters by start date and returns all the entities")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_StartDate_Count() {
        final Iterable<MemberFee> result;
        final DtoFeeQueryRequest  sample;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        date = new GregorianCalendar(2020, 5, 1);
        sample.setStartDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Filters by start date and returns all the data")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_StartDate_Data() {
        final Iterator<MemberFee> result;
        final DtoFeeQueryRequest  sample;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        date = new GregorianCalendar(2020, 5, 1);
        sample.setStartDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        FeeAssertions.isEqualTo(result.next(), ImmutableMemberFee.builder()
            .memberId(5L)
            .name("Member 5")
            .surname("Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());
    }

    @Test
    @DisplayName("When filtering by a start date which includes no fee nothing is returned")
    @Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
    public void testGetAll_StartDate_NotInRange() {
        final Iterable<MemberFee> result;
        final DtoFeeQueryRequest  sample;
        final Pageable            pageable;
        final Calendar            date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeQueryRequest();

        date = new GregorianCalendar(2020, 6, 1);
        sample.setStartDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isEqualTo(0);
    }

}
