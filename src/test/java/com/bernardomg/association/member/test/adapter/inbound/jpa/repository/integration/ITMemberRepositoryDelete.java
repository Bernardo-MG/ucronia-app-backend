/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.member.adapter.inbound.jpa.repository.QueryMemberSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - delete")
class ITMemberRepositoryDelete {

    @Autowired
    private ProfileSpringRepository     profileSpringRepository;

    @Autowired
    private MemberRepository            repository;

    @Autowired
    private QueryMemberSpringRepository springRepository;

    public ITMemberRepositoryDelete() {
        super();
    }

    @Test
    @DisplayName("When deleting an active member, it is deleted")
    @ActiveMember
    void testDelete_Active() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isZero();
    }

    @Test
    @DisplayName("When deleting an active member, the profile is deleted")
    @ActiveMember
    void testDelete_Active_Profile() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profileSpringRepository.count())
            .isZero();
    }

    @Test
    @DisplayName("When deleting an inactive member, it is deleted")
    @InactiveMember
    void testDelete_Inactive() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isZero();
    }

    @Test
    @DisplayName("When deleting an inactive member, the profile is deleted")
    @InactiveMember
    void testDelete_Inactive_Profile() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profileSpringRepository.count())
            .isZero();
    }

    @Test
    @DisplayName("When there is no data, nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isZero();
    }

    @Test
    @DisplayName("With a profile with no member role, nothing is deleted")
    @ValidProfile
    void testDelete_NoMembership() {
        // WHEN
        repository.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isZero();
    }

}
