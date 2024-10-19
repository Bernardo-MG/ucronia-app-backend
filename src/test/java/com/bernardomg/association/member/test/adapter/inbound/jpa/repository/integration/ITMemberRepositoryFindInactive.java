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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.fee.test.configuration.initializer.FeeInitializer;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipInactivePerson;
import com.bernardomg.association.person.test.configuration.data.annotation.SinglePerson;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - find inactive")
class ITMemberRepositoryFindInactive {

    @Autowired
    private FeeInitializer   feeInitializer;

    @Autowired
    private MemberRepository repository;

    public ITMemberRepositoryFindInactive() {
        super();
    }

    @Test
    @DisplayName("With an active member, nothing is returned")
    @MembershipActivePerson
    void testFindInactive_Active() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        pageable = Pageable.unpaged();

        // WHEN
        members = repository.findInactive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With an inactive member, it is returned")
    @MembershipInactivePerson
    void testFindInactive_Inactive() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        feeInitializer.registerFeeCurrentMonth(false);

        pageable = Pageable.unpaged();

        // WHEN
        members = repository.findInactive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(Members.inactive());
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testFindInactive_NoData() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        members = repository.findInactive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with no membership, it returns nothing")
    @SinglePerson
    void testFindInactive_NoMembership() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        members = repository.findInactive(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

}
