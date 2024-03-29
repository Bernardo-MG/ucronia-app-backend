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
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.config.factory.MemberEntities;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - save")
class ITMemberRepositorySave {

    @Autowired
    private MemberRepository       memberRepository;

    @Autowired
    private MemberSpringRepository repository;

    public ITMemberRepositorySave() {
        super();
    }

    @Test
    @DisplayName("With a valid member, the member is persisted")
    void testCreate_PersistedData() {
        final Member                 member;
        final Iterable<MemberEntity> entities;

        // GIVEN
        member = Members.active();

        // WHEN
        memberRepository.save(member);

        // THEN
        entities = repository.findAll();

        Assertions.assertThat(entities)
            .as("entities")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "number")
            .containsExactly(MemberEntities.valid());
    }

    @Test
    @DisplayName("With a valid member, the created member is returned")
    void testCreate_ReturnedData() {
        final Member member;
        final Member saved;

        // GIVEN
        member = Members.active();

        // WHEN
        saved = memberRepository.save(member);

        // THEN
        Assertions.assertThat(saved)
            .as("member")
            .isEqualTo(Members.inactive());
    }

}
