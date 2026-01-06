/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit members to whom the Software is
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

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveToNotRenewMember;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveToRenewMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveToNotRenewMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveToRenewMember;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - find all to renew")
class ITMemberRepositoryFindAllWithRenewalMismatch {

    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("With a profile without member role, nothing is returned")
    @ValidProfile
    void testFindAllWithRenewalMismatch_NoMembership() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With an active membership to not renew, it is returned")
    @PositiveFeeType
    @ActiveToNotRenewMember
    void testFindAllWithRenewalMismatch_ToNotRenewActive() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(Members.activeNoRenew());
    }

    @Test
    @DisplayName("With an inactive membership to not renew, nothing is returned")
    @PositiveFeeType
    @InactiveToNotRenewMember
    void testFindAllWithRenewalMismatch_ToNotRenewInactive() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With an active membership to renew, nothing is returned")
    @PositiveFeeType
    @ActiveToRenewMember
    void testFindAllWithRenewalMismatch_ToRenewActive() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With an inactive membership to renew, it is returned")
    @PositiveFeeType
    @InactiveToRenewMember
    void testFindAllWithRenewalMismatch_ToRenewInactive() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAllWithRenewalMismatch();

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(Members.inactive());
    }

}
