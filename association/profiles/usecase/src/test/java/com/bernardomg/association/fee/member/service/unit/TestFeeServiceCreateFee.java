
package com.bernardomg.association.fee.member.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.bernardomg.association.fee.domain.exception.MissingFeeTypeException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.domain.repository.FeeTransactionRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeService - createFee")
class TestFeeServiceCreateFee {

    @Mock
    private EventEmitter             eventEmitter;

    @Mock
    private FeeRepository            feeRepository;

    @Mock
    private MemberRepository         memberRepository;

    @Mock
    private MessageSource            messageSource;

    @InjectMocks
    private DefaultFeeService        service;

    @Mock
    private FeeTransactionRepository transactionRepository;

    @Test
    @DisplayName("When fee is submitted, then it can be created")
    void testCreateFee() {
        final Fee fee;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));
        given(feeRepository.save(Fees.notPaid())).willReturn(Fees.notPaid());
        given(feeRepository.exists(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(false);

        // WHEN
        fee = service.createFee(FeeConstants.DATE, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(fee)
            .as("fee")
            .isEqualTo(Fees.notPaid());
    }

    @Test
    @DisplayName("When the fee date already exists, then it throws an exception")
    void testCreateFee_Existing() {
        final ThrowingCallable action;
        final FieldFailure     failure;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));
        given(feeRepository.exists(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(true);

        // WHEN
        action = () -> service.createFee(FeeConstants.DATE, MemberConstants.NUMBER);

        // THEN
        failure = new FieldFailure("existing", "month", "month.existing", FeeConstants.DATE);

        ValidationAssertions.assertThatFieldFails(action, failure);
    }

    @Test
    @DisplayName("When the fee has an empty amount, then it is paid automatically")
    void testCreateFee_NoAmout() {
        final Fee fee;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.of(FeeTypes.zero()));
        given(feeRepository.save(Fees.paidNoTransactionNoAmount())).willReturn(Fees.paidNoTransactionNoAmount());
        given(feeRepository.exists(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(false);

        // WHEN
        fee = service.createFee(FeeConstants.DATE, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(fee)
            .as("fee")
            .isEqualTo(Fees.paidNoTransactionNoAmount());
    }

    @Test
    @DisplayName("When the fee type doesn't exist, then it throws an exception")
    void testCreateFee_NotExistingFeeType() {
        final ThrowingCallable action;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.createFee(FeeConstants.DATE, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the fee type doesn't exist, then it throws an exception")
            .isInstanceOf(MissingFeeTypeException.class);
    }

    @Test
    @DisplayName("When the member doesn't exist, then it throws an exception")
    void testCreateFee_NotExistingMember() {
        final ThrowingCallable action;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.createFee(FeeConstants.DATE, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the member doesn't exist, then it throws an exception")
            .isInstanceOf(MissingMemberException.class);
    }

}
