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
import com.bernardomg.association.member.model.request.DtoMemberCreationRequest;
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
    @DisplayName("Adds no entity when updating")
    public void testUpdate_AddsNoEntity() {
        final DtoMemberCreationRequest member;

        member = new DtoMemberCreationRequest();
        member.setName("Member 123");
        member.setSurname("Surname");
        member.setPhone("12345");
        member.setIdentifier("6789");
        member.setActive(true);

        service.update(1L, member);

        Assertions.assertThat(repository.count())
            .isOne();
    }

    @Test
    @DisplayName("When updating a not existing entity a new one is added")
    public void testUpdate_NotExisting_AddsEntity() {
        final DtoMemberCreationRequest member;

        member = new DtoMemberCreationRequest();
        member.setName("Member 123");
        member.setSurname("Surname");
        member.setPhone("12345");
        member.setIdentifier("6789");
        member.setActive(true);

        service.update(10L, member);

        Assertions.assertThat(repository.count())
            .isEqualTo(2);
    }

    @Test
    @DisplayName("Updates persisted data")
    public void testUpdate_PersistedData() {
        final DtoMemberCreationRequest member;
        final PersistentMember         entity;

        member = new DtoMemberCreationRequest();
        member.setName("Member 123");
        member.setSurname("Surname");
        member.setPhone("12345");
        member.setIdentifier("6789");
        member.setActive(true);

        service.update(1L, member);
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
    @DisplayName("Returns the updated data")
    public void testUpdate_ReturnedData() {
        final Member                   result;
        final DtoMemberCreationRequest member;

        member = new DtoMemberCreationRequest();
        member.setName("Member 123");
        member.setSurname("Surname");
        member.setPhone("12345");
        member.setIdentifier("6789");
        member.setActive(true);

        result = service.update(1L, member);

        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getName())
            .isEqualTo("Member 123");
        Assertions.assertThat(result.getSurname())
            .isEqualTo("Surname");
        Assertions.assertThat(result.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(result.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(result.getActive())
            .isTrue();
    }

}
