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

package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - find one")
class ITMemberRepositoryFindOne {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("With an active member, it is returned")
    @ActiveMember
    void testFindOne_Active() {
        final Optional<Member> memberOptional;

        // WHEN
        memberOptional = memberRepository.findOne(PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(memberOptional)
            .contains(Members.active());
    }

    @Test
    @DisplayName("With an inactive member, it is returned")
    @InactiveMember
    void testFindOne_Inactive() {
        final Optional<Member> memberOptional;

        // WHEN
        memberOptional = memberRepository.findOne(PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(memberOptional)
            .contains(Members.inactive());
    }

    @Test
    @DisplayName("With no member, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Member> memberOptional;

        // WHEN
        memberOptional = memberRepository.findOne(PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(memberOptional)
            .isEmpty();
    }

}
