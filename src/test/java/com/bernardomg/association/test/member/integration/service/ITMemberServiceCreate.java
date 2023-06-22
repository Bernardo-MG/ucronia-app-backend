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

import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.request.ValidatedMemberCreate;
import com.bernardomg.association.member.persistence.model.PersistentMember;
import com.bernardomg.association.member.persistence.repository.MemberRepository;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - create")
public class ITMemberServiceCreate {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private MemberService    service;

    public ITMemberServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With two members with minimal data, the members are persisted")
    public void testCreate_Minimal_Additional_AddsEntity() {
        ValidatedMemberCreate memberRequest;

        memberRequest = new ValidatedMemberCreate();
        memberRequest.setName("Member");
        memberRequest.setSurname("");
        memberRequest.setPhone("");
        memberRequest.setIdentifier("");
        memberRequest.setActive(true);

        service.create(memberRequest);

        memberRequest = new ValidatedMemberCreate();
        memberRequest.setName("Member 2");
        memberRequest.setSurname("");
        memberRequest.setPhone("");
        memberRequest.setIdentifier("");
        memberRequest.setActive(true);

        service.create(memberRequest);

        Assertions.assertThat(repository.count())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("With a valid member, the member is persisted")
    public void testCreate_PersistedData() {
        final ValidatedMemberCreate memberRequest;
        final PersistentMember      entity;

        memberRequest = new ValidatedMemberCreate();
        memberRequest.setName("Member");
        memberRequest.setSurname("Surname");
        memberRequest.setPhone("12345");
        memberRequest.setIdentifier("6789");
        memberRequest.setActive(true);

        service.create(memberRequest);

        Assertions.assertThat(repository.count())
            .isOne();

        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getId())
            .isNotNull();
        Assertions.assertThat(entity.getName())
            .isEqualTo("Member");
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
    @DisplayName("With a valid member, the created member is returned")
    public void testCreate_ReturnedData() {
        final ValidatedMemberCreate memberRequest;
        final Member                member;

        memberRequest = new ValidatedMemberCreate();
        memberRequest.setName("Member");
        memberRequest.setSurname("Surname");
        memberRequest.setPhone("12345");
        memberRequest.setIdentifier("6789");
        memberRequest.setActive(true);

        member = service.create(memberRequest);

        Assertions.assertThat(member.getId())
            .isNotNull();
        Assertions.assertThat(member.getName())
            .isEqualTo("Member");
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
