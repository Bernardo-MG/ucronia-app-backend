
package com.bernardomg.association.fee.member.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.Month;
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

import com.bernardomg.association.fee.domain.event.FeePaidEvent;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.domain.repository.FeeTransactionRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.test.configuration.factory.FeesPayments;
import com.bernardomg.association.fee.test.configuration.factory.TransactionConstants;
import com.bernardomg.association.fee.test.configuration.factory.Transactions;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeService - payFees")
class TestFeeServicePayFees {

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
    @DisplayName("When duplicated dates is provided, then they are removed")
    void testPayFees_DuplicatedDates() {
        final Collection<Fee> fees;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));
        given(feeRepository.saveAll(ArgumentMatchers.anyCollection())).willReturn(List.of(Fees.paid()));
        given(messageSource.getMessage(any(), any(), any())).willReturn("", TransactionConstants.DESCRIPTION);
        given(transactionRepository.findNextIndex()).willReturn(TransactionConstants.INDEX);
        given(transactionRepository.save(Transactions.positive())).willReturn(Transactions.positive());

        // WHEN
        fees = service.payFees(FeesPayments.duplicated());

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

    @Test
    @DisplayName("When there is no fees, then nothing is saved")
    void testPayFees_EmptyList() {
        final Collection<Fee> fees;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));
        given(feeRepository.saveAll(List.of())).willReturn(List.of());
        given(messageSource.getMessage(any(), any(), any())).willReturn(TransactionConstants.DESCRIPTION);

        // WHEN
        fees = service.payFees(FeesPayments.empty());

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("When the fee already paid is provided, then it throws an exception")
    void testPayFees_ExistingPaid() {
        final ThrowingCallable action;
        final FieldFailure     failure;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));
        given(feeRepository.existsPaid(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(true);

        // WHEN
        action = () -> service.payFees(FeesPayments.single());

        // THEN
        failure = new FieldFailure("existing", "months[]", "months[].existing", List.of(FeeConstants.DATE));

        ValidationAssertions.assertThatFieldFails(action, failure);
    }

    @Test
    @DisplayName("When pay a multiple fees is requested, then the fees can be paid")
    void testPayFees_Multiple() {
        final Collection<Fee> fees;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));
        given(feeRepository.saveAll(ArgumentMatchers.anyCollection()))
            .willReturn(List.of(Fees.paid(), Fees.paidForMonth(Month.MARCH.getValue())));
        given(messageSource.getMessage(any(), any(), any())).willReturn("", TransactionConstants.DESCRIPTION);
        given(transactionRepository.findNextIndex()).willReturn(TransactionConstants.INDEX);
        given(transactionRepository.save(Transactions.forAmount(TransactionConstants.AMOUNT * 2)))
            .willReturn(Transactions.positive());

        // WHEN
        fees = service.payFees(FeesPayments.multiple());

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid(), Fees.paidForMonth(Month.MARCH.getValue()));
    }

    @Test
    @DisplayName("When the fee already paid, and trying to pay multiple dates is provided, then it throws an exception")
    void testPayFees_MultipleDates_OneExisting_Paid() {
        final ThrowingCallable action;
        final FieldFailure     failure;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));
        given(feeRepository.existsPaid(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(true);

        // WHEN
        action = () -> service.payFees(FeesPayments.multiple());

        // THEN
        failure = new FieldFailure("existing", "months[]", "months[].existing", List.of(FeeConstants.DATE));

        ValidationAssertions.assertThatFieldFails(action, failure);
    }

    @Test
    @DisplayName("When the member doesn't exist, then it throws an exception")
    void testPayFees_NotExistingMember() {
        final ThrowingCallable action;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.payFees(FeesPayments.single());

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the member doesn't exist, then it throws an exception")
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("When the fee is paid in the future, then it throws an exception")
    void testPayFees_PaidInFuture() {
        final ThrowingCallable action;
        final FieldFailure     failure;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));

        // WHEN
        action = () -> service.payFees(FeesPayments.paidFuture());

        // THEN
        failure = new FieldFailure("invalid", "paymentDate", "paymentDate.invalid", FeeConstants.PAYMENT_DATE_FUTURE);

        ValidationAssertions.assertThatFieldFails(action, failure);
    }

    @Test
    @DisplayName("When paying a fee, then an event is sent")
    void testPayFees_SendEvent() {
        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));
        given(feeRepository.saveAll(List.of(Fees.paid()))).willReturn(List.of(Fees.paid()));
        given(messageSource.getMessage(any(), any(), any())).willReturn("", TransactionConstants.DESCRIPTION);
        given(transactionRepository.findNextIndex()).willReturn(TransactionConstants.INDEX);
        given(transactionRepository.save(Transactions.positive())).willReturn(Transactions.positive());

        // WHEN
        service.payFees(FeesPayments.single());

        // THEN
        verify(eventEmitter).emit(assertArg(e -> Assertions.assertThat(e)
            .as("When paying a fee, then an event is sent")
            .isInstanceOf(FeePaidEvent.class)));
    }

    @Test
    @DisplayName("When pay a single fee is requested, then the fees can be paid")
    void testPayFees_Single() {
        final Collection<Fee> fees;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(memberRepository.findFeeType(MemberConstants.NUMBER)).willReturn(Optional.of(FeeTypes.positive()));
        given(feeRepository.saveAll(ArgumentMatchers.anyCollection())).willReturn(List.of(Fees.paid()));
        given(messageSource.getMessage(any(), any(), any())).willReturn("", TransactionConstants.DESCRIPTION);
        given(transactionRepository.findNextIndex()).willReturn(TransactionConstants.INDEX);
        given(transactionRepository.save(Transactions.positive())).willReturn(Transactions.positive());

        // WHEN
        fees = service.payFees(FeesPayments.single());

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

}
