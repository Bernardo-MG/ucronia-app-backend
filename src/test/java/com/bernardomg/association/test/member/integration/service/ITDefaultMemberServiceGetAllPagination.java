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

package com.bernardomg.association.test.member.integration.service;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.DtoMember;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.service.DefaultMemberService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.mvc.pagination.model.Direction;
import com.bernardomg.mvc.pagination.model.Pagination;
import com.bernardomg.mvc.pagination.model.Sort;

@IntegrationTest
@DisplayName("Default member service - get all - pagination")
@Sql({ "/db/queries/member/multiple.sql" })
public class ITDefaultMemberServiceGetAllPagination {

    @Autowired
    private DefaultMemberService service;

    public ITDefaultMemberServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final Member                     sample;
        final Pagination                 pagination;
        final Sort                       sort;
        final Iterator<? extends Member> data;
        final Member                     result;

        pagination = Pagination.of(0, 1);
        sort = Sort.disabled();

        sample = new DtoMember();

        data = service.getAll(sample, pagination, sort)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Member 1", result.getName());
        Assertions.assertEquals("Surname", result.getSurname());
        Assertions.assertEquals("12345", result.getPhone());
        Assertions.assertEquals("6789", result.getIdentifier());
        Assertions.assertEquals(true, result.getActive());
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetAll_Page2_Data() {
        final Member                     sample;
        final Pagination                 pagination;
        final Sort                       sort;
        final Iterator<? extends Member> data;
        final Member                     result;

        pagination = Pagination.of(1, 1);
        sort = Sort.disabled();

        sample = new DtoMember();

        data = service.getAll(sample, pagination, sort)
            .iterator();

        result = data.next();
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Member 2", result.getName());
        Assertions.assertEquals("Surname", result.getSurname());
        Assertions.assertEquals("12346", result.getPhone());
        Assertions.assertEquals("6780", result.getIdentifier());
        Assertions.assertEquals(true, result.getActive());
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetAll_Paged_Count() {
        final Iterable<? extends Member> result;
        final DtoMember                  sample;
        final Pagination                 pagination;
        final Sort                       sort;

        pagination = Pagination.of(0, 1);
        sort = Sort.disabled();

        sample = new DtoMember();

        result = service.getAll(sample, pagination, sort);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all data in ascending order")
    public void testGetAll_Sorted_Asc_Data() {
        final Iterator<? extends Member> result;
        final Member                     sample;
        Member                           data;
        final Pagination                 pagination;
        final Sort                       sort;

        pagination = Pagination.disabled();
        sort = Sort.of("id", Direction.ASC);

        sample = new DtoMember();

        result = service.getAll(sample, pagination, sort)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 1", data.getName());
        Assertions.assertEquals("Surname", data.getSurname());
        Assertions.assertEquals("12345", data.getPhone());
        Assertions.assertEquals("6789", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname", data.getSurname());
        Assertions.assertEquals("12346", data.getPhone());
        Assertions.assertEquals("6780", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 3", data.getName());
        Assertions.assertEquals("Surname", data.getSurname());
        Assertions.assertEquals("12347", data.getPhone());
        Assertions.assertEquals("6781", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 4", data.getName());
        Assertions.assertEquals("Surname", data.getSurname());
        Assertions.assertEquals("12348", data.getPhone());
        Assertions.assertEquals("6782", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 5", data.getName());
        Assertions.assertEquals("Surname", data.getSurname());
        Assertions.assertEquals("12349", data.getPhone());
        Assertions.assertEquals("6783", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());
    }

    @Test
    @DisplayName("Returns all data in descending order")
    public void testGetAll_Sorted_Desc_Data() {
        final Iterator<? extends Member> result;
        final Member                     sample;
        Member                           data;
        final Pagination                 pagination;
        final Sort                       sort;

        pagination = Pagination.disabled();
        sort = Sort.of("id", Direction.DESC);

        sample = new DtoMember();

        result = service.getAll(sample, pagination, sort)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 5", data.getName());
        Assertions.assertEquals("Surname", data.getSurname());
        Assertions.assertEquals("12349", data.getPhone());
        Assertions.assertEquals("6783", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 4", data.getName());
        Assertions.assertEquals("Surname", data.getSurname());
        Assertions.assertEquals("12348", data.getPhone());
        Assertions.assertEquals("6782", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 3", data.getName());
        Assertions.assertEquals("Surname", data.getSurname());
        Assertions.assertEquals("12347", data.getPhone());
        Assertions.assertEquals("6781", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname", data.getSurname());
        Assertions.assertEquals("12346", data.getPhone());
        Assertions.assertEquals("6780", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 1", data.getName());
        Assertions.assertEquals("Surname", data.getSurname());
        Assertions.assertEquals("12345", data.getPhone());
        Assertions.assertEquals("6789", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());
    }

}
