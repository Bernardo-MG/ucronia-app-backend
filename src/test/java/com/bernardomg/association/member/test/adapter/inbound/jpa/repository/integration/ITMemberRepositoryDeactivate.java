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

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.configuration.factory.MemberEntities;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - deactivate")
class ITMemberRepositoryDeactivate {

    @Autowired
    private MemberSpringRepository memberRepository;

    @Autowired
    private MemberRepository       repository;

    @Test
    @DisplayName("With an existing active member, it is deactivated")
    @ActiveMember
    void testDeactivate_Active() {
        final Iterable<MemberEntity> entities;

        // WHEN
        repository.deactivate(PersonConstants.NUMBER);

        // THEN
        entities = memberRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id")
            .containsExactly(MemberEntities.inactive());
    }

    @Test
    @DisplayName("With an existing inactive member, nothing changed")
    @InactiveMember
    void testDeactivate_Inactive() {
        final Iterable<MemberEntity> entities;

        // WHEN
        repository.deactivate(PersonConstants.NUMBER);

        // THEN
        entities = memberRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id")
            .containsExactly(MemberEntities.inactive());
    }

    @Test
    @DisplayName("With no member, nothing changes")
    void testDeactivate_NoData() {
        final Iterable<MemberEntity> entities;

        // WHEN
        repository.deactivate(PersonConstants.NUMBER);

        // THEN
        entities = memberRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .isEmpty();
    }

}
