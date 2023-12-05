/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.membership.test.member.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mapping.PropertyReferenceException;

import com.bernardomg.association.membership.member.model.request.MemberQuery;
import com.bernardomg.association.membership.member.service.MemberService;
import com.bernardomg.association.membership.test.member.configuration.MultipleMembers;
import com.bernardomg.association.membership.test.member.util.model.MembersQuery;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - get all - errors")
@MultipleMembers
class ITMemberServiceGetAllSortError {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetAllSortError() {
        super();
    }

    @Test
    @DisplayName("Ordering by a not existing field generates an error")
    void testGetAll_NotExisting() {
        final MemberQuery      memberQuery;
        final Pageable         pageable;
        final ThrowingCallable executable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "abc");

        memberQuery = MembersQuery.empty();

        executable = () -> service.getAll(memberQuery, pageable)
            .iterator();

        Assertions.assertThatThrownBy(executable)
            .isInstanceOf(PropertyReferenceException.class);
    }

}
