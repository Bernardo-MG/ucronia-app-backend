/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.test.member.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.request.ValidatedMemberUpdate;
import com.bernardomg.association.member.persistence.model.PersistentMember;
import com.bernardomg.association.member.persistence.repository.MemberRepository;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - update")
@Sql({ "/db/queries/member/single.sql" })
public class ITMemberServiceUpdate {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private MemberService    service;

    public ITMemberServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With an existing entity, no new entity is persisted")
    public void testUpdate_AddsNoEntity() {
        final ValidatedMemberUpdate memberRequest;

        memberRequest = new ValidatedMemberUpdate();
        memberRequest.setName("Member 123");
        memberRequest.setSurname("Surname");
        memberRequest.setPhone("12345");
        memberRequest.setIdentifier("6789");
        memberRequest.setActive(true);

        service.update(1L, memberRequest);

        Assertions.assertThat(repository.count())
            .isOne();
    }

    @Test
    @DisplayName("With a not existing entity, a new entity is persisted")
    public void testUpdate_NotExisting_AddsEntity() {
        final ValidatedMemberUpdate memberRequest;

        memberRequest = new ValidatedMemberUpdate();
        memberRequest.setName("Member 123");
        memberRequest.setSurname("Surname");
        memberRequest.setPhone("12345");
        memberRequest.setIdentifier("6789");
        memberRequest.setActive(true);

        service.update(10L, memberRequest);

        Assertions.assertThat(repository.count())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With a changed entity, the change is persisted")
    public void testUpdate_PersistedData() {
        final ValidatedMemberUpdate memberRequest;
        final PersistentMember      entity;

        memberRequest = new ValidatedMemberUpdate();
        memberRequest.setName("Member 123");
        memberRequest.setSurname("Surname");
        memberRequest.setPhone("12345");
        memberRequest.setIdentifier("6789");
        memberRequest.setActive(true);

        service.update(1L, memberRequest);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getId())
            .isNotNull();
        Assertions.assertThat(entity.getName())
            .isEqualTo("Member 123");
        Assertions.assertThat(entity.getSurname())
            .isEqualTo("Surname");
        Assertions.assertThat(entity.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(entity.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(entity.getActive())
            .isTrue();
    }

    @Test
    @DisplayName("With a changed entity, the changed data is returned")
    public void testUpdate_ReturnedData() {
        final ValidatedMemberUpdate memberRequest;
        final Member                member;

        memberRequest = new ValidatedMemberUpdate();
        memberRequest.setName("Member 123");
        memberRequest.setSurname("Surname");
        memberRequest.setPhone("12345");
        memberRequest.setIdentifier("6789");
        memberRequest.setActive(true);

        member = service.update(1L, memberRequest);

        Assertions.assertThat(member.getId())
            .isNotNull();
        Assertions.assertThat(member.getName())
            .isEqualTo("Member 123");
        Assertions.assertThat(member.getSurname())
            .isEqualTo("Surname");
        Assertions.assertThat(member.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(member.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(member.getActive())
            .isTrue();
    }

}
