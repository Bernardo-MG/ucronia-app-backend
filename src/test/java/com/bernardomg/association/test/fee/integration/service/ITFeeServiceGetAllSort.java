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

import java.util.GregorianCalendar;
import java.util.Iterator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.ImmutableMemberFee;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.request.DtoFeeQueryRequest;
import com.bernardomg.association.fee.model.request.FeeQueryRequest;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.fee.assertion.FeeAssertions;

@IntegrationTest
@DisplayName("Fee service - get all - sort")
@Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
public class ITFeeServiceGetAllSort {

    @Autowired
    private FeeService service;

    public ITFeeServiceGetAllSort() {
        super();
    }

    @Test
    @DisplayName("Returns all data in ascending order by date")
    public void testGetAll_Date_Asc() {
        final Iterator<MemberFee> data;
        final FeeQueryRequest     sample;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "date");

        sample = new DtoFeeQueryRequest();

        data = service.getAll(sample, pageable)
            .iterator();

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(2L)
            .name("Member 2")
            .surname("Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(3L)
            .name("Member 3")
            .surname("Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(4L)
            .name("Member 4")
            .surname("Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(5L)
            .name("Member 5")
            .surname("Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());
    }

    @Test
    @DisplayName("Returns all data in descending order by date")
    public void testGetAll_Date_Desc() {
        final Iterator<MemberFee> data;
        final FeeQueryRequest     sample;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "date");

        sample = new DtoFeeQueryRequest();

        data = service.getAll(sample, pageable)
            .iterator();

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(5L)
            .name("Member 5")
            .surname("Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(4L)
            .name("Member 4")
            .surname("Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(3L)
            .name("Member 3")
            .surname("Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(2L)
            .name("Member 2")
            .surname("Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("Returns all data in ascending order by name")
    public void testGetAll_Name_Asc() {
        final Iterator<MemberFee> data;
        final FeeQueryRequest     sample;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "name");

        sample = new DtoFeeQueryRequest();

        data = service.getAll(sample, pageable)
            .iterator();

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(2L)
            .name("Member 2")
            .surname("Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(3L)
            .name("Member 3")
            .surname("Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(4L)
            .name("Member 4")
            .surname("Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(5L)
            .name("Member 5")
            .surname("Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());
    }

    @Test
    @DisplayName("Returns all data in descending order by name")
    public void testGetAll_Name_Desc() {
        final Iterator<MemberFee> data;
        final FeeQueryRequest     sample;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "name");

        sample = new DtoFeeQueryRequest();

        data = service.getAll(sample, pageable)
            .iterator();

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(5L)
            .name("Member 5")
            .surname("Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(4L)
            .name("Member 4")
            .surname("Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(3L)
            .name("Member 3")
            .surname("Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(2L)
            .name("Member 2")
            .surname("Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("Returns all data in ascending order by paid flag")
    public void testGetAll_Paid_Asc() {
        final Iterator<MemberFee> data;
        final FeeQueryRequest     sample;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "paid");

        sample = new DtoFeeQueryRequest();

        data = service.getAll(sample, pageable)
            .iterator();

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(5L)
            .name("Member 5")
            .surname("Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(2L)
            .name("Member 2")
            .surname("Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(3L)
            .name("Member 3")
            .surname("Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(4L)
            .name("Member 4")
            .surname("Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());
    }

    @Test
    @DisplayName("Returns all data in descending order by paid flag")
    public void testGetAll_Paid_Desc() {
        final Iterator<MemberFee> data;
        final FeeQueryRequest     sample;
        final Pageable            pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "paid");

        sample = new DtoFeeQueryRequest();

        data = service.getAll(sample, pageable)
            .iterator();

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(1L)
            .name("Member 1")
            .surname("Surname 1")
            .date(new GregorianCalendar(2020, 1, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(2L)
            .name("Member 2")
            .surname("Surname 2")
            .date(new GregorianCalendar(2020, 2, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(3L)
            .name("Member 3")
            .surname("Surname 3")
            .date(new GregorianCalendar(2020, 3, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(4L)
            .name("Member 4")
            .surname("Surname 4")
            .date(new GregorianCalendar(2020, 4, 1))
            .paid(true)
            .build());

        FeeAssertions.isEqualTo(data.next(), ImmutableMemberFee.builder()
            .id(5L)
            .name("Member 5")
            .surname("Surname 5")
            .date(new GregorianCalendar(2020, 5, 1))
            .paid(false)
            .build());
    }

}
