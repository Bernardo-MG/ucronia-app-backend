
package com.bernardomg.association.fee.member.service.unit;

import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.bernardomg.association.fee.domain.event.FeeDeletedEvent;
import com.bernardomg.association.fee.domain.exception.MissingFeeException;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.domain.repository.FeeTransactionRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.event.emitter.EventEmitter;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeService - delete")
class TestFeeServiceDelete {

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
    @DisplayName("When deleting the current month fee, then an event is sent")
    void testDelete_CurrentMonth_SendEvent() {
        // GIVEN
        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.CURRENT_MONTH))
            .willReturn(Optional.of(Fees.paidCurrentMonth()));

        // WHEN
        service.delete(MemberConstants.NUMBER, FeeConstants.CURRENT_MONTH);

        // THEN
        verify(eventEmitter).emit(assertArg(e -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(e)
                .as("When deleting the current month fee, then an event is sent")
                .isInstanceOf(FeeDeletedEvent.class);
            soft.assertThat(e)
                .as("When deleting the current month fee, then an event is sent")
                .asInstanceOf(InstanceOfAssertFactories.type(FeeDeletedEvent.class))
                .extracting(FeeDeletedEvent::getMonth)
                .isEqualTo(java.time.YearMonth.from(FeeConstants.CURRENT_MONTH.atZone(java.time.ZoneOffset.UTC)));
            soft.assertThat(e)
                .as("When deleting the current month fee, then an event is sent")
                .asInstanceOf(InstanceOfAssertFactories.type(FeeDeletedEvent.class))
                .extracting(FeeDeletedEvent::getProfileNumber)
                .isEqualTo(MemberConstants.NUMBER);
        })));
    }

    @Test
    @DisplayName("When there is a not existing fee, then an exception is thrown")
    void testDelete_NotExistingFee() {
        final ThrowingCallable action;

        // GIVEN
        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(MemberConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing fee, then an exception is thrown")
            .isInstanceOf(MissingFeeException.class);
    }

    @Test
    @DisplayName("When deleting the previous month fee, then an event is sent")
    void testDelete_PreviousMonth_SendEvent() {
        // GIVEN
        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.PREVIOUS_MONTH))
            .willReturn(Optional.of(Fees.paidPreviousMonth()));

        // WHEN
        service.delete(MemberConstants.NUMBER, FeeConstants.PREVIOUS_MONTH);

        // THEN
        verify(eventEmitter).emit(assertArg(e -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(e)
                .as("When deleting the previous month fee, then an event is sent")
                .isInstanceOf(FeeDeletedEvent.class);
            soft.assertThat(e)
                .as("When deleting the previous month fee, then an event is sent")
                .asInstanceOf(InstanceOfAssertFactories.type(FeeDeletedEvent.class))
                .extracting(FeeDeletedEvent::getMonth)
                .isEqualTo(java.time.YearMonth.from(FeeConstants.PREVIOUS_MONTH.atZone(java.time.ZoneOffset.UTC)));
            soft.assertThat(e)
                .as("When deleting the previous month fee, then an event is sent")
                .asInstanceOf(InstanceOfAssertFactories.type(FeeDeletedEvent.class))
                .extracting(FeeDeletedEvent::getProfileNumber)
                .isEqualTo(MemberConstants.NUMBER);
        })));
    }

    @Test
    @DisplayName("When deleting, then the repository is called")
    void testDelete_RemovesEntity() {
        // GIVEN
        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));

        // WHEN
        service.delete(MemberConstants.NUMBER, FeeConstants.DATE);

        // THEN
        verify(feeRepository).delete(MemberConstants.NUMBER, FeeConstants.DATE);
    }

}
