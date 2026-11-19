
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

import com.bernardomg.association.fee.domain.exception.MissingFeeException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.domain.repository.FeeTransactionRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.event.emitter.EventEmitter;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFeeService - getOne")
class TestFeeServiceGetOne {

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
    @DisplayName("When there is data, then it is returned")
    void testGetOne() {
        final Optional<Fee> fee;

        // GIVEN
        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));

        // WHEN
        fee = service.getOne(MemberConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .as("fee")
            .contains(Fees.paid());
    }

    @Test
    @DisplayName("When there is a not existing fee, then an exception is thrown")
    void testGetOne_NotExistingFee() {
        final ThrowingCallable action;

        // GIVEN
        given(feeRepository.findOne(MemberConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(MemberConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing fee, then an exception is thrown")
            .isInstanceOf(MissingFeeException.class);
    }

}
