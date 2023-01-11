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
@DisplayName("Member service - get all - filter")
@Sql({ "/db/queries/member/multiple.sql" })
public class ITMemberServiceGetAllFilter {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetAllFilter() {
        super();
    }

    @Test
    @DisplayName("Filters by active")
    public void testGetAll_Active_Count() {
        final Iterable<? extends Member> result;
        final DtoMemberRequest           sample;
        final Pageable                   pageable;

        pageable = Pageable.unpaged();

        sample = new DtoMemberRequest();
        sample.setActive(true);

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(4, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all the entities when not filtering")
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
    @DisplayName("Filters by noy active")
    public void testGetAll_NotActive_Count() {
        final Iterable<? extends Member> result;
        final DtoMemberRequest           sample;
        final Pageable                   pageable;

        pageable = Pageable.unpaged();

        sample = new DtoMemberRequest();
        sample.setActive(false);

        result = service.getAll(sample, pageable);

        Assertions.assertEquals(1, IterableUtils.size(result));
    }

}
