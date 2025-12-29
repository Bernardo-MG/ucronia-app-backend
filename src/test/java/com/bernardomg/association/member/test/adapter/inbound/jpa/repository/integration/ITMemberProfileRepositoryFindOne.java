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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.factory.MemberContacts;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberProfileRepository - find one")
class ITMemberProfileRepositoryFindOne {

    @Autowired
    private MemberProfileRepository repository;

    @Test
    @DisplayName("With a member, it is returned")
    @ActiveMember
    void testFindOne() {
        final Optional<MemberProfile> member;

        // WHEN
        member = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .contains(MemberContacts.active());
    }

    @Test
    @DisplayName("With no member, nothing is returned")
    void testFindOne_NoData() {
        final Optional<MemberProfile> member;

        // WHEN
        member = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with no member role, it returns nothing")
    @ValidProfile
    void testFindOne_NoMembership() {
        final Optional<MemberProfile> member;

        // WHEN
        member = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .isEmpty();
    }

}
