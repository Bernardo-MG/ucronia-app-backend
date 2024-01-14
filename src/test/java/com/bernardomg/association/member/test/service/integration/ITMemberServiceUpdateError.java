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

package com.bernardomg.association.member.test.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.member.exception.MissingMemberIdException;
import com.bernardomg.association.member.model.MemberChange;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.member.test.config.factory.MemberChanges;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - update")
class ITMemberServiceUpdateError {

    @Autowired
    private MemberService service;

    public ITMemberServiceUpdateError() {
        super();
    }

    @Test
    @DisplayName("With a not existing entity, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final MemberChange     memberRequest;
        final ThrowingCallable execution;

        // GIVEN
        memberRequest = MemberChanges.nameChange();

        // WHEN
        execution = () -> service.update(MemberConstants.NUMBER, memberRequest);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberIdException.class);
    }

}