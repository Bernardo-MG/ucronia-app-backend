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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.service.MemberService;
import com.bernardomg.association.membership.test.member.util.assertion.MemberAssertions;
import com.bernardomg.association.membership.test.member.util.model.DtoMembers;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Member service - get one")
class ITMemberServiceGetOne {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("With a valid id for an active member, the related entity is returned")
    @Sql({ "/db/queries/member/single.sql" })
    @Sql({ "/db/queries/fee/single.sql" })
    void testGetOne_Active() {
        final Optional<Member> memberOptional;
        final Member           member;

        memberOptional = service.getOne(1L);

        Assertions.assertThat(memberOptional)
            .isPresent();

        member = memberOptional.get();
        MemberAssertions.isEqualTo(member, DtoMembers.valid(1));
    }

    @Test
    @DisplayName("With a valid id, the related entity is returned")
    @Sql({ "/db/queries/member/single.sql" })
    @Sql({ "/db/queries/fee/single.sql" })
    void testGetOne_Existing() {
        final Optional<Member> memberOptional;
        final Member           member;

        memberOptional = service.getOne(1L);

        Assertions.assertThat(memberOptional)
            .isPresent();

        member = memberOptional.get();
        MemberAssertions.isEqualTo(member, DtoMembers.valid(1));
    }

    @Test
    @DisplayName("With a valid id for an inactive member, the related entity is returned")
    @Sql({ "/db/queries/member/single.sql" })
    void testGetOne_Inactive() {
        final Optional<Member> memberOptional;
        final Member           member;

        memberOptional = service.getOne(1L);

        Assertions.assertThat(memberOptional)
            .isPresent();

        member = memberOptional.get();
        MemberAssertions.isEqualTo(member, DtoMembers.valid(1));
    }

}
