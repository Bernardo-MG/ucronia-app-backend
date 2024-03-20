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

package com.bernardomg.association.fee.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.exception.MissingFeeException;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.config.factory.MemberConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee service - delete")
class TestFeeServiceDelete {

    @Mock
    private FeeRepository     feeRepository;

    @Mock
    private MemberRepository  memberRepository;

    @InjectMocks
    private DefaultFeeService service;

    public TestFeeServiceDelete() {
        super();
    }

    @Test
    @DisplayName("With a not existing fee, an exception is thrown")
    void testDelete_NotExistingFee() {
        final ThrowingCallable execution;

        // GIVEN
        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(feeRepository.exists(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(false);

        // WHEN
        execution = () -> service.delete(MemberConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingFeeException.class);
    }

    @Test
    @DisplayName("With a not existing member, an exception is thrown")
    void testDelete_NotExistingMember() {
        final ThrowingCallable execution;

        // GIVEN
        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.delete(MemberConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("Calls the repository when deleting")
    void testDelete_NotPaid_RemovesEntity() {
        // GIVEN
        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(feeRepository.exists(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(true);

        // WHEN
        service.delete(MemberConstants.NUMBER, FeeConstants.DATE);

        // THEN
        verify(feeRepository).delete(MemberConstants.NUMBER, FeeConstants.DATE);
    }

}
