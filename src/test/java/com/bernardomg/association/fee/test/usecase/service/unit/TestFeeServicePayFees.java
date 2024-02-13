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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.bernardomg.association.configuration.usecase.source.AssociationConfigurationSource;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeePayment;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.fee.test.config.factory.FeePayments;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.member.domain.exception.MissingMemberIdException;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.config.data.annotation.ValidMember;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee service - pay fees")
class TestFeeServicePayFees {

    @Mock
    private AssociationConfigurationSource configurationSource;

    @Mock
    private FeeRepository                  feeRepository;

    @Mock
    private MemberRepository               memberRepository;

    @Mock
    private MessageSource                  messageSource;

    @InjectMocks
    private DefaultFeeService              service;

    @Mock
    private TransactionRepository          transactionRepository;

    @Test
    @DisplayName("Can pay fees")
    void testPayFees() {
        final Collection<Fee> fees;
        final FeePayment      payment;

        // GIVEN
        payment = FeePayments.single();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeRepository.save(ArgumentMatchers.anyCollection())).willReturn(List.of(Fees.paid()));
        given(feeRepository.findAllForMemberInDates(MemberConstants.NUMBER, List.of(FeeConstants.DATE)))
            .willReturn(List.of(Fees.paid()));

        // WHEN
        fees = service.payFees(payment);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

    @Test
    @DisplayName("With duplicated dates, it throws an exception")
    @ValidMember
    void testPayFees_DuplicatedDates() {
        final ThrowingCallable execution;
        final FieldFailure     failure;
        final FeePayment       payment;

        // GIVEN
        payment = FeePayments.duplicated();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        // WHEN
        execution = () -> service.payFees(payment);

        // THEN
        failure = FieldFailure.of("feeDates[].duplicated", "feeDates[]", "duplicated", 1L);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("With the fee already paid, it throws an exception")
    @ValidMember
    @PaidFee
    void testPayFees_Existing_Paid() {
        final ThrowingCallable execution;
        final FieldFailure     failure;
        final FeePayment       payment;

        // GIVEN
        payment = FeePayments.single();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeRepository.existsPaid(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(true);

        // WHEN
        execution = () -> service.payFees(payment);

        // THEN
        failure = FieldFailure.of("feeDates[].existing", "feeDates[]", "existing", 1L);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("When the member doesn't exist it throws an exception")
    void testPayFees_InvalidMember() {
        final ThrowingCallable execution;
        final FeePayment       payment;

        // GIVEN
        payment = FeePayments.single();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.payFees(payment);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberIdException.class);
    }

    @Test
    @DisplayName("With the fee already paid, and trying to pay multiple dates, it throws an exception")
    @ValidMember
    @PaidFee
    void testPayFees_MultipleDates_OneExisting_Paid() {
        final ThrowingCallable execution;
        final FieldFailure     failure;
        final FeePayment       payment;

        // GIVEN
        payment = FeePayments.two();

        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeRepository.existsPaid(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(true);

        // WHEN
        execution = () -> service.payFees(payment);

        // THEN
        failure = FieldFailure.of("feeDates[].existing", "feeDates[]", "existing", 1L);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

}
