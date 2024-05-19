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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.bernardomg.association.configuration.usecase.source.AssociationConfigurationSource;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.config.factory.PersonConstants;
import com.bernardomg.association.person.test.config.factory.Persons;
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

    @Mock
    private PersonRepository               personRepository;

    @InjectMocks
    private DefaultFeeService              service;

    @Mock
    private TransactionRepository          transactionRepository;

    @Test
    @DisplayName("Can pay fees")
    void testPayFees() {
        final Collection<Fee> fees;

        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.valid()));
        given(feeRepository.save(ArgumentMatchers.anyCollection())).willReturn(List.of(Fees.paid()));
        given(feeRepository.findAllForMemberInDates(PersonConstants.NUMBER, List.of(FeeConstants.DATE)))
            .willReturn(List.of(Fees.paid()));

        // WHEN
        fees = service.payFees(List.of(FeeConstants.DATE), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

    @Test
    @DisplayName("When paying the current month, the user is set to active")
    void testPayFees_CurrentMonth_SetActive() {
        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.valid()));
        given(feeRepository.save(ArgumentMatchers.anyCollection())).willReturn(List.of(Fees.paid()));
        given(feeRepository.findAllForMemberInDates(PersonConstants.NUMBER, List.of(FeeConstants.CURRENT_MONTH)))
            .willReturn(List.of(Fees.paid()));

        // WHEN
        service.payFees(List.of(FeeConstants.CURRENT_MONTH), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        verify(memberRepository).activate(PersonConstants.NUMBER);
    }

    @Test
    @DisplayName("With duplicated dates, it throws an exception")
    void testPayFees_DuplicatedDates() {
        final ThrowingCallable execution;
        final FieldFailure     failure;

        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.valid()));

        // WHEN
        execution = () -> service.payFees(List.of(FeeConstants.DATE, FeeConstants.DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        failure = FieldFailure.of("feeDates[].duplicated", "feeDates[]", "duplicated", 1L);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("With no fees nothing is saved")
    void testPayFees_EmptyList() {
        final Collection<Fee> fees;

        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.valid()));
        given(feeRepository.save(ArgumentMatchers.anyCollection())).willReturn(List.of());
        given(feeRepository.findAllForMemberInDates(PersonConstants.NUMBER, List.of())).willReturn(List.of());

        // WHEN
        fees = service.payFees(List.of(), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("With the fee already paid, it throws an exception")
    void testPayFees_Existing_Paid() {
        final ThrowingCallable execution;
        final FieldFailure     failure;

        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.valid()));
        given(feeRepository.existsPaid(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(true);

        // WHEN
        execution = () -> service.payFees(List.of(FeeConstants.DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        failure = FieldFailure.of("feeDates[].existing", "feeDates[]", "existing", 1L);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("With the fee already paid, and trying to pay multiple dates, it throws an exception")
    void testPayFees_MultipleDates_OneExisting_Paid() {
        final ThrowingCallable execution;
        final FieldFailure     failure;

        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.valid()));
        given(feeRepository.existsPaid(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(true);

        // WHEN
        execution = () -> service.payFees(List.of(FeeConstants.DATE, FeeConstants.NEXT_DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        failure = FieldFailure.of("feeDates[].existing", "feeDates[]", "existing", 1L);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("When the person doesn't exist it throws an exception")
    void testPayFees_NotExistingPerson() {
        final ThrowingCallable execution;

        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.payFees(List.of(FeeConstants.DATE), PersonConstants.NUMBER,
            FeeConstants.PAYMENT_DATE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingPersonException.class);
    }

    @Test
    @DisplayName("When paying the previous month, the user is not set to active")
    void testPayFees_PreviousMonth_NotSetActive() {
        // GIVEN
        given(personRepository.findOne(PersonConstants.NUMBER)).willReturn(Optional.of(Persons.valid()));
        given(feeRepository.save(ArgumentMatchers.anyCollection())).willReturn(List.of(Fees.paid()));
        given(feeRepository.findAllForMemberInDates(PersonConstants.NUMBER, List.of(FeeConstants.PREVIOUS_MONTH)))
            .willReturn(List.of(Fees.paid()));

        // WHEN
        service.payFees(List.of(FeeConstants.PREVIOUS_MONTH), PersonConstants.NUMBER, FeeConstants.PAYMENT_DATE);

        // THEN
        verify(memberRepository, Mockito.never()).activate(PersonConstants.NUMBER);
    }

}
