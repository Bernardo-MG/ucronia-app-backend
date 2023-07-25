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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.DtoMember;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.request.MemberUpdate;
import com.bernardomg.association.member.persistence.model.PersistentMember;
import com.bernardomg.association.member.persistence.repository.MemberRepository;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.association.test.member.util.assertion.MemberAssertions;
import com.bernardomg.association.test.member.util.model.MembersUpdate;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Member service - update errors")
@Sql({ "/db/queries/member/single.sql" })
class ITMemberServiceUpdate {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private MemberService    service;

    public ITMemberServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With an existing entity, no new entity is persisted")
    void testUpdate_AddsNoEntity() {
        final MemberUpdate memberRequest;

        memberRequest = MembersUpdate.nameChange();

        service.update(1L, memberRequest);

        Assertions.assertThat(repository.count())
            .isOne();
    }

    @Test
    @DisplayName("With a changed entity, the change is persisted")
    void testUpdate_PersistedData() {
        final MemberUpdate     memberRequest;
        final PersistentMember entity;

        memberRequest = MembersUpdate.nameChange();

        service.update(1L, memberRequest);
        entity = repository.findAll()
            .iterator()
            .next();
        MemberAssertions.isEqualTo(entity, PersistentMember.builder()
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build());
    }

    @Test
    @DisplayName("With a changed entity, the changed data is returned")
    void testUpdate_ReturnedData() {
        final MemberUpdate memberRequest;
        final Member       member;

        memberRequest = MembersUpdate.nameChange();

        member = service.update(1L, memberRequest);
        MemberAssertions.isEqualTo(member, DtoMember.builder()
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build());
    }

}
