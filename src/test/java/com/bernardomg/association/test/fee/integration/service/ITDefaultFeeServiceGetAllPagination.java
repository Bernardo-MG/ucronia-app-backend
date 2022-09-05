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

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.DtoFee;
import com.bernardomg.association.fee.model.Fee;
import com.bernardomg.association.fee.service.DefaultFeeService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default fee service - get all - pagination")
@Sql({ "/db/queries/member/multiple.sql", "/db/queries/fee/multiple.sql" })
public class ITDefaultFeeServiceGetAllPagination {

    @Autowired
    private DefaultFeeService service;

    public ITDefaultFeeServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final DtoFee                  sample;
        final Iterator<? extends Fee> data;
        final Fee                     result;
        final Pageable                pageable;

        pageable = PageRequest.of(0, 1);

        sample = new DtoFee();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals("Member 1 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetAll_Page2_Data() {
        final DtoFee                  sample;
        final Iterator<? extends Fee> data;
        final Fee                     result;
        final Pageable                pageable;

        pageable = PageRequest.of(1, 1);

        sample = new DtoFee();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(2, result.getMemberId());
        Assertions.assertEquals("Member 2 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetAll_Paged_Count() {
        final Iterable<? extends Fee> result;
        final DtoFee                  sample;
        final Pageable                pageable;

        pageable = PageRequest.of(0, 1);

        sample = new DtoFee();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data in ascending order")
    public void testGetAll_Sorted_Asc_Data() {
        final Iterator<? extends Fee> data;
        final DtoFee                  sample;
        Fee                           result;
        final Pageable                pageable;

        pageable = PageRequest.of(0, 1, Direction.ASC, "id");

        sample = new DtoFee();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals("Member 1 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(2, result.getMemberId());
        Assertions.assertEquals("Member 2 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(3, result.getMemberId());
        Assertions.assertEquals("Member 3 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(4, result.getMemberId());
        Assertions.assertEquals("Member 4 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(5, result.getMemberId());
        Assertions.assertEquals("Member 5 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());
    }

    @Test
    @DisplayName("Returns all data in descending order")
    public void testGetAll_Sorted_Desc_Data() {
        final Iterator<? extends Fee> data;
        final DtoFee                  sample;
        Fee                           result;
        final Pageable                pageable;

        pageable = PageRequest.of(0, 1, Direction.DESC, "id");

        sample = new DtoFee();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(5, result.getMemberId());
        Assertions.assertEquals("Member 5 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(4, result.getMemberId());
        Assertions.assertEquals("Member 4 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(3, result.getMemberId());
        Assertions.assertEquals("Member 3 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(2, result.getMemberId());
        Assertions.assertEquals("Member 2 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(1, result.getMemberId());
        Assertions.assertEquals("Member 1 Surname", result.getMember());
        Assertions.assertEquals(2, result.getMonth());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertTrue(result.getPaid());
    }

}
