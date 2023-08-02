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

package com.bernardomg.association.test.member.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.request.MemberCreate;
import com.bernardomg.association.member.persistence.repository.MemberRepository;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.test.member.util.model.MembersCreate;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("Member service - create errors")
@Sql({ "/db/queries/member/single.sql" })
class ITMemberServiceCreateError {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private MemberService    service;

    public ITMemberServiceCreateError() {
        super();
    }

    @Test
    @DisplayName("With a missing active flag it throws an exception")
    void testCreate_MissingActive() {
        final MemberCreate     memberRequest;
        final ThrowingCallable execution;

        memberRequest = MembersCreate.missingActive();

        execution = () -> {
            service.create(memberRequest);
            repository.flush();
        };

        // TODO: Shouldn't this be a validation error?
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("With a missing identifier it throws an exception")
    void testCreate_MissingIdentifier() {
        final MemberCreate     memberRequest;
        final ThrowingCallable execution;

        memberRequest = MembersCreate.missingIdentifier();

        execution = () -> {
            service.create(memberRequest);
            repository.flush();
        };

        // TODO: Shouldn't this be a validation error?
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("With a missing name it throws an exception")
    void testCreate_MissingName() {
        final MemberCreate     memberRequest;
        final ThrowingCallable execution;

        memberRequest = MembersCreate.missingName();

        execution = () -> {
            service.create(memberRequest);
            repository.flush();
        };

        // TODO: Shouldn't this be a validation error?
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("With a missing phone it throws an exception")
    void testCreate_MissingPhone() {
        final MemberCreate     memberRequest;
        final ThrowingCallable execution;

        memberRequest = MembersCreate.missingPhone();

        execution = () -> {
            service.create(memberRequest);
            repository.flush();
        };

        // TODO: Shouldn't this be a validation error?
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("With a missing surname it throws an exception")
    void testCreate_MissingSurname() {
        final MemberCreate     memberRequest;
        final ThrowingCallable execution;

        memberRequest = MembersCreate.missingSurname();

        execution = () -> {
            service.create(memberRequest);
            repository.flush();
        };

        // TODO: Shouldn't this be a validation error?
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

}
