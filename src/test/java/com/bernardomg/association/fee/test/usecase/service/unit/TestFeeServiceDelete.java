/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

import com.bernardomg.association.event.domain.FeeDeletedEvent;
import com.bernardomg.association.fee.domain.exception.MissingFeeException;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.person.domain.repository.ContactRepository;
import com.bernardomg.association.person.test.configuration.factory.ContactConstants;
import com.bernardomg.association.settings.usecase.source.AssociationSettingsSource;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.event.emitter.EventEmitter;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee service - delete")
class TestFeeServiceDelete {

    @Mock
    private EventEmitter              eventEmitter;

    @Mock
    private FeeRepository             feeRepository;

    @Mock
    private MessageSource             messageSource;

    @Mock
    private ContactRepository         personRepository;

    @InjectMocks
    private DefaultFeeService         service;

    @Mock
    private AssociationSettingsSource settingsSource;

    @Mock
    private TransactionRepository     transactionRepository;

    public TestFeeServiceDelete() {
        super();
    }

    @Test
    @DisplayName("When deleting the current month fee, an event is sent")
    void testDelete_CurrentMonth_SendEvent() {
        // GIVEN
        given(feeRepository.findOne(ContactConstants.NUMBER, FeeConstants.CURRENT_MONTH))
            .willReturn(Optional.of(Fees.paidCurrentMonth()));

        // WHEN
        service.delete(ContactConstants.NUMBER, FeeConstants.CURRENT_MONTH);

        // THEN
        verify(eventEmitter).emit(assertArg(e -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(e)
                .isInstanceOf(FeeDeletedEvent.class);
            soft.assertThat(e)
                .asInstanceOf(InstanceOfAssertFactories.type(FeeDeletedEvent.class))
                .extracting(FeeDeletedEvent::getDate)
                .isEqualTo(FeeConstants.CURRENT_MONTH);
            soft.assertThat(e)
                .asInstanceOf(InstanceOfAssertFactories.type(FeeDeletedEvent.class))
                .extracting(FeeDeletedEvent::getContactNumber)
                .isEqualTo(ContactConstants.NUMBER);
        })));
    }

    @Test
    @DisplayName("With a not existing fee, an exception is thrown")
    void testDelete_NotExistingFee() {
        final ThrowingCallable execution;

        // GIVEN
        given(feeRepository.findOne(ContactConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.delete(ContactConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingFeeException.class);
    }

    @Test
    @DisplayName("When deleting the previous month fee, an event is sent")
    void testDelete_PreviousMonth_SendEvent() {
        // GIVEN
        given(feeRepository.findOne(ContactConstants.NUMBER, FeeConstants.PREVIOUS_MONTH))
            .willReturn(Optional.of(Fees.paidPreviousMonth()));

        // WHEN
        service.delete(ContactConstants.NUMBER, FeeConstants.PREVIOUS_MONTH);

        // THEN
        verify(eventEmitter).emit(assertArg(e -> SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(e)
                .isInstanceOf(FeeDeletedEvent.class);
            soft.assertThat(e)
                .asInstanceOf(InstanceOfAssertFactories.type(FeeDeletedEvent.class))
                .extracting(FeeDeletedEvent::getDate)
                .isEqualTo(FeeConstants.PREVIOUS_MONTH);
            soft.assertThat(e)
                .asInstanceOf(InstanceOfAssertFactories.type(FeeDeletedEvent.class))
                .extracting(FeeDeletedEvent::getContactNumber)
                .isEqualTo(ContactConstants.NUMBER);
        })));
    }

    @Test
    @DisplayName("When deleting the repository is called")
    void testDelete_RemovesEntity() {
        // GIVEN
        given(feeRepository.findOne(ContactConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));

        // WHEN
        service.delete(ContactConstants.NUMBER, FeeConstants.DATE);

        // THEN
        verify(feeRepository).delete(ContactConstants.NUMBER, FeeConstants.DATE);
    }

}
