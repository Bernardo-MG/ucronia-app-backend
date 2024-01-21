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

package com.bernardomg.association.member.test.service.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.member.usecase.service.MemberService;
import com.bernardomg.association.test.data.fee.initializer.FeeInitializer;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - get one")
class ITMemberServiceGetOne {

    @Autowired
    private FeeInitializer feeInitializer;

    @Autowired
    private MemberService  service;

    public ITMemberServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("With a member having no fee in the current month, a not active member is returned")
    @ValidMember
    void testGetOne_NoFee() {
        final Optional<Member> memberOptional;

        // WHEN
        memberOptional = service.getOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(memberOptional)
            .contains(Members.inactive());
    }

    @Test
    @DisplayName("With a member having a not paid fee in the current month, an active member is returned")
    @ValidMember
    void testGetOne_NotPaidFee_CurrentMonth() {
        final Optional<Member> memberOptional;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        // WHEN
        memberOptional = service.getOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(memberOptional)
            .contains(Members.active());
    }

    @Test
    @DisplayName("With a member having a paid fee in the current month, an active member is returned")
    @ValidMember
    void testGetOne_PaidFee_CurrentMonth() {
        final Optional<Member> memberOptional;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(true);

        // WHEN
        memberOptional = service.getOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(memberOptional)
            .contains(Members.active());
    }

    @Test
    @DisplayName("With a member having a paid fee in the next month, a not active member is returned")
    @ValidMember
    void testGetOne_PaidFee_NextMonth() {
        final Optional<Member> memberOptional;

        // GIVEN
        feeInitializer.registerFeeNextMonth(true);

        // WHEN
        memberOptional = service.getOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(memberOptional)
            .contains(Members.inactive());
    }

    @Test
    @DisplayName("With a member having a paid fee in the previous month, a not active member is returned")
    @ValidMember
    void testGetOne_PaidFee_PreviousMonth() {
        final Optional<Member> memberOptional;

        // GIVEN
        feeInitializer.registerFeePreviousMonth(true);

        // WHEN
        memberOptional = service.getOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(memberOptional)
            .contains(Members.inactive());
    }

}
