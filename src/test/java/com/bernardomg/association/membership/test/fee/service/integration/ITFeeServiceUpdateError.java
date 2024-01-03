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

package com.bernardomg.association.membership.test.fee.service.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.membership.fee.exception.MissingFeeIdException;
import com.bernardomg.association.membership.fee.model.FeeChange;
import com.bernardomg.association.membership.fee.service.FeeService;
import com.bernardomg.association.membership.member.exception.MissingMemberIdException;
import com.bernardomg.association.membership.test.fee.config.factory.FeeConstants;
import com.bernardomg.association.membership.test.fee.config.factory.FeesUpdate;
import com.bernardomg.association.membership.test.member.config.ValidMember;
import com.bernardomg.association.membership.test.member.config.factory.MemberConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee service - update - errors")
class ITFeeServiceUpdateError {

    @Autowired
    private FeeService service;

    public ITFeeServiceUpdateError() {
        super();
    }

    @Test
    @DisplayName("With a not existing fee, an exception is thrown")
    @ValidMember
    void testUpdate_NotExistingFee_Exception() {
        final FeeChange        feeRequest;
        final ThrowingCallable execution;

        // GIVEN
        feeRequest = FeesUpdate.nextMonth();

        // WHEN
        execution = () -> service.update(MemberConstants.NUMBER, FeeConstants.DATE, feeRequest);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingFeeIdException.class);
    }

    @Test
    @DisplayName("With a not existing member, an exception is thrown")
    void testUpdate_NotExistingMember_Exception() {
        final FeeChange        feeRequest;
        final ThrowingCallable execution;

        // GIVEN
        feeRequest = FeesUpdate.nextMonth();

        // WHEN
        execution = () -> service.update(MemberConstants.NUMBER, FeeConstants.DATE, feeRequest);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberIdException.class);
    }

}
