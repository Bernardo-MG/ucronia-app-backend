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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.request.DtoMemberQueryRequest;
import com.bernardomg.association.member.model.request.MemberQueryRequest;
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
    @DisplayName("With multiple members it returns all the members")
    public void testGetAll() {
        final Iterable<Member>   members;
        final Iterator<Member>   membersItr;
        final MemberQueryRequest memberQuery;
        final Pageable           pageable;
        Member                   member;

        pageable = Pageable.unpaged();

        memberQuery = new DtoMemberQueryRequest();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(IterableUtils.size(members))
            .isEqualTo(5);

        membersItr = members.iterator();

        member = membersItr.next();
        Assertions.assertThat(member.getId())
            .isNotNull();
        Assertions.assertThat(member.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(member.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(member.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(member.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(member.getActive())
            .isTrue();

        member = membersItr.next();
        Assertions.assertThat(member.getId())
            .isNotNull();
        Assertions.assertThat(member.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(member.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(member.getPhone())
            .isEqualTo("12346");
        Assertions.assertThat(member.getIdentifier())
            .isEqualTo("6780");
        Assertions.assertThat(member.getActive())
            .isTrue();

        member = membersItr.next();
        Assertions.assertThat(member.getId())
            .isNotNull();
        Assertions.assertThat(member.getName())
            .isEqualTo("Member 3");
        Assertions.assertThat(member.getSurname())
            .isEqualTo("Surname 3");
        Assertions.assertThat(member.getPhone())
            .isEqualTo("12347");
        Assertions.assertThat(member.getIdentifier())
            .isEqualTo("6781");
        Assertions.assertThat(member.getActive())
            .isTrue();

        member = membersItr.next();
        Assertions.assertThat(member.getId())
            .isNotNull();
        Assertions.assertThat(member.getName())
            .isEqualTo("Member 4");
        Assertions.assertThat(member.getSurname())
            .isEqualTo("Surname 4");
        Assertions.assertThat(member.getPhone())
            .isEqualTo("12348");
        Assertions.assertThat(member.getIdentifier())
            .isEqualTo("6782");
        Assertions.assertThat(member.getActive())
            .isTrue();

        member = membersItr.next();
        Assertions.assertThat(member.getId())
            .isNotNull();
        Assertions.assertThat(member.getName())
            .isEqualTo("Member 5");
        Assertions.assertThat(member.getSurname())
            .isEqualTo("Surname 5");
        Assertions.assertThat(member.getPhone())
            .isEqualTo("12349");
        Assertions.assertThat(member.getIdentifier())
            .isEqualTo("6783");
        Assertions.assertThat(member.getActive())
            .isFalse();
    }

    @Test
    @DisplayName("With an inactive member it returns the member")
    @Sql({ "/db/queries/member/inactive.sql" })
    public void testGetAll_Inactive() {
        final Iterable<Member>   members;
        final Iterator<Member>   membersItr;
        final MemberQueryRequest memberQuery;
        final Pageable           pageable;
        Member                   member;

        pageable = Pageable.unpaged();

        memberQuery = new DtoMemberQueryRequest();

        members = service.getAll(memberQuery, pageable);

        Assertions.assertThat(IterableUtils.size(members))
            .isEqualTo(1);

        membersItr = members.iterator();

        member = membersItr.next();
        Assertions.assertThat(member.getId())
            .isNotNull();
        Assertions.assertThat(member.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(member.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(member.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(member.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(member.getActive())
            .isFalse();
    }

}
