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

import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.configuration.factory.PublicMembers;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PublicMemberRepository - find all")
class ITPublicMemberRepositoryFindAll {

    @Autowired
    private PublicMemberRepository repository;

    public ITPublicMemberRepositoryFindAll() {
        super();
    }

    @Test
    @DisplayName("With an active member, it is returned")
    @ActiveMember
    void testFindAll_Active() {
        final Iterable<PublicMember> members;
        final Pageable               pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        members = repository.findAll(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(PublicMembers.active());
    }

    @Test
    @DisplayName("With an inactive member, it is returned")
    @InactiveMember
    void testFindAll_Inactive() {
        final Iterable<PublicMember> members;
        final Pageable               pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        members = repository.findAll(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(PublicMembers.inactive());
    }

    @Test
    @DisplayName("With no data it returns nothing")
    void testFindAll_NoData() {
        final Iterable<PublicMember> members;
        final Pageable               pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        // WHEN
        members = repository.findAll(pageable);

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

}
