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

package com.bernardomg.association.test.member.service.integration;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.request.MemberQuery;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.member.util.model.MembersQuery;

@IntegrationTest
@DisplayName("Member service - get all - filter")
@Sql({ "/db/queries/member/multiple.sql" })
class ITMemberServiceGetAllFilter {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetAllFilter() {
        super();
    }

    @Test
    @DisplayName("With a filter applied to active status, the returned data is filtered")
    void testGetAll_Active_Count() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.active();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(IterableUtils.size(members))
            .isEqualTo(4);
    }

    @Test
    @DisplayName("With a filter applied to not active status, the returned data is filtered")
    void testGetAll_NotActive_Count() {
        final Iterable<Member> members;
        final MemberQuery      memberQuery;
        final Pageable         pageable;

        pageable = Pageable.unpaged();

        memberQuery = MembersQuery.notActive();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(IterableUtils.size(members))
            .isEqualTo(1);
    }

}
