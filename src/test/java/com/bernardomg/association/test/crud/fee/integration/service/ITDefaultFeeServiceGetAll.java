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

package com.bernardomg.association.test.crud.fee.integration.service;

import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.crud.fee.model.DtoMemberFee;
import com.bernardomg.association.crud.fee.model.MemberFee;
import com.bernardomg.association.crud.fee.service.DefaultFeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default fee service - get all")
@Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
public class ITDefaultFeeServiceGetAll {

    @Autowired
    private DefaultFeeService service;

    public ITDefaultFeeServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<? extends MemberFee> result;
        final DtoMemberFee                  sample;
        final Pageable                      pageable;

        pageable = Pageable.unpaged();

        sample = new DtoMemberFee();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(5, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data")
    public void testGetAll_Data() {
        final Iterator<? extends MemberFee> data;
        final DtoMemberFee                  sample;
        MemberFee                           result;
        final Pageable                      pageable;

        pageable = Pageable.unpaged();

        sample = new DtoMemberFee();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals("Member 1", result.getName());
        Assertions.assertEquals("Surname 1", result.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(2, result.getMemberId());
        Assertions.assertEquals("Member 2", result.getName());
        Assertions.assertEquals("Surname 2", result.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 2, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(3, result.getMemberId());
        Assertions.assertEquals("Member 3", result.getName());
        Assertions.assertEquals("Surname 3", result.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 3, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(4, result.getMemberId());
        Assertions.assertEquals("Member 4", result.getName());
        Assertions.assertEquals("Surname 4", result.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 4, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(5, result.getMemberId());
        Assertions.assertEquals("Member 5", result.getName());
        Assertions.assertEquals("Surname 5", result.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 5, 1).toInstant(), result.getDate()
            .toInstant());
        Assertions.assertFalse(result.getPaid());
    }

    @Test
    @DisplayName("Returns a page")
    public void testGetAll_Page() {
        final Iterable<? extends MemberFee> result;
        final MemberFee                     sample;
        final Pageable                      pageable;

        pageable = Pageable.ofSize(10);

        sample = new DtoMemberFee();

        result = service.getAll(sample, pageable);

        Assertions.assertInstanceOf(Page.class, result);
    }

}
