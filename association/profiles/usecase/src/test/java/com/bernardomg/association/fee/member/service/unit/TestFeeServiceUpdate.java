
package com.bernardomg.association.fee.member.service.unit;

import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.bernardomg.association.fee.domain.event.FeePaidEvent;
import com.bernardomg.association.fee.domain.exception.MissingFeeException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.domain.repository.FeeTransactionRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
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
@DisplayName("DefaultFeeService - update")
class TestFeeServiceUpdate {

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
    @DisplayName("When a payment is added, then the fee is saved")
    void testUpdate_AddedPayment_SaveFee() {
        final Fee toUpdate;

        // GIVEN
        toUpdate = Fees.addPayment();

        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.notPaid()));
        given(feeRepository.save(Fees.paid())).willReturn(Fees.paid());
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(transactionRepository.findNextIndex()).willReturn(TransactionConstants.INDEX);
        given(transactionRepository.save(Transactions.positive())).willReturn(Transactions.positive());
        given(messageSource.getMessage(ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.any()))
            .willReturn(TransactionConstants.DESCRIPTION);

        // WHEN
        service.update(toUpdate);

        // THEN
        verify(feeRepository).save(Fees.paid());
    }

    @Test
    @DisplayName("When a payment is added, then the payment is saved")
    void testUpdate_AddedPayment_SavePayment() {
        final Fee toUpdate;

        // GIVEN
        toUpdate = Fees.addPayment();

        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.notPaid()));
        given(feeRepository.save(Fees.paid())).willReturn(Fees.paid());
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(transactionRepository.findNextIndex()).willReturn(TransactionConstants.INDEX);
        given(transactionRepository.save(Transactions.positive())).willReturn(Transactions.positive());
        given(messageSource.getMessage(ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.any()))
            .willReturn(TransactionConstants.DESCRIPTION);

        // WHEN
        service.update(toUpdate);

        // THEN
        verify(transactionRepository).save(Transactions.positive());
    }

    @Test
    @DisplayName("When updating a fee, and a payment is added, then an event is sent")
    void testUpdate_AddedPayment_SendEvent() {
        final Fee toUpdate;

        // GIVEN
        toUpdate = Fees.addPayment();

        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.notPaid()));
        given(feeRepository.save(Fees.paid())).willReturn(Fees.paid());
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(transactionRepository.save(Transactions.toCreate())).willReturn(Transactions.positive());
        given(messageSource.getMessage(ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.any()))
            .willReturn(TransactionConstants.DESCRIPTION);

        // WHEN
        service.update(toUpdate);

        // THEN
        verify(eventEmitter).emit(assertArg(e -> Assertions.assertThat(e)
            .as("When updating a fee, and a payment is added, then an event is sent")
            .isInstanceOf(FeePaidEvent.class)));
    }

    @Test
    @DisplayName("When there is a not existing fee, then an exception is thrown")
    void testUpdate_NotExistingFee() {
        final ThrowingCallable action;

        // GIVEN
        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.update(Fees.updatePaid());

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing fee, then an exception is thrown")
            .isInstanceOf(MissingFeeException.class);
    }

    @Test
    @DisplayName("When there is a not existing member, then an exception is thrown")
    void testUpdate_NotExistingMember() {
        final ThrowingCallable action;

        // GIVEN
        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.update(Fees.updatePaid());

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing member, then an exception is thrown")
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("When the fee is paid in the future, then it throws an exception")
    void testUpdate_PaidInFuture() {
        final ThrowingCallable action;
        final FieldFailure     failure;

        // GIVEN
        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        // WHEN
        action = () -> service.update(Fees.paidInFuture());

        // THEN
        failure = new FieldFailure("invalid", "transaction.date", "transaction.date.invalid",
            FeeConstants.PAYMENT_DATE_FUTURE);

        ValidationAssertions.assertThatFieldFails(action, failure);
    }

    @Test
    @DisplayName("When updating a fee, and a payment is changed, then no event is sent")
    void testUpdate_PaymentDateChanged_NotSendEvent() {
        final Instant date;
        final Fee     toUpdate;

        // GIVEN
        date = LocalDate.ofInstant(FeeConstants.PAYMENT_DATE, ZoneOffset.UTC)
            .plusMonths(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        toUpdate = Fees.updatePaidAtDate(date);

        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(feeRepository.save(Fees.paidAtDate(date))).willReturn(toUpdate);
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(transactionRepository.findOne(TransactionConstants.INDEX))
            .willReturn(Optional.of(Transactions.positive()));

        // WHEN
        service.update(toUpdate);

        // THEN
        verify(eventEmitter, Mockito.never()).emit(assertArg(e -> Assertions.assertThat(e)
            .as("When updating a fee, and a payment is changed, then no event is sent")
            .isInstanceOf(FeePaidEvent.class)));
    }

    @Test
    @DisplayName("When the payment date is changed, then the change is returned")
    void testUpdate_PaymentDateChanged_ReturnedData() {
        final Fee     toUpdate;
        final Instant date;
        final Fee     updated;

        // GIVEN
        date = LocalDate.ofInstant(FeeConstants.PAYMENT_DATE, ZoneOffset.UTC)
            .plusMonths(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        toUpdate = Fees.updatePaidAtDate(date);

        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(feeRepository.save(Fees.paidAtDate(date))).willReturn(toUpdate);
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(transactionRepository.findOne(TransactionConstants.INDEX))
            .willReturn(Optional.of(Transactions.positive()));

        // WHEN
        updated = service.update(toUpdate);

        // THEN
        Assertions.assertThat(updated)
            .as("fee")
            .isEqualTo(toUpdate);
    }

    @Test
    @DisplayName("When the payment date is changed, then the fee is saved")
    void testUpdate_PaymentDateChanged_SaveFee() {
        final Fee     toUpdate;
        final Instant date;

        // GIVEN
        date = LocalDate.ofInstant(FeeConstants.PAYMENT_DATE, ZoneOffset.UTC)
            .plusMonths(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        toUpdate = Fees.updatePaidAtDate(date);

        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(feeRepository.save(Fees.paidAtDate(date))).willReturn(toUpdate);
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(transactionRepository.findOne(TransactionConstants.INDEX))
            .willReturn(Optional.of(Transactions.positive()));

        // WHEN
        service.update(toUpdate);

        // THEN
        verify(feeRepository).save(Fees.paidAtDate(date));
    }

    @Test
    @DisplayName("When the payment date is changed, then the payment is updated")
    void testUpdate_PaymentDateChanged_SavePayment() {
        final Fee     toUpdate;
        final Instant date;

        // GIVEN
        date = LocalDate.ofInstant(FeeConstants.PAYMENT_DATE, ZoneOffset.UTC)
            .plusMonths(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        toUpdate = Fees.updatePaidAtDate(date);

        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(feeRepository.save(Fees.paidAtDate(date))).willReturn(toUpdate);
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(transactionRepository.findOne(TransactionConstants.INDEX))
            .willReturn(Optional.of(Transactions.positive()));

        // WHEN
        service.update(toUpdate);

        // THEN
        verify(transactionRepository).save(Transactions.forDate(date));
    }

    @Test
    @DisplayName("When the payment date is not changed, then no payment is saved")
    void testUpdate_PaymentDateNotChanged() {
        final Fee toUpdate;

        // GIVEN
        toUpdate = Fees.updatePaid();

        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(feeRepository.save(Fees.paid())).willReturn(toUpdate);
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        // WHEN
        service.update(Fees.updatePaid());

        // THEN
        verify(transactionRepository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("When the payment date is not changed, then no event is sent")
    void testUpdate_PaymentDateNotChanged_NotSendEvent() {
        final Fee toUpdate;

        // GIVEN
        toUpdate = Fees.updatePaid();

        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(feeRepository.save(Fees.paid())).willReturn(toUpdate);
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        // WHEN
        service.update(toUpdate);

        // THEN
        verify(eventEmitter, Mockito.never()).emit(assertArg(e -> Assertions.assertThat(e)
            .as("When the payment date is not changed, then no event is sent")
            .isInstanceOf(FeePaidEvent.class)));
    }

    @Test
    @DisplayName("When updating a fee, then the change is persisted")
    void testUpdate_PersistedData() {
        final Fee toUpdate;

        // GIVEN
        toUpdate = Fees.updatePaid();

        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(feeRepository.save(Fees.paid())).willReturn(toUpdate);
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        // WHEN
        service.update(toUpdate);

        // THEN
        verify(feeRepository).save(Fees.paid());
    }

    @Test
    @DisplayName("When updating a fee, then the change is returned")
    void testUpdate_ReturnedData() {
        final Fee updated;

        // GIVEN
        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        given(feeRepository.save(Fees.paid())).willReturn(Fees.updatePaid());

        // WHEN
        updated = service.update(Fees.updatePaid());

        // THEN
        Assertions.assertThat(updated)
            .as("fee")
            .isEqualTo(Fees.updatePaid());
    }

}
