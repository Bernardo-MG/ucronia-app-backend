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
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.DtoMemberRequest;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.MemberRequest;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - get all")
@Sql({ "/db/queries/member/multiple.sql" })
public class ITMemberServiceGetAll {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<? extends Member> result;
        final MemberRequest              sample;
        final Pageable                   pageable;

        pageable = Pageable.unpaged();

        sample = new DtoMemberRequest();

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(5, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all the entities data")
    public void testGetAll_Data() {
        final Iterator<? extends Member> result;
        final MemberRequest              sample;
        Member                           data;
        final Pageable                   pageable;

        pageable = Pageable.unpaged();

        sample = new DtoMemberRequest();

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

}
