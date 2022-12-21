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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.DtoMember;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - get all - sort")
@Sql({ "/db/queries/member/multiple.sql" })
public class ITMemberServiceGetAllSort {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetAllSort() {
        super();
    }

    @Test
    @DisplayName("Returns all data in ascending order by active flag")
    public void testGetAll_Active_Asc() {
        final Iterator<? extends Member> result;
        final Member                     sample;
        Member                           data;
        final Pageable                   pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "active");

        sample = new DtoMember();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 5", data.getName());
        Assertions.assertEquals("Surname 5", data.getSurname());
        Assertions.assertEquals("12349", data.getPhone());
        Assertions.assertEquals("6783", data.getIdentifier());
        Assertions.assertEquals(false, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 1", data.getName());
        Assertions.assertEquals("Surname 1", data.getSurname());
        Assertions.assertEquals("12345", data.getPhone());
        Assertions.assertEquals("6789", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname 2", data.getSurname());
        Assertions.assertEquals("12346", data.getPhone());
        Assertions.assertEquals("6780", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 3", data.getName());
        Assertions.assertEquals("Surname 3", data.getSurname());
        Assertions.assertEquals("12347", data.getPhone());
        Assertions.assertEquals("6781", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 4", data.getName());
        Assertions.assertEquals("Surname 4", data.getSurname());
        Assertions.assertEquals("12348", data.getPhone());
        Assertions.assertEquals("6782", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());
    }

    @Test
    @DisplayName("Returns all data in descending order by active flag")
    public void testGetAll_Active_Desc() {
        final Iterator<? extends Member> result;
        final Member                     sample;
        Member                           data;
        final Pageable                   pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "active");

        sample = new DtoMember();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 1", data.getName());
        Assertions.assertEquals("Surname 1", data.getSurname());
        Assertions.assertEquals("12345", data.getPhone());
        Assertions.assertEquals("6789", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname 2", data.getSurname());
        Assertions.assertEquals("12346", data.getPhone());
        Assertions.assertEquals("6780", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 3", data.getName());
        Assertions.assertEquals("Surname 3", data.getSurname());
        Assertions.assertEquals("12347", data.getPhone());
        Assertions.assertEquals("6781", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 4", data.getName());
        Assertions.assertEquals("Surname 4", data.getSurname());
        Assertions.assertEquals("12348", data.getPhone());
        Assertions.assertEquals("6782", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 5", data.getName());
        Assertions.assertEquals("Surname 5", data.getSurname());
        Assertions.assertEquals("12349", data.getPhone());
        Assertions.assertEquals("6783", data.getIdentifier());
        Assertions.assertEquals(false, data.getActive());
    }

    @Test
    @DisplayName("Returns all data in ascending order by name")
    public void testGetAll_Name_Asc() {
        final Iterator<? extends Member> result;
        final Member                     sample;
        Member                           data;
        final Pageable                   pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "name");

        sample = new DtoMember();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 1", data.getName());
        Assertions.assertEquals("Surname 1", data.getSurname());
        Assertions.assertEquals("12345", data.getPhone());
        Assertions.assertEquals("6789", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname 2", data.getSurname());
        Assertions.assertEquals("12346", data.getPhone());
        Assertions.assertEquals("6780", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 3", data.getName());
        Assertions.assertEquals("Surname 3", data.getSurname());
        Assertions.assertEquals("12347", data.getPhone());
        Assertions.assertEquals("6781", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 4", data.getName());
        Assertions.assertEquals("Surname 4", data.getSurname());
        Assertions.assertEquals("12348", data.getPhone());
        Assertions.assertEquals("6782", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 5", data.getName());
        Assertions.assertEquals("Surname 5", data.getSurname());
        Assertions.assertEquals("12349", data.getPhone());
        Assertions.assertEquals("6783", data.getIdentifier());
        Assertions.assertEquals(false, data.getActive());
    }

    @Test
    @DisplayName("Returns all data in descending order by name")
    public void testGetAll_Name_Desc() {
        final Iterator<? extends Member> result;
        final Member                     sample;
        Member                           data;
        final Pageable                   pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "name");

        sample = new DtoMember();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 5", data.getName());
        Assertions.assertEquals("Surname 5", data.getSurname());
        Assertions.assertEquals("12349", data.getPhone());
        Assertions.assertEquals("6783", data.getIdentifier());
        Assertions.assertEquals(false, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 4", data.getName());
        Assertions.assertEquals("Surname 4", data.getSurname());
        Assertions.assertEquals("12348", data.getPhone());
        Assertions.assertEquals("6782", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 3", data.getName());
        Assertions.assertEquals("Surname 3", data.getSurname());
        Assertions.assertEquals("12347", data.getPhone());
        Assertions.assertEquals("6781", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname 2", data.getSurname());
        Assertions.assertEquals("12346", data.getPhone());
        Assertions.assertEquals("6780", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 1", data.getName());
        Assertions.assertEquals("Surname 1", data.getSurname());
        Assertions.assertEquals("12345", data.getPhone());
        Assertions.assertEquals("6789", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());
    }

    @Test
    @DisplayName("Returns all data in ascending order by surname")
    public void testGetAll_Surname_Asc() {
        final Iterator<? extends Member> result;
        final Member                     sample;
        Member                           data;
        final Pageable                   pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "surname");

        sample = new DtoMember();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 1", data.getName());
        Assertions.assertEquals("Surname 1", data.getSurname());
        Assertions.assertEquals("12345", data.getPhone());
        Assertions.assertEquals("6789", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname 2", data.getSurname());
        Assertions.assertEquals("12346", data.getPhone());
        Assertions.assertEquals("6780", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 3", data.getName());
        Assertions.assertEquals("Surname 3", data.getSurname());
        Assertions.assertEquals("12347", data.getPhone());
        Assertions.assertEquals("6781", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 4", data.getName());
        Assertions.assertEquals("Surname 4", data.getSurname());
        Assertions.assertEquals("12348", data.getPhone());
        Assertions.assertEquals("6782", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 5", data.getName());
        Assertions.assertEquals("Surname 5", data.getSurname());
        Assertions.assertEquals("12349", data.getPhone());
        Assertions.assertEquals("6783", data.getIdentifier());
        Assertions.assertEquals(false, data.getActive());
    }

    @Test
    @DisplayName("Returns all data in descending order by surname")
    public void testGetAll_Surname_Desc() {
        final Iterator<? extends Member> result;
        final Member                     sample;
        Member                           data;
        final Pageable                   pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "surname");

        sample = new DtoMember();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 5", data.getName());
        Assertions.assertEquals("Surname 5", data.getSurname());
        Assertions.assertEquals("12349", data.getPhone());
        Assertions.assertEquals("6783", data.getIdentifier());
        Assertions.assertEquals(false, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 4", data.getName());
        Assertions.assertEquals("Surname 4", data.getSurname());
        Assertions.assertEquals("12348", data.getPhone());
        Assertions.assertEquals("6782", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 3", data.getName());
        Assertions.assertEquals("Surname 3", data.getSurname());
        Assertions.assertEquals("12347", data.getPhone());
        Assertions.assertEquals("6781", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 2", data.getName());
        Assertions.assertEquals("Surname 2", data.getSurname());
        Assertions.assertEquals("12346", data.getPhone());
        Assertions.assertEquals("6780", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Member 1", data.getName());
        Assertions.assertEquals("Surname 1", data.getSurname());
        Assertions.assertEquals("12345", data.getPhone());
        Assertions.assertEquals("6789", data.getIdentifier());
        Assertions.assertEquals(true, data.getActive());
    }

}
