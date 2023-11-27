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

import com.bernardomg.association.membership.member.model.DtoMember;
import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.request.MemberCreate;
import com.bernardomg.association.membership.member.persistence.model.MemberEntity;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;
import com.bernardomg.association.membership.member.service.MemberService;
import com.bernardomg.association.membership.test.member.util.assertion.MemberAssertions;
import com.bernardomg.association.membership.test.member.util.model.MembersCreate;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Member service - create")
class ITMemberServiceCreate {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private MemberService    service;

    public ITMemberServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With two members with minimal data, the members are persisted")
    void testCreate_Minimal_Additional_AddsEntity() {
        MemberCreate memberRequest;

        memberRequest = MembersCreate.active();

        service.create(memberRequest);

        memberRequest = MembersCreate.alternative();

        service.create(memberRequest);

        Assertions.assertThat(repository.count())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With a member having padding whitespaces in name and surname, these whitespaces are removed")
    void testCreate_Padded_PersistedData() {
        final MemberCreate memberRequest;
        final MemberEntity entity;

        memberRequest = MembersCreate.paddedWithWhitespaces();

        service.create(memberRequest);

        Assertions.assertThat(repository.count())
            .isOne();

        entity = repository.findAll()
            .iterator()
            .next();

        MemberAssertions.isEqualTo(entity, MemberEntity.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build());
    }

    @Test
    @DisplayName("With a valid member, the member is persisted")
    void testCreate_PersistedData() {
        final MemberCreate memberRequest;
        final MemberEntity entity;

        memberRequest = MembersCreate.active();

        service.create(memberRequest);

        Assertions.assertThat(repository.count())
            .isOne();

        entity = repository.findAll()
            .iterator()
            .next();

        MemberAssertions.isEqualTo(entity, MemberEntity.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build());
    }

    @Test
    @DisplayName("With a valid member, the created member is returned")
    void testCreate_ReturnedData() {
        final MemberCreate memberRequest;
        final Member       member;

        memberRequest = MembersCreate.active();

        member = service.create(memberRequest);

        MemberAssertions.isEqualTo(member, DtoMember.builder()
            .name("Member")
            .surname("Surname")
            .phone("12345")
            .identifier("6789")
            .active(true)
            .build());
    }

}
