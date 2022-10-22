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

package com.bernardomg.association.test.crud.member.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.crud.member.model.DtoMember;
import com.bernardomg.association.crud.member.model.Member;
import com.bernardomg.association.crud.member.model.PersistentMember;
import com.bernardomg.association.crud.member.repository.MemberRepository;
import com.bernardomg.association.crud.member.service.MemberService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default member service - create")
public class ITMemberServiceCreate {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private MemberService    service;

    public ITMemberServiceCreate() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when creating")
    public void testCreate_AddsEntity() {
        final DtoMember member;

        member = new DtoMember();
        member.setName("Member");
        member.setSurname("Surname");
        member.setPhone("12345");
        member.setIdentifier("6789");
        member.setActive(true);

        service.create(member);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Adds two entities with minimal data")
    public void testCreate_Minimal_Additional_AddsEntity() {
        DtoMember member;

        member = new DtoMember();
        member.setName("Member");
        member.setSurname("");
        member.setPhone("");
        member.setIdentifier("");
        member.setActive(true);

        service.create(member);

        member = new DtoMember();
        member.setName("Member 2");
        member.setSurname("");
        member.setPhone("");
        member.setIdentifier("");
        member.setActive(true);

        service.create(member);

        Assertions.assertEquals(2L, repository.count());
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final DtoMember        member;
        final PersistentMember entity;

        member = new DtoMember();
        member.setName("Member");
        member.setSurname("Surname");
        member.setPhone("12345");
        member.setIdentifier("6789");
        member.setActive(true);

        service.create(member);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("Member", entity.getName());
        Assertions.assertEquals("Surname", entity.getSurname());
        Assertions.assertEquals("12345", entity.getPhone());
        Assertions.assertEquals("6789", entity.getIdentifier());
        Assertions.assertEquals(true, entity.getActive());
    }

    @Test
    @DisplayName("Returns the created data")
    public void testCreate_ReturnedData() {
        final Member    result;
        final DtoMember member;

        member = new DtoMember();
        member.setName("Member");
        member.setSurname("Surname");
        member.setPhone("12345");
        member.setIdentifier("6789");
        member.setActive(true);

        result = service.create(member);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Member", result.getName());
        Assertions.assertEquals("Surname", result.getSurname());
        Assertions.assertEquals("12345", result.getPhone());
        Assertions.assertEquals("6789", result.getIdentifier());
        Assertions.assertEquals(true, result.getActive());
    }

}
