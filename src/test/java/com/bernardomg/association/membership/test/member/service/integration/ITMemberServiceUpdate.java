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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.request.MemberUpdate;
import com.bernardomg.association.membership.member.persistence.model.MemberEntity;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;
import com.bernardomg.association.membership.member.service.MemberService;
import com.bernardomg.association.membership.test.member.configuration.ValidMember;
import com.bernardomg.association.membership.test.member.util.assertion.MemberAssertions;
import com.bernardomg.association.membership.test.member.util.model.MembersEntity;
import com.bernardomg.association.membership.test.member.util.model.MembersUpdate;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - update errors")
@ValidMember
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
    @DisplayName("With a member having padding whitespaces in name and surname, these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final MemberUpdate memberRequest;
        final MemberEntity entity;

        memberRequest = MembersUpdate.paddedWithWhitespaces();

        service.update(1L, memberRequest);
        entity = repository.findAll()
            .iterator()
            .next();
        MemberAssertions.isEqualTo(entity, MembersEntity.valid(123));
    }

    @Test
    @DisplayName("With a changed entity, the change is persisted")
    void testUpdate_PersistedData() {
        final MemberUpdate memberRequest;
        final MemberEntity entity;

        memberRequest = MembersUpdate.nameChange();

        service.update(1L, memberRequest);
        entity = repository.findAll()
            .iterator()
            .next();
        MemberAssertions.isEqualTo(entity, MemberEntity.builder()
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build());
    }

    @Test
    @DisplayName("With a changed entity, the changed data is returned")
    void testUpdate_ReturnedData() {
        final MemberUpdate memberRequest;
        final Member       member;

        memberRequest = MembersUpdate.nameChange();

        member = service.update(1L, memberRequest);
        MemberAssertions.isEqualTo(member, Member.builder()
            .name("Member 123")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .build());
    }

}
