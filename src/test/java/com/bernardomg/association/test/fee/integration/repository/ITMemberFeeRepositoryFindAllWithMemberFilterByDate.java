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

package com.bernardomg.association.test.fee.integration.repository;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.DtoFeeRequest;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.repository.MemberFeeRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee repository - find all with member - filter by date")
@Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
public class ITMemberFeeRepositoryFindAllWithMemberFilterByDate {

    @Autowired
    private MemberFeeRepository repository;

    public ITMemberFeeRepositoryFindAllWithMemberFilterByDate() {
        super();
    }

    @Test
    @DisplayName("Filters by date and returns all the entities")
    public void testFindAllWithMember_ByDate_Count() {
        final Iterable<? extends MemberFee> result;
        final DtoFeeRequest                 example;
        final Pageable                      pageable;
        final Calendar                      date;

        pageable = Pageable.unpaged();

        date = new GregorianCalendar(2020, 2, 1);

        example = new DtoFeeRequest();
        example.setDate(date);

        result = repository.findAllWithMember(example, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Filters by date and returns all the data")
    public void testFindAllWithMember_ByDate_Data() {
        final Iterator<? extends MemberFee> result;
        final DtoFeeRequest                 example;
        final Pageable                      pageable;
        final Calendar                      date;
        final MemberFee                     data;

        pageable = Pageable.unpaged();

        date = new GregorianCalendar(2020, 2, 1);

        example = new DtoFeeRequest();
        example.setDate(date);

        result = repository.findAllWithMember(example, pageable)
            .iterator();

        data = result.next();

        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals(2, data.getMemberId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname 2", data.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 2, 1).getTime(), data.getDate()
            .getTime());
        Assertions.assertTrue(data.getPaid());
    }

    @Test
    @DisplayName("When filtering by a not existing date nothing is returned")
    public void testFindAllWithMember_ByDate_NotExisting() {
        final Iterable<? extends MemberFee> result;
        final DtoFeeRequest                 example;
        final Pageable                      pageable;
        final Calendar                      date;

        pageable = Pageable.unpaged();

        date = new GregorianCalendar(2020, 10, 1);

        example = new DtoFeeRequest();
        example.setDate(date);

        result = repository.findAllWithMember(example, pageable);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Filters by end date and returns all the entities")
    public void testFindAllWithMember_ByEndDate_Count() {
        final Iterable<? extends MemberFee> result;
        final DtoFeeRequest                 example;
        final Pageable                      pageable;
        final Calendar                      end;

        pageable = Pageable.unpaged();

        end = new GregorianCalendar(2020, 1, 1);

        example = new DtoFeeRequest();
        example.setEndDate(end);

        result = repository.findAllWithMember(example, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Filters by end date and returns all the data")
    public void testFindAllWithMember_ByEndDate_Data() {
        final Iterator<MemberFee> result;
        final DtoFeeRequest       example;
        final Pageable            pageable;
        final Calendar            end;
        final MemberFee           data;

        pageable = Pageable.unpaged();

        end = new GregorianCalendar(2020, 1, 1);

        example = new DtoFeeRequest();
        example.setEndDate(end);

        result = repository.findAllWithMember(example, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals(1, data.getMemberId());
        Assertions.assertEquals("Member 1", data.getName());
        Assertions.assertEquals("Surname 1", data.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).getTime(), data.getDate()
            .getTime());
        Assertions.assertTrue(data.getPaid());
    }

    @Test
    @DisplayName("When filtering by a end date which includes no fee nothing is returned")
    public void testFindAllWithMember_ByEndDate_NotInRage() {
        final Iterable<? extends MemberFee> result;
        final DtoFeeRequest                 example;
        final Pageable                      pageable;
        final Calendar                      end;

        pageable = Pageable.unpaged();

        end = new GregorianCalendar(2020, 0, 1);

        example = new DtoFeeRequest();
        example.setEndDate(end);

        result = repository.findAllWithMember(example, pageable);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Filters by range")
    public void testFindAllWithMember_ByRange() {
        final Iterable<? extends MemberFee> result;
        final Iterator<? extends MemberFee> data;
        final DtoFeeRequest                 example;
        final Pageable                      pageable;
        final Calendar                      start;
        final Calendar                      end;

        pageable = Pageable.unpaged();

        start = new GregorianCalendar(2020, 0, 1);
        end = new GregorianCalendar(2020, 2, 1);

        example = new DtoFeeRequest();
        example.setStartDate(start);
        example.setEndDate(end);

        result = repository.findAllWithMember(example, pageable);
        data = result.iterator();

        Assertions.assertEquals(2, IterableUtils.size(result));

        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1), data.next()
            .getDate());
        Assertions.assertEquals(new GregorianCalendar(2020, 2, 1), data.next()
            .getDate());
    }

    @Test
    @DisplayName("When filtering by a range which includes no fee nothing is returned")
    public void testFindAllWithMember_ByRange_NotInRange() {
        final Iterable<? extends MemberFee> result;
        final DtoFeeRequest                 example;
        final Pageable                      pageable;
        final Calendar                      start;
        final Calendar                      end;

        pageable = Pageable.unpaged();

        start = new GregorianCalendar(2020, 7, 1);
        end = new GregorianCalendar(2020, 10, 1);

        example = new DtoFeeRequest();
        example.setStartDate(start);
        example.setEndDate(end);

        result = repository.findAllWithMember(example, pageable);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Filters by start date and returns all the entities")
    public void testFindAllWithMember_ByStartDate_Count() {
        final Iterable<? extends MemberFee> result;
        final DtoFeeRequest                 example;
        final Pageable                      pageable;
        final Calendar                      start;

        pageable = Pageable.unpaged();

        start = new GregorianCalendar(2020, 5, 1);

        example = new DtoFeeRequest();
        example.setStartDate(start);

        result = repository.findAllWithMember(example, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Filters by start date and returns all the data")
    public void testFindAllWithMember_ByStartDate_Data() {
        final Iterator<? extends MemberFee> result;
        final DtoFeeRequest                 example;
        final Pageable                      pageable;
        final Calendar                      start;
        final MemberFee                     data;

        pageable = Pageable.unpaged();

        start = new GregorianCalendar(2020, 5, 1);

        example = new DtoFeeRequest();
        example.setStartDate(start);

        result = repository.findAllWithMember(example, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals(5, data.getMemberId());
        Assertions.assertEquals("Member 5", data.getName());
        Assertions.assertEquals("Surname 5", data.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 5, 1).getTime(), data.getDate()
            .getTime());
        Assertions.assertFalse(data.getPaid());
    }

    @Test
    @DisplayName("When filtering by a start date which includes no fee nothing is returned")
    public void testFindAllWithMember_ByStartDate_NotInRange() {
        final Iterable<? extends MemberFee> result;
        final DtoFeeRequest                 example;
        final Pageable                      pageable;
        final Calendar                      start;

        pageable = Pageable.unpaged();

        start = new GregorianCalendar(2020, 6, 1);

        example = new DtoFeeRequest();
        example.setStartDate(start);

        result = repository.findAllWithMember(example, pageable);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

}
