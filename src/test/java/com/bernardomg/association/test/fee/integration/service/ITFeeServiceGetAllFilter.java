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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.DtoFeeRequest;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.service.FeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee service - get all - filter")
@Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
public class ITFeeServiceGetAllFilter {

    @Autowired
    private FeeService service;

    public ITFeeServiceGetAllFilter() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities after a date")
    public void testGetAll_AfterDate_Count() {
        final Iterable<? extends MemberFee> result;
        final DtoFeeRequest                 sample;
        final Pageable                      pageable;
        final Calendar                      date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeRequest();

        date = new GregorianCalendar(2020, 2, 1, 0, 0, 0);
        sample.setStartDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(4, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all the entities data after a date")
    public void testGetAll_AfterDate_Data() {
        final Iterator<? extends MemberFee> result;
        final DtoFeeRequest                 sample;
        final Pageable                      pageable;
        final Calendar                      date;
        MemberFee                           data;

        pageable = Pageable.unpaged();

        sample = new DtoFeeRequest();

        date = new GregorianCalendar(2020, 2, 1, 0, 0, 0);
        sample.setStartDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals(2, data.getMemberId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname 2", data.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 2, 1).toInstant(), data.getDate()
            .toInstant());
        Assertions.assertTrue(data.getPaid());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals(3, data.getMemberId());
        Assertions.assertEquals("Member 3", data.getName());
        Assertions.assertEquals("Surname 3", data.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 3, 1).toInstant(), data.getDate()
            .toInstant());
        Assertions.assertTrue(data.getPaid());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals(4, data.getMemberId());
        Assertions.assertEquals("Member 4", data.getName());
        Assertions.assertEquals("Surname 4", data.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 4, 1).toInstant(), data.getDate()
            .toInstant());
        Assertions.assertTrue(data.getPaid());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals(5, data.getMemberId());
        Assertions.assertEquals("Member 5", data.getName());
        Assertions.assertEquals("Surname 5", data.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 5, 1).toInstant(), data.getDate()
            .toInstant());
        Assertions.assertFalse(data.getPaid());
    }

    @Test
    @DisplayName("Returns all the entities before a date")
    public void testGetAll_BeforeDate_Count() {
        final Iterable<? extends MemberFee> result;
        final DtoFeeRequest                 sample;
        final Pageable                      pageable;
        final Calendar                      date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeRequest();

        date = new GregorianCalendar(2020, 2, 1, 0, 0, 0);
        sample.setEndDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(2, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all the entities data before a date")
    public void testGetAll_BeforeDate_Data() {
        final Iterator<? extends MemberFee> result;
        final DtoFeeRequest                 sample;
        final Pageable                      pageable;
        final Calendar                      date;
        MemberFee                           data;

        pageable = Pageable.unpaged();

        sample = new DtoFeeRequest();

        date = new GregorianCalendar(2020, 2, 1, 0, 0, 0);
        sample.setEndDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals(1, data.getMemberId());
        Assertions.assertEquals("Member 1", data.getName());
        Assertions.assertEquals("Surname 1", data.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), data.getDate()
            .toInstant());
        Assertions.assertTrue(data.getPaid());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals(2, data.getMemberId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname 2", data.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 2, 1).toInstant(), data.getDate()
            .toInstant());
        Assertions.assertTrue(data.getPaid());
    }

    @Test
    @DisplayName("Returns all the entities in a date")
    public void testGetAll_InDate_Count() {
        final Iterable<? extends MemberFee> result;
        final DtoFeeRequest                 sample;
        final Pageable                      pageable;
        final Calendar                      date;

        pageable = Pageable.unpaged();

        sample = new DtoFeeRequest();

        date = new GregorianCalendar(2020, 2, 1, 0, 0, 0);
        sample.setDate(date);

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all the entities data after a date")
    public void testGetAll_InDate_Data() {
        final Iterator<? extends MemberFee> result;
        final DtoFeeRequest                 sample;
        final Pageable                      pageable;
        final Calendar                      date;
        MemberFee                           data;

        pageable = Pageable.unpaged();

        sample = new DtoFeeRequest();

        date = new GregorianCalendar(2020, 2, 1, 0, 0, 0);
        sample.setDate(date);

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals(2, data.getMemberId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname 2", data.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 2, 1).toInstant(), data.getDate()
            .toInstant());
        Assertions.assertTrue(data.getPaid());
    }

}
