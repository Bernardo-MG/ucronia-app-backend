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

import com.bernardomg.association.membership.member.service.MemberService;
import com.bernardomg.association.membership.test.fee.util.initializer.FeeInitializer;
import com.bernardomg.association.membership.test.member.config.ValidMember;
import com.bernardomg.association.membership.test.member.config.factory.MemberChanges;
import com.bernardomg.association.membership.test.member.config.factory.MemberConstants;
import com.bernardomg.association.membership.test.member.config.factory.MemberEntities;
import com.bernardomg.association.membership.test.member.config.factory.Members;
import com.bernardomg.association.membership.test.member.util.assertion.MemberAssertions;
import com.bernardomg.association.model.member.Member;
import com.bernardomg.association.model.member.MemberChange;
import com.bernardomg.association.persistence.member.model.MemberEntity;
import com.bernardomg.association.persistence.member.repository.MemberRepository;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - update")
class ITMemberServiceUpdate {

    @Autowired
    private FeeInitializer   feeInitializer;

    @Autowired
    private MemberRepository repository;

    @Autowired
    private MemberService    service;

    public ITMemberServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("When updating an active member, the change is returned")
    @ValidMember
    void testUpdate_Active_ReturnedData() {
        final MemberChange memberRequest;
        final Member       member;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);
        memberRequest = MemberChanges.nameChange();

        // WHEN
        member = service.update(MemberConstants.NUMBER, memberRequest);

        // THEN
        Assertions.assertThat(member)
            .as("member")
            .isEqualTo(Members.nameChangeActive());
    }

    @Test
    @DisplayName("With an existing entity, no new entity is persisted")
    @ValidMember
    void testUpdate_AddsNoEntity() {
        final MemberChange memberRequest;

        // GIVEN
        memberRequest = MemberChanges.nameChange();

        // WHEN
        service.update(MemberConstants.NUMBER, memberRequest);

        // THEN
        Assertions.assertThat(repository.count())
            .isOne();
    }

    @Test
    @DisplayName("When updating a not member, the change is returned")
    @ValidMember
    void testUpdate_NotActive_ReturnedData() {
        final MemberChange memberRequest;
        final Member       member;

        // GIVEN
        memberRequest = MemberChanges.nameChange();

        // WHEN
        member = service.update(MemberConstants.NUMBER, memberRequest);

        // THEN
        Assertions.assertThat(member)
            .as("member")
            .isEqualTo(Members.nameChange());
    }

    @Test
    @DisplayName("With a member having padding whitespaces in name and surname, these whitespaces are removed")
    @ValidMember
    void testUpdate_Padded_PersistedData() {
        final MemberChange memberRequest;
        final MemberEntity entity;

        // GIVEN
        memberRequest = MemberChanges.paddedWithWhitespaces();

        // WHEN
        service.update(MemberConstants.NUMBER, memberRequest);

        // THEN
        entity = repository.findAll()
            .iterator()
            .next();
        MemberAssertions.isEqualTo(entity, MemberEntities.valid());
    }

    @Test
    @DisplayName("When updating a member, the change is persisted")
    @ValidMember
    void testUpdate_PersistedData() {
        final MemberChange memberRequest;
        final MemberEntity entity;

        // GIVEN
        memberRequest = MemberChanges.nameChange();

        // WHEN
        service.update(MemberConstants.NUMBER, memberRequest);

        // THEN
        entity = repository.findAll()
            .iterator()
            .next();
        MemberAssertions.isEqualTo(entity, MemberEntities.nameChange());
    }

}
