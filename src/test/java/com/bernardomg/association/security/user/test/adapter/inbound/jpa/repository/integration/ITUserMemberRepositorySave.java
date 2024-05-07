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

package com.bernardomg.association.security.user.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.member.test.config.factory.PersonConstants;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserMemberEntity;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.UserMemberSpringRepository;
import com.bernardomg.association.security.user.domain.repository.UserMemberRepository;
import com.bernardomg.association.security.user.test.config.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.config.data.annotation.ValidUserWithMember;
import com.bernardomg.association.security.user.test.config.factory.UserConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("UserMemberRepository - save")
class ITUserMemberRepositorySave {

    @Autowired
    private UserMemberRepository       repository;

    @Autowired
    private UserMemberSpringRepository userMemberSpringRepository;

    @Test
    @DisplayName("When the data already exists, the relationship is persisted")
    @ValidUserWithMember
    void testSave_Existing_PersistedData() {
        final Collection<UserMemberEntity> members;

        // WHEN
        repository.save(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        members = userMemberSpringRepository.findAll();
        SoftAssertions.assertSoftly(softly -> {
            final UserMemberEntity member;

            softly.assertThat(members)
                .as("members")
                .hasSize(1);

            member = members.iterator()
                .next();
            softly.assertThat(member.getUserId())
                .as("user id")
                .isNotNull();
            softly.assertThat(member.getMember()
                .getPerson()
                .getNumber())
                .as("member number")
                .isEqualTo(PersonConstants.NUMBER);
            softly.assertThat(member.getUser()
                .getUsername())
                .as("username")
                .isEqualTo(UserConstants.USERNAME);
        });
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUserWithMember
    void testSave_Existing_ReturnedData() {
        final Member member;

        // WHEN
        member = repository.save(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .isEqualTo(Members.inactive());
    }

    @Test
    @DisplayName("With valid data, the relationship is persisted")
    @ValidUser
    @ValidMember
    void testSave_PersistedData() {
        final Collection<UserMemberEntity> members;

        // WHEN
        repository.save(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        members = userMemberSpringRepository.findAll();
        SoftAssertions.assertSoftly(softly -> {
            final UserMemberEntity member;

            softly.assertThat(members)
                .as("members")
                .hasSize(1);

            member = members.iterator()
                .next();
            softly.assertThat(member.getUserId())
                .as("user id")
                .isNotNull();
            softly.assertThat(member.getMember()
                .getPerson()
                .getNumber())
                .as("member number")
                .isEqualTo(PersonConstants.NUMBER);
            softly.assertThat(member.getUser()
                .getUsername())
                .as("username")
                .isEqualTo(UserConstants.USERNAME);
        });
    }

    @Test
    @DisplayName("With valid data, the created relationship is returned")
    @ValidUser
    @ValidMember
    void testSave_ReturnedData() {
        final Member member;

        // WHEN
        member = repository.save(UserConstants.USERNAME, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .isEqualTo(Members.inactive());
    }

}
