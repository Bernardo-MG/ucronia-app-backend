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

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.config.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.config.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.config.factory.MemberEntities;
import com.bernardomg.association.person.test.config.factory.PersonConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - activate multiple")
class ITMemberRepositoryActivateMultiple {

    @Autowired
    private MemberSpringRepository memberRepository;

    @Autowired
    private MemberRepository       repository;

    @Test
    @DisplayName("With an existing active member, nothing changes")
    @ActiveMember
    void testActivate_Active() {
        final Iterable<MemberEntity> entities;

        // WHEN
        repository.activate(List.of(PersonConstants.NUMBER));

        // THEN
        entities = memberRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id")
            .containsExactly(MemberEntities.active());
    }

    @Test
    @DisplayName("With an existing inactive member, it is activated")
    @InactiveMember
    void testActivate_Inactive() {
        final Iterable<MemberEntity> entities;

        // WHEN
        repository.activate(List.of(PersonConstants.NUMBER));

        // THEN
        entities = memberRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "person.id")
            .containsExactly(MemberEntities.active());
    }

    @Test
    @DisplayName("With no member, nothing changes")
    void testActivate_NoData() {
        final Iterable<MemberEntity> entities;

        // WHEN
        repository.activate(List.of(PersonConstants.NUMBER));

        // THEN
        entities = memberRepository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .isEmpty();
    }

}
